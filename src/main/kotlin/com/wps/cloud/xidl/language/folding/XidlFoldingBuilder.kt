package com.wps.cloud.xidl.language.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.elementType
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlTypes


class XidlFoldingBuilder : FoldingBuilderEx(), DumbAware {
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        if (root !is XidlFile) return emptyArray()

        val descriptors = mutableListOf<FoldingDescriptor>()

        // 收集所有可折叠的区域
        PsiTreeUtil.processElements(root) { element ->
            when (element.node.elementType) {
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
                // 折叠block
                XidlTypes.BLOCK_META -> {
                    descriptors.add(
                        FoldingDescriptor(
                            element.node,
                            element.textRange,
                            null,
//                            "{...}" + element.text.substring(0, min(60, element.text.length)).trim() + "..."
                            "{...}"
                        )
                    )
                }
            }
            true
        }

        // 添加region折叠支持
        addRegionFoldingDescriptors(root, document, descriptors)

        return descriptors.toTypedArray()
    }

    private fun addRegionFoldingDescriptors(
        root: PsiElement, 
        document: Document, 
        descriptors: MutableList<FoldingDescriptor>
    ) {
        val documentText = document.text
        val lines = documentText.split('\n')
        val regionStarts = mutableListOf<Int>()
        
        // 使用更简单的文本匹配方式
        for (i in lines.indices) {
            val line = lines[i].trim()
            
            when {
                line.matches(Regex("//\\s*region.*", RegexOption.IGNORE_CASE)) -> {
                    regionStarts.add(i)
                }
                line.matches(Regex("//\\s*(regionend|endregion)", RegexOption.IGNORE_CASE)) -> {
                    if (regionStarts.isNotEmpty()) {
                        val startLine = regionStarts.removeAt(regionStarts.size - 1)
                        val startOffset = document.getLineStartOffset(startLine)
                        val endOffset = document.getLineEndOffset(i)
                        
                        if (endOffset > startOffset) {
                            val regionName = extractRegionNameFromLine(lines[startLine])
                            val placeholderText = if (regionName.isNotEmpty()) {
                                "// region $regionName..."
                            } else {
                                "// region..."
                            }
                            
                            // 找到对应的PSI元素
                            val elementAtStart = root.containingFile.findElementAt(startOffset)
                            if (elementAtStart != null) {
                                descriptors.add(
                                    FoldingDescriptor(
                                        elementAtStart.node,
                                        TextRange(startOffset, endOffset),
                                        null,
                                        placeholderText
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    
    private fun extractRegionNameFromLine(line: String): String {
        val regex = Regex("//\\s*region\\s+(.*)", RegexOption.IGNORE_CASE)
        val match = regex.find(line)
        return match?.groupValues?.get(1)?.trim() ?: ""
    }
    


    override fun getPlaceholderText(node: ASTNode): String {
        return when (node.psi.elementType) {
            XidlTypes.BLOCK_COMMENT -> "/*...*/"
            XidlTypes.BLOCK_META -> "{...}"
            else -> "..."
        }
    }

    override fun isCollapsedByDefault(node: ASTNode): Boolean {
        return when (node.elementType) {
            XidlTypes.BLOCK_META -> false
            else -> false // region等默认不折叠
        }
    }

}
