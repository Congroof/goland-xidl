package com.wps.cloud.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiWhiteSpace
import com.wps.cloud.xidl.language.psi.XidlBlockMeta
import com.wps.cloud.xidl.language.psi.XidlTypes
import com.wps.cloud.xidl.language.reference.XidlModuleImplReference
import com.wps.cloud.xidl.language.reference.XidlSchemaStringReference

/** 对应这些注解下的字符串指向 schema 定义, 支持 Ctrl+Click 跳转。 */
private val SCHEMA_REF_ANNOTATIONS = setOf("@allof", "@anyof", "@oneof")

/** `@name "foo"` 里的字符串指向 `modules/impl/foo.go` 文件。 */
private const val NAME_ANNOTATION = "@name"

/**
 * `anno_string` 规则的 mixin 基类, 为以下两类注解里的字符串挂上 [PsiReference]:
 *
 *  1. `@allof / @anyof / @oneof { "Foo" }` 多参数注解里的字符串 →
 *     指向对应的 schema 定义 ([XidlSchemaStringReference]);
 *  2. `@name "xxx"` 单参数注解里的字符串 →
 *     指向 Xidl 项目根下 `modules/impl/xxx.go` 文件 ([XidlModuleImplReference])。
 *
 * 走 mixin 路线而不是 PsiReferenceContributor: leaf 级别 STRING_LITERAL 上
 * 的 contributor 并非任何情况下都会被 platform 的 findReferenceAt 流程触发,
 * 而 composite 节点上的 getReference() 是最可靠的 reference 挂载方式
 * (与 XidlTypeReferenceMixin 使用相同策略)。
 */
abstract class XidlAnnoStringMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getReference(): PsiReference? {
        val text = text
        // 至少是一对引号 + 1 个字符, 例如 "a"
        if (text.length < 3) return null
        val range = TextRange(1, text.length - 1)
        val ctx = classifyContext()
        if (LOG.isDebugEnabled) {
            LOG.debug("[xidl anno_string] text=$text ctx=$ctx parent=${parent?.javaClass?.simpleName}")
        }
        return when (ctx) {
            AnnoContext.SCHEMA_REF -> XidlSchemaStringReference(this, range)
            AnnoContext.MODULE_IMPL -> XidlModuleImplReference(this, range)
            AnnoContext.NONE -> null
        }
    }

    /**
     * 根据 anno_string 在语法树中的位置, 判断它属于哪种注解场景。
     *
     * - `@allof / @anyof / @oneof { "X" }`: anno_string 的父节点是 block_meta,
     *   block_meta 的前一个 sibling 是 MULTI_PARAM_ANNOTATION token;
     * - `@name "X"`: anno_string 的前一个 sibling 直接就是
     *   SINGLE_PARAM_ANNOTATION token (single_param_anno 规则为 private,
     *   不产生 composite 节点)。
     */
    private fun classifyContext(): AnnoContext {
        val parent = parent
        if (parent is XidlBlockMeta) {
            val annoText = prevAnnotationText(parent, XidlTypes.MULTI_PARAM_ANNOTATION)
            if (annoText != null && annoText in SCHEMA_REF_ANNOTATIONS) {
                return AnnoContext.SCHEMA_REF
            }
            return AnnoContext.NONE
        }
        val annoText = prevAnnotationText(this, XidlTypes.SINGLE_PARAM_ANNOTATION)
        if (annoText == NAME_ANNOTATION) return AnnoContext.MODULE_IMPL
        return AnnoContext.NONE
    }

    /**
     * 取 [from] 向前最近的一个非空白 / 非注释 sibling, 当且仅当其 elementType
     * 等于 [expected] 时返回其文本, 否则返回 null。
     */
    private fun prevAnnotationText(
        from: PsiElement,
        expected: com.intellij.psi.tree.IElementType,
    ): String? {
        var prev: PsiElement? = from.prevSibling
        while (prev is PsiWhiteSpace || prev is PsiComment) {
            prev = prev.prevSibling
        }
        val node = prev?.node ?: return null
        if (node.elementType != expected) return null
        return prev!!.text
    }

    private enum class AnnoContext { SCHEMA_REF, MODULE_IMPL, NONE }

    companion object {
        private val LOG = logger<XidlAnnoStringMixin>()
    }
}
