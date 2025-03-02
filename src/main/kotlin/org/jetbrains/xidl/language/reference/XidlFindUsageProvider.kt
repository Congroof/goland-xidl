package org.jetbrains.xidl.language.reference

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.jetbrains.xidl.language.lexer.XidlLexerAdapter
import org.jetbrains.xidl.language.psi.XidlSchemaDeclaration
import org.jetbrains.xidl.language.psi.XidlTokenSets
import org.jetbrains.xidl.language.psi.XidlTypes


internal class XidlFindUsageProvider : FindUsagesProvider {
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            XidlLexerAdapter(),
            XidlTokenSets.TYPE_REFERENCES,
            XidlTokenSets.COMMENTS,
            XidlTokenSets.LITERALS
        )
    }

    override fun canFindUsagesFor(@NotNull psiElement: PsiElement): Boolean {
        return false
    }

    @Nullable
    override fun getHelpId(@NotNull psiElement: PsiElement): String? {
        return null
    }

    @NotNull
    override fun getType(@NotNull element: PsiElement): String {
        return "schema_declaration"
    }

    @NotNull
    override fun getDescriptiveName(@NotNull element: PsiElement): String {
        if (element is XidlSchemaDeclaration) {
            return element.node.findChildByType(XidlTypes.IDENTIFIER)?.text ?: ""
        }
        return "demo"
    }

    @NotNull
    override fun getNodeText(@NotNull element: PsiElement, useFullName: Boolean): String {
        return element.text
    }
}