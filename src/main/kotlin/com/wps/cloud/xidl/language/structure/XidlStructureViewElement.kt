package com.wps.cloud.xidl.language.structure

import com.intellij.icons.AllIcons
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.wps.cloud.xidl.language.psi.XidlAnnoString
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlFuncName
import com.wps.cloud.xidl.language.psi.XidlHttpCookie
import com.wps.cloud.xidl.language.psi.XidlHttpHeader
import com.wps.cloud.xidl.language.psi.XidlHttpQuery
import com.wps.cloud.xidl.language.psi.XidlHttpRequestBody
import com.wps.cloud.xidl.language.psi.XidlHttpResponse
import com.wps.cloud.xidl.language.psi.XidlOperation
import com.wps.cloud.xidl.language.psi.XidlSchemaObject
import com.wps.cloud.xidl.language.psi.XidlTypes
import javax.swing.Icon

/**
 * Structure View 里的一个节点。
 *
 * 不同 PSI 类型给出不同的展示形式:
 *  - 文件根节点: 展示文件名, 子项为文件里所有 schema / operation / func;
 *  - [XidlSchemaObject]: 展示 `schema <Name>`, 默认不展开子项 (字段层未实现);
 *  - [XidlOperation]: 展示 `GET /path`, 位置文本里带 `@name` (若有), 不展开子项;
 *  - [XidlFuncName]: 展示 `func <name>`, 不展开子项。
 *
 * Navigation 能力直接委托给 [NavigatablePsiElement], 这样双击节点即可跳到源码。
 */
class XidlStructureViewElement(
    private val element: NavigatablePsiElement,
) : StructureViewTreeElement, SortableTreeElement {

    override fun getValue(): Any = element

    override fun navigate(requestFocus: Boolean) {
        if (element.canNavigate()) element.navigate(requestFocus)
    }

    override fun canNavigate(): Boolean = element.canNavigate()

    override fun canNavigateToSource(): Boolean = element.canNavigateToSource()

    /**
     * 字母排序用 key; 保持与 presentable text 一致, 让排序结果与肉眼看到的文字对齐。
     */
    override fun getAlphaSortKey(): String = presentation.presentableText.orEmpty()

    override fun getPresentation(): ItemPresentation = XidlItemPresentation(element)

    override fun getChildren(): Array<TreeElement> {
        if (element !is XidlFile) return TreeElement.EMPTY_ARRAY
        val result = mutableListOf<TreeElement>()
        // 按源码顺序深搜一轮, 把关心的顶层结构收集起来。
        // 不用 iterator 是因为 operation 可以出现在 top-level 也可以在 endpoints block 里,
        // endpoint block 自身不是 composite 节点, 统一用深搜覆盖两种情况。
        PsiTreeUtil.processElements(element) { child ->
            when (child) {
                is XidlSchemaObject, is XidlOperation, is XidlFuncName -> {
                    if (child is NavigatablePsiElement) {
                        result.add(XidlStructureViewElement(child))
                    }
                }
            }
            true
        }
        return result.toTypedArray()
    }
}

/**
 * 单一 [ItemPresentation] 实现, 内部按元素类型分派文本 / 图标。
 */
private class XidlItemPresentation(private val element: PsiElement) : ItemPresentation {

    override fun getPresentableText(): String = when (element) {
        is XidlFile -> element.name
        is XidlSchemaObject -> schemaText(element)
        is XidlOperation -> operationText(element)
        is XidlFuncName -> "func ${element.text}"
        else -> element.text ?: ""
    }

    override fun getLocationString(): String? = when (element) {
        is XidlOperation -> operationPath(element).ifBlank { null }
        else -> null
    }

    override fun getIcon(unused: Boolean): Icon = when (element) {
        is XidlFile -> AllIcons.FileTypes.Any_type
        is XidlSchemaObject -> AllIcons.Nodes.Class
        is XidlOperation -> AllIcons.Nodes.Method
        is XidlFuncName -> AllIcons.Nodes.Function
        else -> AllIcons.Nodes.Unknown
    }

    private fun schemaText(schema: XidlSchemaObject): String {
        val name = schema.schemaName?.text ?: "<anonymous>"
        return "schema $name"
    }

    /**
     * 主展示文本: `METHOD <@summary> [<@name>]` (缺失部分会省略对应片段)。
     *
     * - @summary 负责人类可读的功能描述, 放在 method 后, 是眼睛主要扫描的内容;
     * - @name 以 `[...]` 包裹作为次要技术标识, 跟 `modules/impl/http/<name>.go`
     *   的 Ctrl+Click 跳转关联, 方便识别对应实现文件名;
     * - @summary 完全缺失时退化成 `METHOD /path`, 避免只显示孤零零的 HTTP method。
     */
    private fun operationText(op: XidlOperation): String {
        val method = op.httpMethod.text.uppercase()
        val summary = findSingleParamValue(op, "@summary")
        val name = findSingleParamValue(op, "@name")

        val head = if (!summary.isNullOrBlank()) {
            "$method $summary"
        } else {
            val path = operationPath(op)
            if (path.isBlank()) method else "$method $path"
        }
        return if (!name.isNullOrBlank()) "$head [$name]" else head
    }

    /**
     * 提取 HTTP 路径: 从 HTTP_METHOD token 的下一个兄弟开始, 直到遇到请求/响应组件
     * 或分号为止, 中间所有 token 文本拼接。
     *
     * http_path 规则是 private, 没有独立 PSI 节点, 只能靠兄弟 token 顺序收集。
     */
    private fun operationPath(op: XidlOperation): String = buildString {
        var node: PsiElement? = op.httpMethod.nextSibling
        while (node != null) {
            if (isOperationBoundary(node)) break
            append(node.text)
            node = node.nextSibling
        }
    }.trim()

    private fun isOperationBoundary(node: PsiElement): Boolean {
        if (node is XidlHttpQuery || node is XidlHttpHeader ||
            node is XidlHttpCookie || node is XidlHttpRequestBody ||
            node is XidlHttpResponse
        ) return true
        return node.node?.elementType == XidlTypes.SEMICOLON
    }

    /**
     * 在 operation 的直接子节点里, 查找紧跟在指定 `@xxx` 单参数注解后面的
     * [XidlAnnoString] 的裸值 (去引号)。
     *
     * 例如 [annoName] = `"@summary"` 时, 返回 `@summary "foo"` 中的 `foo`;
     * 若 operation 没有该注解, 返回 null。
     */
    private fun findSingleParamValue(op: XidlOperation, annoName: String): String? {
        for (annoStr in op.annoStringList) {
            if (isPrecededBySingleParam(annoStr, annoName)) {
                return unquote(annoStr.text)
            }
        }
        return null
    }

    private fun isPrecededBySingleParam(annoStr: XidlAnnoString, annoName: String): Boolean {
        var prev: PsiElement? = annoStr.prevSibling
        while (prev is PsiWhiteSpace || prev is PsiComment) prev = prev.prevSibling
        val node = prev?.node ?: return false
        if (node.elementType != XidlTypes.SINGLE_PARAM_ANNOTATION) return false
        return prev.text == annoName
    }

    private fun unquote(text: String): String {
        if (text.length < 2) return text
        val first = text.first()
        val last = text.last()
        if ((first == '"' && last == '"') || (first == '`' && last == '`')) {
            return text.substring(1, text.length - 1)
        }
        return text
    }
}
