package com.wps.cloud.xidl.language.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class XidlKeywordCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {

        val position = parameters.position
        val document = parameters.editor.document
        val offset = parameters.offset

        addAnnotationCompletions(resultSet, checkIfAfterAtSymbol(document, offset))
    }

    private fun checkIfAfterAtSymbol(document: com.intellij.openapi.editor.Document, offset: Int): Boolean {
        if (offset == 0) return false

        // 检查光标前的字符是否是@
        val charBeforeCursor = document.getText(com.intellij.openapi.util.TextRange(offset - 1, offset))
        if (charBeforeCursor == "@") return true

        // 检查前面几个字符中是否有@（考虑空格等）
        val startCheck = maxOf(0, offset - 10)
        val textBefore = document.getText(com.intellij.openapi.util.TextRange(startCheck, offset))
        return textBefore.contains("@") && textBefore.last() != ' '
    }

    private fun checkIfKeywordContext(document: com.intellij.openapi.editor.Document, offset: Int): Boolean {
        val lineNumber = document.getLineNumber(offset)
        val lineStartOffset = document.getLineStartOffset(lineNumber)
        val textBeforeCursor = document.getText(com.intellij.openapi.util.TextRange(lineStartOffset, offset))

        // 如果行开始只有空白字符，则适合关键字
        return textBeforeCursor.isBlank() || textBeforeCursor.trim().isEmpty()
    }

    private fun addAnnotationCompletions(resultSet: CompletionResultSet, afterAtSymbol: Boolean) {
        val zeroParamAnnotations = listOf(
            "noescape", "deprecated", "omitempty", "pflag", "pflagenv",
            "order", "nowrap", "nodoc", "nonstandard", "external", "i18n"
        )

        val singleParamAnnotations = listOf(
            "desc", "title", "summary", "table", "column", "template", "format",
            "name", "use", "short", "shorthand", "long", "min", "server",
            "parent", "type", "xtype", "etype", "level", "timeout"
        )

        val multiParamAnnotations = listOf(
            "tags", "allof", "oneof", "anyof", "middlewares", "middleware_replace_map",
            "x", "permissions", "aliases", "scopes", "identities", "accountTags", "companyTypes"
        )

        for (annotation in zeroParamAnnotations) {
            val displayText = if (afterAtSymbol) annotation else "@$annotation"
            val insertText = if (afterAtSymbol) annotation else "@$annotation"

            resultSet.addElement(
                LookupElementBuilder.create(insertText)
                    .withPresentableText(displayText)
                    .withIcon(com.intellij.util.PlatformIcons.ANNOTATION_TYPE_ICON)
                    .withTypeText("Zero Param Annotation")
                    .withInsertHandler { insertContext, _ ->
                        insertContext.editor.caretModel.moveToOffset(insertContext.selectionEndOffset)
                    }
            )
        }

        for (annotation in singleParamAnnotations) {
            val displayText = if (afterAtSymbol) "$annotation \"...\"" else "@$annotation \"...\""
            val insertKey = if (afterAtSymbol) annotation else "@$annotation"

            resultSet.addElement(
                LookupElementBuilder.create(insertKey)
                    .withPresentableText(displayText)
                    .withIcon(com.intellij.util.PlatformIcons.ANNOTATION_TYPE_ICON)
                    .withTypeText("Single Param Annotation")
                    .withInsertHandler { insertContext, _ ->
                        val template = if (afterAtSymbol) "$annotation \"\"" else "@$annotation \"\""
                        insertContext.document.replaceString(
                            insertContext.startOffset,
                            insertContext.selectionEndOffset,
                            template
                        )
                        insertContext.editor.caretModel.moveToOffset(insertContext.startOffset + template.length - 1)
                    }
            )
        }

        for (annotation in multiParamAnnotations) {
            val displayText = if (afterAtSymbol) "$annotation {...}" else "@$annotation {...}"
            val insertKey = if (afterAtSymbol) annotation else "@$annotation"

            resultSet.addElement(
                LookupElementBuilder.create(insertKey)
                    .withPresentableText(displayText)
                    .withIcon(com.intellij.util.PlatformIcons.ANNOTATION_TYPE_ICON)
                    .withTypeText("Multi Param Annotation")
                    .withInsertHandler { insertContext, _ ->
                        val template = if (afterAtSymbol) "$annotation {\"\"}" else "@$annotation {}"
                        insertContext.document.replaceString(
                            insertContext.startOffset,
                            insertContext.selectionEndOffset,
                            template
                        )
                        insertContext.editor.caretModel.moveToOffset(insertContext.startOffset + template.length - 2)
                    }
            )
        }

    }

}
