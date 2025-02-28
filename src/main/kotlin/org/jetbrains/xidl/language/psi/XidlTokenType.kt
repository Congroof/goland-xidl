package org.jetbrains.xidl.language.psi

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.xidl.language.XidlLanguage

class XidlTokenType(@NonNls debugName: String) : IElementType(debugName, XidlLanguage.INSTANCE) {
    override fun toString(): String {
        return "XidlTokenType." + super.toString()
    }
}