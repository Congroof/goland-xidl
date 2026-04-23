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
        val HTTP_METHOD = TextAttributesKey.createTextAttributesKey(
            "XIDL_HTTP_METHOD",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val BUILTIN_TYPE = TextAttributesKey.createTextAttributesKey(
            "XIDL_BUILTIN_TYPE",
            DefaultLanguageHighlighterColors.KEYWORD
        )
        val BOOLEAN = TextAttributesKey.createTextAttributesKey(
            "XIDL_BOOLEAN",
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

        val BRACES = TextAttributesKey.createTextAttributesKey(
            "XIDL_BRACES",
            DefaultLanguageHighlighterColors.BRACES
        )

        val BRACKETS = TextAttributesKey.createTextAttributesKey(
            "XIDL_BRACKETS",
            DefaultLanguageHighlighterColors.BRACKETS
        )

        val PARENTHESES = TextAttributesKey.createTextAttributesKey(
            "XIDL_PARENTHESES",
            DefaultLanguageHighlighterColors.PARENTHESES
        )

        val ANNOTATION = TextAttributesKey.createTextAttributesKey(
            "XIDL_ANNOTATION",
            DefaultLanguageHighlighterColors.METADATA
        )

        val SIGN = TextAttributesKey.createTextAttributesKey(
            "XIDL_SIGN",
            DefaultLanguageHighlighterColors.OPERATION_SIGN
        )

        val SEMICOLON = TextAttributesKey.createTextAttributesKey(
            "XIDL_SEMICOLON",
            DefaultLanguageHighlighterColors.SEMICOLON
        )

        val COMMA = TextAttributesKey.createTextAttributesKey(
            "XIDL_COMMA",
            DefaultLanguageHighlighterColors.COMMA
        )

        val DOT = TextAttributesKey.createTextAttributesKey(
            "XIDL_DOT",
            DefaultLanguageHighlighterColors.DOT
        )

        private val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer = XidlLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {

        return when (tokenType) {
            XidlTypes.LINE_COMMENT -> pack(LINE_COMMENT)
            XidlTypes.BLOCK_COMMENT -> pack(BLOCK_COMMENT)

            XidlTypes.STRING_LITERAL -> pack(STRING)
            XidlTypes.INTEGER_LITERAL, XidlTypes.FLOAT_LITERAL -> pack(NUMBER)
            XidlTypes.BOOLEAN_LITERAL -> pack(BOOLEAN)

            XidlTypes.KEYWORD -> pack(KEYWORD)
            XidlTypes.HTTP_METHOD -> pack(HTTP_METHOD)
            XidlTypes.SIMPLE_TYPE, XidlTypes.MAP, XidlTypes.CHAN -> pack(BUILTIN_TYPE)

            XidlTypes.IDENTIFIER -> pack(IDENTIFIER)

            XidlTypes.LBRACE, XidlTypes.RBRACE -> pack(BRACES)
            XidlTypes.LBRACKET, XidlTypes.RBRACKET -> pack(BRACKETS)
            XidlTypes.LPAREN, XidlTypes.RPAREN -> pack(PARENTHESES)

            XidlTypes.CUSTOM_ANNOTATION, XidlTypes.ZERO_PARAM_ANNOTATION,
                XidlTypes.SINGLE_PARAM_ANNOTATION, XidlTypes.MULTI_PARAM_ANNOTATION -> pack(ANNOTATION)

            XidlTypes.SEMICOLON -> pack(SEMICOLON)
            XidlTypes.COMMA -> pack(COMMA)
            XidlTypes.DOT -> pack(DOT)
            XidlTypes.CHAN_SEND, XidlTypes.CHAN_RECEIVE,
                XidlTypes.MULTIPLY, XidlTypes.DIVIDE, XidlTypes.QUESTION,
                XidlTypes.ASSIGN, XidlTypes.LANGLE, XidlTypes.RANGLE,
                XidlTypes.COLON -> pack(SIGN)

            else -> EMPTY_KEYS
        }
    }
}
