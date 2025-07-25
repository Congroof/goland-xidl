package com.wps.cloud.xidl.language

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import com.wps.cloud.xidl.language.lexer.XidlLexerAdapter
import com.wps.cloud.xidl.language.parser.XidlParser
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlTokenSets
import org.jetbrains.annotations.NotNull
import com.wps.cloud.xidl.language.psi.XidlTypes

internal class XidlParserDefinition : ParserDefinition {
    @NotNull
    override fun createLexer(project: Project?): Lexer {
        return XidlLexerAdapter()
    }

    @NotNull
    override fun getCommentTokens(): TokenSet {
        return XidlTokenSets.COMMENTS
    }

    @NotNull
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    @NotNull
    override fun createParser(project: Project?): PsiParser {
        return XidlParser()
    }

    @NotNull
    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    @NotNull
    override fun createFile(@NotNull viewProvider: FileViewProvider): PsiFile {
        return XidlFile(viewProvider)
    }

    @NotNull
    override fun createElement(node: ASTNode?): PsiElement {
        return XidlTypes.Factory.createElement(node)
    }

    companion object {
        val FILE: IFileElementType = IFileElementType(XidlLanguage.INSTANCE)
    }
}
