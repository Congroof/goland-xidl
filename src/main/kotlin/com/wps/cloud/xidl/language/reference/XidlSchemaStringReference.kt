package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult

/**
 * 用于 `@allof / @anyof / @oneof { "Foo", "Bar" }` 这类注解里的字符串字面量,
 * 将字符串内容指向对应的 schema 定义, 支持 Ctrl+Click 跳转。
 *
 * 构造时传入的 rangeInElement 为去掉首尾引号后的文本区间,
 * 这样高亮区域与跳转内容都不包含引号。
 */
class XidlSchemaStringReference(
    element: PsiElement,
    rangeInElement: TextRange,
) : PsiReferenceBase<PsiElement>(element, rangeInElement, /* soft = */ true),
    PsiPolyVariantReference {

    private val targetName: String = rangeInElement.substring(element.text)

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        if (targetName.isEmpty()) return ResolveResult.EMPTY_ARRAY
        val currentFile = element.containingFile?.virtualFile ?: return ResolveResult.EMPTY_ARRAY
        val currentDir = currentFile.parent ?: return ResolveResult.EMPTY_ARRAY
        val schemas = XidlUtil.findSchemasInScopedDirectories(element.project, targetName, currentDir)
        return schemas
            .map { PsiElementResolveResult(it) }
            .toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val results = multiResolve(false)
        return when (results.size) {
            0 -> null
            1 -> results[0].element
            else -> null // 多个候选时返回 null, 让 IDE 弹出选择列表
        }
    }

    override fun getVariants(): Array<Any> = emptyArray()
}
