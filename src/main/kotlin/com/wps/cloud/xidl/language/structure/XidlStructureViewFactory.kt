package com.wps.cloud.xidl.language.structure

import com.intellij.ide.structureView.StructureViewBuilder
import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.TreeBasedStructureViewBuilder
import com.intellij.lang.PsiStructureViewFactory
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

/**
 * Xidl 文件的 Structure View 入口 (IDE 右侧 Structure 面板, Cmd+7 / Alt+7)。
 *
 * 展示内容由 [XidlStructureViewModel] 决定, 当前版本展示文件里的:
 *  - `schema Xxx { ... }`
 *  - `endpoints { ... }` 下的每一条 HTTP operation (GET/POST/...)
 *  - `func` / `xapi` 声明
 *
 * 展示形式为扁平列表 (按源码顺序), 使用右上角工具栏可切换字母排序 / 按类型过滤。
 */
class XidlStructureViewFactory : PsiStructureViewFactory {
    override fun getStructureViewBuilder(psiFile: PsiFile): StructureViewBuilder =
        object : TreeBasedStructureViewBuilder() {
            override fun createStructureViewModel(editor: Editor?): StructureViewModel =
                XidlStructureViewModel(psiFile)
        }
}
