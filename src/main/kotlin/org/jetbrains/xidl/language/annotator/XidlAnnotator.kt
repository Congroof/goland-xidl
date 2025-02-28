package org.jetbrains.xidl.language.annotator

import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import org.jetbrains.xidl.language.psi.XidlDataType
import org.jetbrains.xidl.language.psi.XidlTypeReference

class XidlAnnotator : Annotator {
    override fun annotate(element: com.intellij.psi.PsiElement, holder: com.intellij.lang.annotation.AnnotationHolder) {
        if (element is XidlDataType) {
            val identifier = element.identifier
            if (identifier != null) {
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(identifier.textRange)
                    .textAttributes(DefaultLanguageHighlighterColors.CLASS_REFERENCE)
                    .create()
            }
        }
        if (element is XidlTypeReference) {
            val identifier = element.identifier
                holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
                    .range(identifier.textRange)
                    .textAttributes(DefaultLanguageHighlighterColors.CLASS_REFERENCE)
                    .create()
        }
    }
}