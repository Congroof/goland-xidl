package com.wps.cloud.xidl.language.documentation

import com.intellij.lang.documentation.AbstractDocumentationProvider
import com.intellij.lang.documentation.DocumentationMarkup
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.wps.cloud.xidl.language.psi.XidlAnnoString
import com.wps.cloud.xidl.language.psi.XidlBlockMeta
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import com.wps.cloud.xidl.language.psi.XidlSchemaObject
import com.wps.cloud.xidl.language.psi.XidlTypes

/**
 * 在 `.xidl` 里给 schema 提供悬浮文档 / Ctrl+Q 快速文档。
 *
 * 触发场景 (都会被 platform 自动路由到 `generateDoc(element = resolve target)`):
 *  - 鼠标悬浮在 `password: LoginRequest` 的 `LoginRequest` 类型引用上 → resolve 到 [XidlSchemaName];
 *  - 鼠标悬浮在 `@allof { "Foo" }` / `@anyof` / `@oneof` 的字符串上 → 同上;
 *  - Ctrl+Q 停留在 schema 定义名本身 → element 就是 [XidlSchemaName]。
 *
 * 两类输出:
 *  - [getQuickNavigateInfo]: Ctrl+hover 的一行式浮框, 纯文本即可, 重点是"它是什么";
 *  - [generateDoc]: Ctrl+Q 的富文本 popup, HTML, 给出**完整定义 + 前置 doc 注释**,
 *    用户不需再跳到定义文件就能看清整个 schema 的字段清单。
 *
 * 为什么直接贴原始源码作为定义展示 (而不是 AST → 重新格式化):
 *  - key_type 规则在 bnf 里是 private, 没有独立 composite PSI, 重建 field 结构要额外
 *    反射 leaf token, 不划算;
 *  - 原文保留了作者自己的 `@desc`, 注释, 对齐, 反而是最有信息量的呈现形式;
 *  - schema_object 的 text 已经天然包含前置 `@title / @desc` 以及整个 `{ ... }` 块。
 */
class XidlDocumentationProvider : AbstractDocumentationProvider() {

    override fun getQuickNavigateInfo(element: PsiElement?, originalElement: PsiElement?): String? {
        val schema = resolveSchema(element) ?: return null
        val name = schema.schemaName?.text ?: return null
        val desc = extractSchemaAnnotation(schema, "@desc") ?: extractSchemaAnnotation(schema, "@title")
        val location = locationOf(schema)
        return buildString {
            append("schema ").append(name)
            if (!desc.isNullOrBlank()) append("  \u2014  ").append(desc)
            if (location != null) append("  [").append(location).append("]")
        }
    }

    override fun generateDoc(element: PsiElement?, originalElement: PsiElement?): String? {
        val schema = resolveSchema(element) ?: return null
        val name = schema.schemaName?.text ?: return null

        val docComments = collectLeadingDocComments(schema)
        val definitionSource = schemaSourceWithLeadingAnnotations(schema)
        val location = locationOf(schema)

        return buildString {
            append(DocumentationMarkup.DEFINITION_START)
            append("schema <b>").append(StringUtil.escapeXmlEntities(name)).append("</b>")
            if (location != null) {
                append("<br><span style=\"color:#888;font-size:smaller\">")
                append(StringUtil.escapeXmlEntities(location))
                append("</span>")
            }
            append(DocumentationMarkup.DEFINITION_END)

            if (docComments.isNotBlank()) {
                append(DocumentationMarkup.CONTENT_START)
                append(StringUtil.escapeXmlEntities(docComments).replace("\n", "<br>"))
                append(DocumentationMarkup.CONTENT_END)
            }

            // 把完整定义以等宽代码块贴出来, 用户一眼能看完全部字段
            append("<pre style=\"font-family:monospace;white-space:pre-wrap;margin:0\">")
            append(StringUtil.escapeXmlEntities(definitionSource))
            append("</pre>")
        }
    }

    /**
     * 允许点在 schema 名 leaf 上 (例如 identifier token 而不是 XidlSchemaName 本身) 时,
     * 也能把它识别成 "要查 schema 的位置"。 这让 Ctrl+Q 在 schema 定义行上任意位置生效。
     *
     * 对引用侧 (`LoginRequest` / `@allof { "Foo" }`) 的场景, platform 已经把
     * element 解析成 schema 目标了, 这里不需要特别处理。
     */
    override fun getCustomDocumentationElement(
        editor: com.intellij.openapi.editor.Editor,
        file: com.intellij.psi.PsiFile,
        contextElement: PsiElement?,
        targetOffset: Int,
    ): PsiElement? {
        if (contextElement == null) return null
        return PsiTreeUtil.getParentOfType(contextElement, XidlSchemaName::class.java, false)
    }

    /** 把任意 xidl 元素归约成对应的 schema 定义节点, 找不到返回 null。 */
    private fun resolveSchema(element: PsiElement?): XidlSchemaObject? {
        if (element == null) return null
        return when (element) {
            is XidlSchemaObject -> element
            is XidlSchemaName -> PsiTreeUtil.getParentOfType(element, XidlSchemaObject::class.java)
            else -> PsiTreeUtil.getParentOfType(element, XidlSchemaObject::class.java, false)
        }
    }

    /**
     * 取 schema 定义上方连续的 `//` / `/* */` 注释 (允许中间是纯空白但不能跨空行),
     * 作为 Go / Java 风格的"doc 注释"呈现。 与 `@desc` 注解是互补关系:
     *  - `@desc` 是结构化注解, 会出现在 definition 顶部;
     *  - 注释是自由文本, 适合多行描述 / 示例 / TODO。
     */
    private fun collectLeadingDocComments(schema: XidlSchemaObject): String {
        val lines = mutableListOf<String>()
        var node: PsiElement? = schema.prevSibling
        // 允许 schema 前面有若干个 @annotation token, 那些不是注释, 跳过即可
        while (node != null && (node is PsiWhiteSpace || node is PsiComment || isAnnotationLeaf(node))) {
            if (node is PsiComment) {
                lines.add(0, stripCommentMarkers(node.text))
            } else if (node is PsiWhiteSpace) {
                // 遇到空行 (连续两个换行) 就截断, 只保留紧邻 schema 的那块注释
                if (node.text.count { it == '\n' } >= 2 && lines.isNotEmpty()) break
            }
            node = node.prevSibling
        }
        return lines.joinToString("\n").trim()
    }

    private fun isAnnotationLeaf(element: PsiElement): Boolean {
        val t = element.node?.elementType ?: return false
        return t == XidlTypes.ZERO_PARAM_ANNOTATION ||
            t == XidlTypes.SINGLE_PARAM_ANNOTATION ||
            t == XidlTypes.MULTI_PARAM_ANNOTATION ||
            t == XidlTypes.CUSTOM_ANNOTATION ||
            element is XidlAnnoString ||
            element is XidlBlockMeta
    }

    /**
     * 找 schema 上指定单参数注解的裸值, 例如 `@desc "foo"` → `"foo"` (去引号)。
     *
     * schema_object 里 single_param_anno 是 private 规则, 但它里面的字符串会作为
     * [XidlAnnoString] 挂在 schema 直接子节点上, 判断"紧邻前兄弟是不是指定注解 token"即可。
     */
    private fun extractSchemaAnnotation(schema: XidlSchemaObject, annoName: String): String? {
        for (annoStr in schema.annoStringList) {
            var prev: PsiElement? = annoStr.prevSibling
            while (prev is PsiWhiteSpace || prev is PsiComment) prev = prev.prevSibling
            val p = prev ?: continue
            if (p.node?.elementType == XidlTypes.SINGLE_PARAM_ANNOTATION && p.text == annoName) {
                return unquote(annoStr.text)
            }
        }
        return null
    }

    /**
     * 把 schema 定义连同紧挨着它前面的若干 annotation token 一起原样取出来,
     * 让 `generateDoc` 里的代码块能呈现:
     *
     *     @desc "用户登录请求"
     *     schema LoginRequest {
     *       @desc "用户名"
     *       username: string
     *       password: string
     *     }
     *
     * 而不是只有 `schema { ... }` 部分, 丢掉上方的 `@desc`。
     */
    private fun schemaSourceWithLeadingAnnotations(schema: XidlSchemaObject): String {
        val startElement = findLeadingAnnotationStart(schema)
        val startOffset = startElement.textRange.startOffset
        val endOffset = schema.textRange.endOffset
        val fileText = schema.containingFile?.text ?: return schema.text
        return fileText.substring(startOffset, endOffset)
    }

    /**
     * 从 schema 向前回退, 跳过空白 / 注释, 把连续的 annotation 节点都包进来,
     * 返回最靠前的那一个作为 "片段起点"; 没有前置注解时返回 schema 本身。
     */
    private fun findLeadingAnnotationStart(schema: XidlSchemaObject): PsiElement {
        var cursor: PsiElement = schema
        var prev: PsiElement? = schema.prevSibling
        while (prev != null && (prev is PsiWhiteSpace || isAnnotationLeaf(prev))) {
            if (isAnnotationLeaf(prev)) cursor = prev
            prev = prev.prevSibling
        }
        return cursor
    }

    private fun locationOf(schema: XidlSchemaObject): String? {
        val file = schema.containingFile?.virtualFile ?: return null
        val doc = com.intellij.psi.PsiDocumentManager
            .getInstance(schema.project)
            .getDocument(schema.containingFile) ?: return file.name
        val line = doc.getLineNumber(schema.textRange.startOffset) + 1
        return "${file.name}:${line}"
    }

    private fun unquote(text: String): String {
        if (text.length < 2) return text
        val f = text.first()
        val l = text.last()
        if ((f == '"' && l == '"') || (f == '`' && l == '`')) {
            return text.substring(1, text.length - 1)
        }
        return text
    }

    private fun stripCommentMarkers(raw: String): String {
        val trimmed = raw.trim()
        return when {
            trimmed.startsWith("//") -> trimmed.removePrefix("//").trim()
            trimmed.startsWith("/*") && trimmed.endsWith("*/") ->
                trimmed.removePrefix("/*").removeSuffix("*/").trim()
            else -> trimmed
        }
    }
}
