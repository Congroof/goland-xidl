package com.wps.cloud.xidl.language.psi

import com.intellij.psi.tree.IElementType
import com.wps.cloud.xidl.language.XidlLanguage
import org.jetbrains.annotations.NonNls

class XidlTokenType(@NonNls debugName: String) : IElementType(debugName, XidlLanguage.INSTANCE) {
    override fun toString(): String {
        return "XidlTokenType." + super.toString()
    }
}
