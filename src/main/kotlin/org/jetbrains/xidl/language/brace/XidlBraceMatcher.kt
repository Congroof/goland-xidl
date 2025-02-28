package org.jetbrains.xidl.language.brace
import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import org.jetbrains.xidl.language.psi.XidlTypes

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
            BracePair(XidlTypes.LEFT_PAREN, XidlTypes.RIGHT_PAREN, false),
            BracePair(XidlTypes.LEFT_BRACE, XidlTypes.RIGHT_BRACE, true),
            BracePair(XidlTypes.LEFT_BRACKET, XidlTypes.RIGHT_BRACKET, false)
        )
    }
}