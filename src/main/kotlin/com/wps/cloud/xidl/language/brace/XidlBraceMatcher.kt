package com.wps.cloud.xidl.language.brace
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.wps.cloud.xidl.language.psi.XidlTypes

class XidlBraceMatcher : PairedBraceMatcher {
    override fun getPairs(): Array<BracePair> {
        return PAIRS
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }

    companion object {
        private val PAIRS = arrayOf(
            BracePair(XidlTypes.LPAREN, XidlTypes.RPAREN, false),
            BracePair(XidlTypes.LBRACE, XidlTypes.RBRACE, true),
            BracePair(XidlTypes.LBRACKET, XidlTypes.RBRACKET, false)
        )
    }
}
