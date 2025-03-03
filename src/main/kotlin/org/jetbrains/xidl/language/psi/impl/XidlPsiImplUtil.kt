package org.jetbrains.xidl.language.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import org.jetbrains.xidl.language.psi.XidlSchemaDeclaration
import org.jetbrains.xidl.language.psi.XidlTypeReference
import org.jetbrains.xidl.language.psi.XidlTypes
import org.jetbrains.xidl.language.reference.XidlReference

object XidlPsiImplUtil {

    @JvmStatic
    fun getNameIdentifier(element: XidlSchemaDeclaration): PsiElement {
         return element.node.findChildByType(XidlTypes.IDENTIFIER)!!.psi
    }

    @JvmStatic
    fun getNameIdentifier(element: XidlTypeReference): PsiElement {
        return element.identifier
    }

    @JvmStatic
    fun getName(element: XidlSchemaDeclaration): String {
        return element.node.findChildByType(XidlTypes.IDENTIFIER)!!.psi.text
    }

    @JvmStatic
    fun setName(element: XidlSchemaDeclaration, newName: String): PsiElement {
        // TODO: Implement this method
        return element
    }

    @JvmStatic
    fun getName(element: XidlTypeReference): String {
        return element.node.findChildByType(XidlTypes.IDENTIFIER)!!.psi.text
    }

    @JvmStatic
    fun setName(element: XidlTypeReference, newName: String): PsiElement {
        // TODO: Implement this method
        return element
    }

    @JvmStatic
    fun getReference(element: XidlTypeReference): PsiReference {
        return XidlReference(element as PsiElement)
    }
}




