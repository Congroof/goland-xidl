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
        // 声明 STRING_LITERAL 为字符串字面量 token,
        // 这样 IntelliJ platform 才会把它当成字符串处理:
        // - PsiReferenceContributor 能正常收集 leaf 上的 references
        // - Find Usages / Ctrl+Click / quote handler 才能作用在字符串上
        return TokenSet.create(XidlTypes.STRING_LITERAL)
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
