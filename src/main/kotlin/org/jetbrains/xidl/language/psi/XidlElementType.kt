package org.jetbrains.xidl.language.psi

import com.intellij.psi.tree.IElementType
import org.jetbrains.annotations.NonNls
import org.jetbrains.xidl.language.XidlLanguage

class XidlElementType(@NonNls debugName: String) : IElementType(debugName, XidlLanguage.INSTANCE) {
    override fun toString(): String {
        return "XidlElementType." + super.toString()
    }
}
