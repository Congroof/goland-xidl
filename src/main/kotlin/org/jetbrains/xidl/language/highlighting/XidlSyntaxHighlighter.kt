package org.jetbrains.xidl.language.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.tree.IElementType
import org.jetbrains.xidl.language.lexer.XidlLexerAdapter
import org.jetbrains.xidl.language.psi.XidlTypes

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

        private val EMPTY_KEYS = arrayOf<TextAttributesKey>()
    }

    override fun getHighlightingLexer(): Lexer = XidlLexerAdapter()

    override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
        return when (tokenType) {
            // 注释
            XidlTypes.LINE_COMMENT -> pack(LINE_COMMENT)
            XidlTypes.BLOCK_COMMENT -> pack(BLOCK_COMMENT)

            // 字面量
            XidlTypes.LITERAL_STRING -> pack(STRING)
            XidlTypes.LITERAL_INTEGER, XidlTypes.LITERAL_FLOAT -> pack(NUMBER)

            // 自定义类型
//            XidlTypes.XIDL_CUSTOM_TYPE -> pack(KEYWORD)
            // 关键字
            XidlTypes.SIMPLE_TYPE, XidlTypes.KEYWORD -> pack(KEYWORD)
            // 标识符
            XidlTypes.IDENTIFIER -> pack(IDENTIFIER)
            // 括号
            XidlTypes.LEFT_BRACE, XidlTypes.RIGHT_BRACE, XidlTypes.LEFT_PAREN, XidlTypes.RIGHT_PAREN, XidlTypes.LEFT_BRACKET, XidlTypes.RIGHT_BRACKET -> pack(BRACES)

            // 注解
            XidlTypes.ANNOTATION -> pack(ANNOTATION)

            else -> EMPTY_KEYS
        }
    }
} 