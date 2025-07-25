package com.wps.cloud.xidl.language.psi

import com.intellij.psi.tree.TokenSet

interface XidlTokenSets {
    companion object {
        val COMMENTS: TokenSet = TokenSet.create(XidlTypes.LINE_COMMENT, XidlTypes.BLOCK_COMMENT)
        val LITERALS: TokenSet = TokenSet.create(XidlTypes.STRING_LITERAL, XidlTypes.BOOLEAN_LITERAL, XidlTypes.INTEGER_LITERAL, XidlTypes.FLOAT_LITERAL)
        val TYPE_REFERENCES: TokenSet = TokenSet.create(XidlTypes.TYPE_REFERENCE, XidlTypes.IDENTIFIER, XidlTypes.SCHEMA_NAME)
    }
}

