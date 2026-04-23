package com.wps.cloud.xidl.language.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import com.wps.cloud.xidl.language.reference.XidlUtil

class XidlFunctionNameCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
        val position = parameters.position
        val project = position.project
        val currentFile = parameters.originalFile
        val currentDir = currentFile.virtualFile

        val currentXidlFile = currentFile as? XidlFile
        val localSchemas = currentXidlFile?.let {
            PsiTreeUtil.findChildrenOfType(it, XidlSchemaName::class.java)
        } ?: emptyList()

        for (schema in localSchemas) {
            resultSet.addElement(
                LookupElementBuilder.create(schema.text)
                    .withIcon(com.intellij.util.PlatformIcons.CLASS_ICON)
                    .withTypeText("Schema")
            )
        }

        if (currentDir != null) {
            val scopedSchemas = XidlUtil.findAllSchemasInScope(project, currentDir)
            for (schema in scopedSchemas) {
                val schemaName = schema.text
                if (localSchemas.none { it.text == schemaName }) {
                    resultSet.addElement(
                        LookupElementBuilder.create(schemaName)
                            .withIcon(com.intellij.util.PlatformIcons.CLASS_ICON)
                            .withTypeText("Schema")
                    )
                }
            }
        }

        val simpleTypes = listOf(
            "byte", "int", "int8", "int16", "int32", "int64",
            "uint", "uint8", "uint16", "uint32", "uint64",
            "float32", "float64", "bool", "string", "datetime",
            "any", "map", "chan", "interface", "RawMessage"
        )

        for (type in simpleTypes) {
            resultSet.addElement(
                LookupElementBuilder.create(type)
                    .withIcon(com.intellij.util.PlatformIcons.CLASS_ICON)
                    .withTypeText("Built-in Type")
            )
        }
    }
}
