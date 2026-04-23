package com.wps.cloud.xidl.language.annotator

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.psi.PsiElement
import com.wps.cloud.xidl.language.psi.XidlFuncName
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import com.wps.cloud.xidl.language.psi.XidlTypeReference
import com.wps.cloud.xidl.language.reference.XidlUtil

class XidlAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        when (element) {
            is XidlTypeReference -> annotateTypeReference(element, holder)
            is XidlFuncName -> annotateFuncName(element, holder)
            is XidlSchemaName -> annotateSchemaName(element, holder)
        }
    }

    private fun annotateTypeReference(element: XidlTypeReference, holder: AnnotationHolder) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .textAttributes(DefaultLanguageHighlighterColors.CLASS_REFERENCE)
            .create()

        val text = element.text
        if (text.isBlank() || isBuiltinType(text)) return

        val reference = element.reference ?: return
        if (reference.resolve() == null) {
            holder.newAnnotation(HighlightSeverity.WARNING, "无法解析类型引用: $text")
                .range(element.textRange)
                .highlightType(ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                .create()
        }
    }

    private fun annotateFuncName(element: XidlFuncName, holder: AnnotationHolder) {
        val identifier = element.identifier
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(identifier.textRange)
            .textAttributes(DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
            .create()
    }

    /**
     * 检测作用域内同名 schema。
     *
     * 作用域与引用解析保持一致 ([XidlUtil.findSchemasInScopedDirectories]):
     * 从当前文件所在目录向上直到 `spec` 目录为止。这样"跳转能跳到多处" <=> "被判定重名",
     * 行为语义对齐, 不会出现 "标红但 Ctrl+Click 跳转只找到一个" 的割裂感。
     */
    private fun annotateSchemaName(element: XidlSchemaName, holder: AnnotationHolder) {
        val name = element.name ?: return
        if (name.isBlank()) return

        val currentDir = element.containingFile?.virtualFile?.parent ?: return
        val schemas = XidlUtil.findSchemasInScopedDirectories(element.project, name, currentDir)
        // 同作用域内只找到它自身, 不是重名
        if (schemas.size <= 1) return

        val otherLocations = schemas
            .asSequence()
            .filter { it != element }
            .mapNotNull { it.containingFile?.name }
            .distinct()
            .toList()

        val message = buildString {
            append("重复的 schema 名: '")
            append(name)
            append("' 在作用域内有 ")
            append(schemas.size)
            append(" 处定义")
            if (otherLocations.isNotEmpty()) {
                append(", 其它位置: ")
                append(otherLocations.joinToString(", "))
            }
        }

        holder.newAnnotation(HighlightSeverity.ERROR, message)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.GENERIC_ERROR)
            .create()
    }

    private fun isBuiltinType(name: String): Boolean = name in BUILTIN_TYPES

    companion object {
        private val BUILTIN_TYPES = setOf(
            "byte", "int", "int8", "int16", "int32", "int64",
            "uint", "uint8", "uint16", "uint32", "uint64",
            "float32", "float64", "bool", "string", "datetime",
            "any", "map", "chan", "interface", "RawMessage"
        )
    }
}
