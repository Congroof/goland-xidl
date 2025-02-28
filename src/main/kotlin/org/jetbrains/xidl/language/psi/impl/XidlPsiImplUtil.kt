package org.jetbrains.xidl.language.psi.impl

import com.intellij.psi.PsiElement
import org.jetbrains.xidl.language.psi.XidlSchemaDeclaration
import org.jetbrains.xidl.language.psi.XidlTypes


class XidlPsiImplUtil {
    companion object {
        @JvmStatic
        fun getName(element: XidlSchemaDeclaration): String? {
            val keyNode = element.node.findChildByType(XidlTypes.IDENTIFIER)
            return keyNode?.text
        }

        @JvmStatic
        fun setName(element: XidlSchemaDeclaration, newName: String): PsiElement {
            return element
        }

        @JvmStatic
        fun getNameIdentifier(element: XidlSchemaDeclaration): PsiElement? {
            return element.node.findChildByType(XidlTypes.IDENTIFIER)?.psi
        }
    }
}