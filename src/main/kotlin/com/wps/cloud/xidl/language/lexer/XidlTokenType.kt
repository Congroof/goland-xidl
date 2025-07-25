package com.wps.cloud.xidl.language.lexer

import com.intellij.psi.tree.IElementType
import com.wps.cloud.xidl.language.XidlLanguage

class XidlTokenType(debugName: String) : IElementType(debugName, XidlLanguage.INSTANCE) {
    override fun toString(): String = "XidlTokenType." + super.toString()
}
