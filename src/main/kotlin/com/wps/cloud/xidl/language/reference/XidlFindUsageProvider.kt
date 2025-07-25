package com.wps.cloud.xidl.language.reference

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.wps.cloud.xidl.language.lexer.XidlLexerAdapter
import com.wps.cloud.xidl.language.psi.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class XidlFindUsageProvider : FindUsagesProvider {
    
    @Nullable
    override fun getWordsScanner(): WordsScanner {
        return DefaultWordsScanner(
            XidlLexerAdapter(),
            TokenSet.create(XidlTypes.IDENTIFIER, XidlTypes.STRING_LITERAL),
            XidlTokenSets.COMMENTS,
            XidlTokenSets.LITERALS
        )
    }
    
    override fun canFindUsagesFor(@NotNull psiElement: PsiElement): Boolean {
        // 支持查找命名元素和类型引用的使用
        return psiElement is XidlNamedElement || psiElement is XidlTypeReference
    }
    
    @Nullable
    override fun getHelpId(@NotNull psiElement: PsiElement): String? {
        return null
    }
    
    @NotNull
    override fun getType(@NotNull element: PsiElement): String {
        return when (element) {
            is XidlSchemaName -> "schema"
            is XidlTypeReference -> "type reference"
            else -> ""
        }
    }
    
    @NotNull
    override fun getDescriptiveName(@NotNull element: PsiElement): String {
        return when (element) {
            is XidlSchemaName -> element.name
            is XidlTypeReference -> element.identifier.text
            is XidlNamedElement -> element.name ?: "未命名元素"
            else -> ""
        }
    }
    
    @NotNull
    override fun getNodeText(@NotNull element: PsiElement, useFullName: Boolean): String {
        return when (element) {
            is XidlSchemaName -> {
                if (useFullName) {
                    "schema ${element.name}"
                } else {
                    element.name ?: ""
                }
            }
            is XidlTypeReference -> {
                element.identifier.text
            }
            else -> element.text ?: ""
        }
    }
}
