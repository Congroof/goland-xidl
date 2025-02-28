package org.jetbrains.xidl.language.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiSearchHelper
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.xidl.language.psi.XidlNamedElement

class XidlReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element) {

    override fun resolve(): PsiElement? {
        val name = element.text
        val project = element.project
        var result: PsiElement? = null

        // 在项目中搜索同名的声明
        val scope = GlobalSearchScope.allScope(project)
        PsiSearchHelper.getInstance(project)
            .processAllFilesWithWord(name, scope, { file ->
                // 在文件中查找声明
                val declarations = PsiTreeUtil.findChildrenOfType(file, XidlNamedElement::class.java)
                val found = declarations.find { it.text == name }
                if (found != null) {
                    result = found
                    false
                } else {
                    true
                }
            }, false)

        return result
    }

    override fun getVariants(): Array<Any> {
        // 提供代码补全建议
        return emptyArray()
    }
}

