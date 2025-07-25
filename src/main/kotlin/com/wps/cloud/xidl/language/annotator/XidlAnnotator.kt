package com.wps.cloud.xidl.language.annotator

import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.wps.cloud.xidl.language.psi.XidlFuncName
import com.wps.cloud.xidl.language.psi.XidlTypeReference

class XidlAnnotator : Annotator {
    override fun annotate(element: com.intellij.psi.PsiElement, holder: com.intellij.lang.annotation.AnnotationHolder) {
        if (element is XidlTypeReference) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(element.textRange)
                    .textAttributes(DefaultLanguageHighlighterColors.CLASS_REFERENCE)
                    .create()
        }

        if (element is XidlFuncName) {
            val identifier = element.identifier
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(identifier.textRange)
                    .textAttributes(DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
                    .create()
        }
    }
}
