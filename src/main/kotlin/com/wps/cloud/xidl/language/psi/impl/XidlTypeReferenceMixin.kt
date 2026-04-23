package com.wps.cloud.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.wps.cloud.xidl.language.reference.XidlReference

/**
 * type_reference 的 mixin 基类。
 *
 * 之所以用 mixin 而不是 `methods=[getReference]` + psiImplUtilClass：
 * Gradle 下 generateParser 任务运行在 Kotlin 编译之前，Grammar-Kit 无法
 * 通过反射在 classpath 里找到 Kotlin 实现的 XidlPsiImplUtil.getReference，
 * 会跳过方法生成，导致 Ctrl+Click 跳转失效。用 mixin 直接在基类覆盖
 * `getReference()`，不依赖生成期的符号查找。
 */
abstract class XidlTypeReferenceMixin(node: ASTNode) : ASTWrapperPsiElement(node) {
    override fun getReference(): PsiReference = XidlReference(this)
}
