package org.jetbrains.xidl.language.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import org.jetbrains.xidl.language.psi.XidlFile
import org.jetbrains.xidl.language.psi.XidlTypes
import kotlin.math.min

class XidlFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        if (root !is XidlFile) return emptyArray()

        val descriptors = mutableListOf<FoldingDescriptor>()

        // 收集所有可折叠的区域
        PsiTreeUtil.processElements(root) { element ->
            when (element.node.elementType) {
                // 例如，折叠块注释
                XidlTypes.BLOCK_COMMENT -> {
                    descriptors.add(
                        FoldingDescriptor(
                            element.node,
                            element.textRange,
                            null,
                            "/*...*/ "
                        )
                    )
                }
                // 折叠operation
                XidlTypes.OPERATION -> {
                    descriptors.add(
                        FoldingDescriptor(
                            element.node,
                            element.textRange,
                            null,
                            "{...}" + element.text.substring(0, min(60, element.text.length)).trim() + "..."
                        )
                    )
                }
            }
            true
        }

        return descriptors.toTypedArray()
    }

    override fun getPlaceholderText(node: ASTNode): String {
        return when (node.psi.elementType) {
            XidlTypes.BLOCK_COMMENT -> "/*...*/"
            XidlTypes.OPERATION -> "{...}"
            else -> "..."
        }
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return node.elementType != XidlTypes.OPERATION
    }
}