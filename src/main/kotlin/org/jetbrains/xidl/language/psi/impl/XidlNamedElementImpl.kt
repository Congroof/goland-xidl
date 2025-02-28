package org.jetbrains.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.xidl.language.psi.XidlNamedElement
import org.jetbrains.xidl.language.psi.XidlSchemaDeclaration


abstract class XidlNamedElementImpl(@NotNull node: ASTNode) : ASTWrapperPsiElement(node), XidlNamedElement {
    override fun getName(): String? {
        return XidlPsiImplUtil.getName(this as XidlSchemaDeclaration)
    }

    override fun setName(name: String): PsiElement {
        return XidlPsiImplUtil.setName(this as XidlSchemaDeclaration, name)
    }

    override fun getNameIdentifier(): PsiElement? {
        return XidlPsiImplUtil.getNameIdentifier(this as XidlSchemaDeclaration)
    }
}