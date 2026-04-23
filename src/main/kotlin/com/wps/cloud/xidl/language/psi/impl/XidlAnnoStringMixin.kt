package com.wps.cloud.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiWhiteSpace
import com.wps.cloud.xidl.language.psi.XidlBlockMeta
import com.wps.cloud.xidl.language.psi.XidlTypes
import com.wps.cloud.xidl.language.reference.XidlSchemaStringReference

/** 对应这些注解下的字符串指向 schema 定义, 支持 Ctrl+Click 跳转。 */
private val SCHEMA_REF_ANNOTATIONS = setOf("@allof", "@anyof", "@oneof")

/**
 * `anno_string` 规则的 mixin 基类, 为 `@allof / @anyof / @oneof { "Foo" }` 这类
 * 注解内部的字符串提供 [PsiReference], 从而支持 Ctrl+Click 跳转到 schema 定义
 * 以及 Find Usages 反查。
 *
 * 走 mixin 路线而不是 PsiReferenceContributor: leaf 级别 STRING_LITERAL 上
 * 的 contributor 并非任何情况下都会被 platform 的 findReferenceAt 流程触发,
 * 而 composite 节点上的 getReference() 是最可靠的 reference 挂载方式
 * (与 XidlTypeReferenceMixin 使用相同策略)。
 */
abstract class XidlAnnoStringMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getReference(): PsiReference? {
        if (!isSchemaRef()) return null
        val text = text
        // 至少是一对引号 + 1 个字符, 例如 "a"
        if (text.length < 3) return null
        val range = TextRange(1, text.length - 1)
        return XidlSchemaStringReference(this, range)
    }

    private fun isSchemaRef(): Boolean {
        val blockMeta = parent as? XidlBlockMeta ?: return false
        var prev: PsiElement? = blockMeta.prevSibling
        while (prev is PsiWhiteSpace || prev is PsiComment) {
            prev = prev.prevSibling
        }
        val prevNode = prev?.node ?: return false
        if (prevNode.elementType != XidlTypes.MULTI_PARAM_ANNOTATION) return false
        return prev.text in SCHEMA_REF_ANNOTATIONS
    }
}
