package com.wps.cloud.xidl.language.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import com.wps.cloud.xidl.language.psi.XidlTypeReference
import com.wps.cloud.xidl.language.reference.XidlReference

object XidlPsiImplUtil {

    @JvmStatic
    fun getNameIdentifier(element: XidlSchemaName): PsiElement {
        return element
    }

    @JvmStatic
    fun getName(element: XidlSchemaName): String {
        return element.text
    }

    @JvmStatic
    fun setName(element: XidlSchemaName, newName: String): PsiElement {
        val identifierRegex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")
        if (newName.isNotEmpty() && identifierRegex.matches(newName)) {
            // TODO
        } else {
            throw IllegalArgumentException("Invalid identifier: $newName")
        }
        return element
    }

    @JvmStatic
    fun getReference(element: XidlTypeReference): PsiReference {
        return XidlReference(element)
    }

}
