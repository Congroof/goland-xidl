package org.jetbrains.xidl.language.lexer

import com.intellij.psi.tree.IElementType
import org.jetbrains.xidl.language.XidlLanguage

class XidlTokenType(debugName: String) : IElementType(debugName, XidlLanguage.INSTANCE) {
    override fun toString(): String = "XidlTokenType." + super.toString()
} 