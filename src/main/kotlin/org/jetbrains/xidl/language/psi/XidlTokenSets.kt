package org.jetbrains.xidl.language.psi

import com.intellij.psi.tree.TokenSet

interface XidlTokenSets {
    companion object {
        val COMMENTS: TokenSet = TokenSet.create(XidlTypes.LINE_COMMENT, XidlTypes.BLOCK_COMMENT)
        val LITERALS: TokenSet = TokenSet.create(XidlTypes.LITERAL_STRING, XidlTypes.LITERAL_BOOLEAN, XidlTypes.LITERAL_INTEGER, XidlTypes.LITERAL_FLOAT)
    }
}

