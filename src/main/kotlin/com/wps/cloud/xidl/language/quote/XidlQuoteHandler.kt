package com.wps.cloud.xidl.language.quote

import com.intellij.codeInsight.editorActions.SimpleTokenSetQuoteHandler
import com.intellij.psi.TokenType
import com.wps.cloud.xidl.language.psi.XidlTypes

class XidlQuoteHandler : SimpleTokenSetQuoteHandler(
    XidlTypes.STRING_LITERAL,
    TokenType.BAD_CHARACTER  // 用于处理不完整的字符串
) {
    /*
    override fun isClosingQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        // 自定义判断是否为闭合引号的逻辑
        return super.isClosingQuote(iterator, offset)
    }

    override fun isOpeningQuote(iterator: HighlighterIterator, offset: Int): Boolean {
        // 自定义判断是否为开始引号的逻辑
        return super.isOpeningQuote(iterator, offset)
    }
    */
}
