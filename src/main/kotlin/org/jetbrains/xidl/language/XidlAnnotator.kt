package org.jetbrains.xidl.language

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import org.jetbrains.annotations.NotNull


internal class XidlAnnotator : Annotator {
    override fun annotate(@NotNull element: PsiElement, @NotNull holder: AnnotationHolder) {

//        // Ensure the PSI Element is an expression
//        if (element !is PsiLiteralExpression) {
//            return
//        }
//
//        // Ensure the PSI element contains a string that starts with the prefix and separator
//        val value = if (element.getValue() is String) element.getValue() as String? else null
//        if (value == null || !value.startsWith(SIMPLE_PREFIX_STR + SIMPLE_SEPARATOR_STR)) {
//            return
//        }
//
//        // Define the text ranges (start is inclusive, end is exclusive)
//        // "simple:key"
//        //  01234567890
//        val prefixRange: TextRange = TextRange.from(element.textRange.startOffset, SIMPLE_PREFIX_STR.length + 1)
//        val separatorRange: TextRange = TextRange.from(prefixRange.getEndOffset(), SIMPLE_SEPARATOR_STR.length)
//        val keyRange: TextRange = TextRange(separatorRange.getEndOffset(), element.textRange.endOffset - 1)
//
//        // highlight "simple" prefix and ":" separator
//        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//            .range(prefixRange).textAttributes(DefaultLanguageHighlighterColors.KEYWORD).create()
//        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//            .range(separatorRange).textAttributes(SimpleSyntaxHighlighter.SEPARATOR).create()
//
//
//        // Get the list of properties for given key
//        val key = value.substring(SIMPLE_PREFIX_STR.length + SIMPLE_SEPARATOR_STR.length)
//        val properties: List<SimpleProperty> = SimpleUtil.findProperties(element.project, key)
//        if (properties.isEmpty()) {
//            holder.newAnnotation(HighlightSeverity.ERROR, "Unresolved property")
//                .range(keyRange)
//                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL) // ** Tutorial step 19. - Add a quick fix for the string containing possible properties
//                .withFix(SimpleCreatePropertyQuickFix(key))
//                .create()
//        } else {
//            // Found at least one property, force the text attributes to Simple syntax value character
//            holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
//                .range(keyRange).textAttributes(SimpleSyntaxHighlighter.VALUE).create()
//        }
    }

    companion object {
        // Define strings for the Simple language prefix - used for annotations, line markers, etc.
        const val SIMPLE_PREFIX_STR: String = "simple"
        const val SIMPLE_SEPARATOR_STR: String = ":"
    }
}