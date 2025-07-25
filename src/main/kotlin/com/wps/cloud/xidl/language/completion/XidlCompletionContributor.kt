package com.wps.cloud.xidl.language.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.StandardPatterns
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlTypes

class XidlCompletionContributor: CompletionContributor() {
    init {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().andOr(
                PlatformPatterns.psiElement().afterLeaf(":"),
            ),
            XidlFunctionNameCompletionProvider()
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement()
                .andOr(
                    PlatformPatterns.psiElement().afterLeaf(
                        StandardPatterns.or(
                            PlatformPatterns.psiElement(XidlTypes.RBRACE),
                            PlatformPatterns.psiElement(XidlTypes.SEMICOLON),
                            PlatformPatterns.psiElement(XidlTypes.RPAREN),
                        )
                    ),
                    PlatformPatterns.psiElement().withSuperParent(2, XidlFile::class.java),
                ),
            XidlTemplateCompletionProvider()
        )

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().andNot(PlatformPatterns.psiElement().afterLeaf(":")),
            XidlKeywordCompletionProvider()
        )
    }
}
