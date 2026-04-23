package com.wps.cloud.xidl.language.breadcrumbs

import com.intellij.lang.Language
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.ui.breadcrumbs.BreadcrumbsProvider
import com.wps.cloud.xidl.language.XidlLanguage
import com.wps.cloud.xidl.language.psi.XidlBlockMeta
import com.wps.cloud.xidl.language.psi.XidlHttpCookie
import com.wps.cloud.xidl.language.psi.XidlHttpHeader
import com.wps.cloud.xidl.language.psi.XidlHttpQuery
import com.wps.cloud.xidl.language.psi.XidlHttpRequestBody
import com.wps.cloud.xidl.language.psi.XidlHttpResponse
import com.wps.cloud.xidl.language.psi.XidlOperation
import com.wps.cloud.xidl.language.psi.XidlSchemaObject
import com.wps.cloud.xidl.language.psi.XidlTypes

/**
 * 为 Xidl 语言提供编辑器顶部的 Breadcrumbs (面包屑) 导航。
 *
 * 典型效果:
 * ```
 * endpoints > POST /api/v1/auth/login > ? {}
 * schema LoginRequest
 * ```
 *
 * 选择展示的节点:
 *  - [XidlSchemaObject] → `schema <Name>`
 *  - [XidlOperation]    → `METHOD /path`
 *  - [XidlHttpQuery] / [XidlHttpHeader] / [XidlHttpCookie] /
 *    [XidlHttpRequestBody] / [XidlHttpResponse] → `? query` / `! header` / ...
 *  - 特殊情况: [XidlBlockMeta] 仅在它代表 `endpoints { }` 的块体时才参与面包屑,
 *    因为 `endpoints_object` 语法规则是 private, 没有独立 composite 节点,
 *    只能通过 "BlockMeta 的前一个非空白兄弟是 `endpoints` 关键字" 间接识别。
 *
 * 其它 block_meta (schema body / @allof 参数块 / http 请求段内部等) 会被刻意排除,
 * 避免面包屑里冒出没有语义的 `{...}` 节点。
 */
class XidlBreadcrumbsProvider : BreadcrumbsProvider {

    override fun getLanguages(): Array<Language> = arrayOf(XidlLanguage.INSTANCE)

    override fun acceptElement(e: PsiElement): Boolean = when (e) {
        is XidlSchemaObject,
        is XidlOperation,
        is XidlHttpQuery,
        is XidlHttpHeader,
        is XidlHttpCookie,
        is XidlHttpRequestBody,
        is XidlHttpResponse,
        -> true
        is XidlBlockMeta -> isEndpointsBlock(e)
        else -> false
    }

    override fun getElementInfo(e: PsiElement): String = when (e) {
        is XidlSchemaObject -> "schema ${e.schemaName?.text ?: "<anonymous>"}"
        is XidlOperation -> operationLabel(e)
        is XidlHttpQuery -> "? query"
        is XidlHttpHeader -> "! header"
        is XidlHttpCookie -> "~ cookie"
        is XidlHttpRequestBody -> ". body"
        is XidlHttpResponse -> "response"
        is XidlBlockMeta -> "endpoints"
        else -> ""
    }

    override fun getElementTooltip(e: PsiElement): String? = when (e) {
        // operation 在面包屑文本里已经含 path, 鼠标悬停再给出完整描述(method / summary / name)
        is XidlOperation -> operationTooltip(e)
        else -> null
    }

    /**
     * Operation 面包屑文本: `METHOD /path`。
     * 路径拼接逻辑与 Structure View 保持一致 (http_path 是 private 规则, 没有独立 PSI,
     * 只能顺着 HTTP_METHOD 的兄弟 token 往后扫到第一个请求/响应组件或分号为止)。
     */
    private fun operationLabel(op: XidlOperation): String {
        val method = op.httpMethod.text.uppercase()
        val path = buildString {
            var node: PsiElement? = op.httpMethod.nextSibling
            while (node != null) {
                if (isOperationBoundary(node)) break
                append(node.text)
                node = node.nextSibling
            }
        }.trim()
        return if (path.isEmpty()) method else "$method $path"
    }

    private fun operationTooltip(op: XidlOperation): String? {
        val summary = findSingleParamValue(op, "@summary")
        val name = findSingleParamValue(op, "@name")
        if (summary.isNullOrBlank() && name.isNullOrBlank()) return null
        return buildString {
            append(operationLabel(op))
            if (!summary.isNullOrBlank()) {
                append('\n')
                append(summary)
            }
            if (!name.isNullOrBlank()) {
                append('\n')
                append("@name ")
                append(name)
            }
        }
    }

    private fun isOperationBoundary(node: PsiElement): Boolean {
        if (node is XidlHttpQuery || node is XidlHttpHeader ||
            node is XidlHttpCookie || node is XidlHttpRequestBody ||
            node is XidlHttpResponse
        ) return true
        return node.node?.elementType == XidlTypes.SEMICOLON
    }

    /**
     * 判断一个 BlockMeta 是不是 `endpoints { ... }` 的 body:
     * 取它前面最近的非空白 / 非注释兄弟, 文本等于 `endpoints` 即算命中。
     */
    private fun isEndpointsBlock(block: XidlBlockMeta): Boolean {
        var prev: PsiElement? = block.prevSibling
        while (prev is PsiWhiteSpace || prev is PsiComment) {
            prev = prev.prevSibling
        }
        return prev != null && prev.text == ENDPOINTS_KEYWORD
    }

    private fun findSingleParamValue(op: XidlOperation, annoName: String): String? {
        for (annoStr in op.annoStringList) {
            var prev: PsiElement? = annoStr.prevSibling
            while (prev is PsiWhiteSpace || prev is PsiComment) {
                prev = prev.prevSibling
            }
            val node = prev?.node ?: continue
            if (node.elementType != XidlTypes.SINGLE_PARAM_ANNOTATION) continue
            if (prev.text != annoName) continue
            return unquote(annoStr.text)
        }
        return null
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

    companion object {
        private const val ENDPOINTS_KEYWORD = "endpoints"
    }
}
