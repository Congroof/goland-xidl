package com.wps.cloud.xidl.language.structure

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.Sorter
import com.intellij.psi.PsiFile
import com.wps.cloud.xidl.language.psi.XidlFuncName
import com.wps.cloud.xidl.language.psi.XidlOperation
import com.wps.cloud.xidl.language.psi.XidlSchemaObject

/**
 * Xidl 文件的 Structure View 数据模型。
 *
 * 默认按源码顺序展示, 并通过 [Sorter.ALPHA_SORTER] 支持字母排序 (右上角切换)。
 *
 * 作为"可以出现在结构面板里"的 PSI 类型集合 ([SUITABLE_CLASSES]) 用于:
 *  - 光标在文件中移动时, platform 据此高亮结构面板里对应的当前节点;
 *  - Structure 面板里 `Autoscroll from Source` / `Autoscroll to Source` 也依赖它。
 */
class XidlStructureViewModel(
    psiFile: PsiFile,
) : StructureViewModelBase(psiFile, XidlStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {

    init {
        withSuitableClasses(*SUITABLE_CLASSES)
    }

    override fun getSorters(): Array<Sorter> = arrayOf(Sorter.ALPHA_SORTER)

    override fun isAlwaysShowsPlus(element: StructureViewTreeElement): Boolean = false

    override fun isAlwaysLeaf(element: StructureViewTreeElement): Boolean {
        val value = element.value
        // 目前只对 file 根节点展开子项, 其他元素都视为叶子 (不展开字段层)。
        // 若将来要展示 schema 内部的 field / operation 内部的 query/body, 改这里。
        return value !is PsiFile
    }

    companion object {
        private val SUITABLE_CLASSES = arrayOf(
            XidlSchemaObject::class.java,
            XidlOperation::class.java,
            XidlFuncName::class.java,
        )
    }
}
