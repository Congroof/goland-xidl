package org.jetbrains.xidl.language

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
import org.jetbrains.annotations.NotNull
import org.jetbrains.xidl.language.lexer.XidlLexerAdapter
import org.jetbrains.xidl.language.parser.XidlParser
import org.jetbrains.xidl.language.psi.XidlFile
import org.jetbrains.xidl.language.psi.XidlTokenSets
import org.jetbrains.xidl.language.psi.XidlTypes

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