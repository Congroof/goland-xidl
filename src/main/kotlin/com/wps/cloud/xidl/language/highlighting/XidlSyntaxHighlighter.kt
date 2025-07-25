package com.wps.cloud.xidl.language.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import com.wps.cloud.xidl.language.lexer.XidlLexerAdapter
import com.wps.cloud.xidl.language.psi.XidlTypes

class XidlSyntaxHighlighter : SyntaxHighlighterBase() {
    companion object {
        val LINE_COMMENT = TextAttributesKey.createTextAttributesKey(
            "XIDL_LINE_COMMENT",
            DefaultLanguageHighlighterColors.LINE_COMMENT
        )
        val BLOCK_COMMENT = TextAttributesKey.createTextAttributesKey(
            "XIDL_BLOCK_COMMENT",
            DefaultLanguageHighlighterColors.BLOCK_COMMENT
        )
        val KEYWORD = TextAttributesKey.createTextAttributesKey(
            "XIDL_KEYWORD",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val IDENTIFIER = TextAttributesKey.createTextAttributesKey(
            "XIDL_IDENTIFIER",
            DefaultLanguageHighlighterColors.IDENTIFIER
        )
        val STRING = TextAttributesKey.createTextAttributesKey(
            "XIDL_STRING",
            DefaultLanguageHighlighterColors.STRING
        )

        val NUMBER = TextAttributesKey.createTextAttributesKey(
            "XIDL_NUMBER",
            DefaultLanguageHighlighterColors.NUMBER
        )

        // 括号
        val BRACES = TextAttributesKey.createTextAttributesKey(
            "XIDL_BRACES",
            DefaultLanguageHighlighterColors.BRACES
        )

        // 注解
        val ANNOTATION = TextAttributesKey.createTextAttributesKey(
            "XIDL_ANNOTATION",
            DefaultLanguageHighlighterColors.METADATA
        )

        val SIGN = TextAttributesKey.createTextAttributesKey(
            "XIDL_SIGN",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        private val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer = XidlLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {

        return when (tokenType) {
            // 注释
            XidlTypes.LINE_COMMENT -> pack(LINE_COMMENT)
            XidlTypes.BLOCK_COMMENT -> pack(BLOCK_COMMENT)

            // 字面量
            XidlTypes.STRING_LITERAL -> pack(STRING)
            XidlTypes.INTEGER_LITERAL, XidlTypes.FLOAT_LITERAL -> pack(NUMBER)

            // 关键字
            XidlTypes.HTTP_METHOD, XidlTypes.SIMPLE_TYPE, XidlTypes.KEYWORD -> pack(KEYWORD)

            // 标识符
            XidlTypes.IDENTIFIER -> pack(IDENTIFIER)

            // 括号
            XidlTypes.LPAREN, XidlTypes.RPAREN, XidlTypes.LBRACE, XidlTypes.RBRACE,
                XidlTypes.LBRACKET, XidlTypes.RBRACKET -> pack(BRACES)

            // 注解
            XidlTypes.CUSTOM_ANNOTATION, XidlTypes.ZERO_PARAM_ANNOTATION,
                XidlTypes.SINGLE_PARAM_ANNOTATION, XidlTypes.MULTI_PARAM_ANNOTATION -> pack(ANNOTATION)

            // 符号
            XidlTypes.SEMICOLON, XidlTypes.CHAN_SEND, XidlTypes.CHAN_RECEIVE -> pack(SIGN)

            else -> EMPTY_KEYS
        }
    }
}
