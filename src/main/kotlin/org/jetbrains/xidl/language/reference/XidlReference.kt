package org.jetbrains.xidl.language.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.annotations.NotNull
import org.jetbrains.xidl.language.XidlFileType
import org.jetbrains.xidl.language.psi.XidlFile
import org.jetbrains.xidl.language.psi.XidlSchemaDeclaration
import org.jetbrains.xidl.language.psi.XidlTypes


class XidlReference(element: PsiElement) : PsiReferenceBase<PsiElement?>(element),
    PsiPolyVariantReference {
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val name = element.text // 获取标识符名称
        val project = element.project

        val schemas: Collection<XidlSchemaDeclaration> = XidlUtil.findSchemas(project, name)

        val results: MutableList<ResolveResult> = ArrayList()
        for (schema in schemas) {
            results.add(PsiElementResolveResult(schema))
        }
        return results.toTypedArray<ResolveResult>()
    }

    override fun resolve(): PsiElement? {
        val results = multiResolve(false)
        println("results: ${results.size}")
        return if (results.size == 1) results[0].element else null
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }
}

object XidlUtil {
    fun findSchemas(@NotNull project: Project, @NotNull name: String): Collection<XidlSchemaDeclaration> {
        val result: MutableCollection<XidlSchemaDeclaration> = ArrayList()

        // 获取所有 .xidl 文件
        val xidlFiles = FileTypeIndex.getFiles(XidlFileType.INSTANCE, GlobalSearchScope.allScope(project))

        for (file in xidlFiles) {
            val psiFile = PsiManager.getInstance(project).findFile(file)
            if (psiFile is XidlFile) {
                // 在文件中查找 schema 定义
                val schemas = PsiTreeUtil.findChildrenOfType(
                    psiFile,
                    XidlSchemaDeclaration::class.java
                )
                for (schema in schemas) {
                    if (schema.node.findChildByType(XidlTypes.IDENTIFIER)!!.text == name) {
                        result.add(schema)
                    }
                }
            }
        }

        return result
    }
}
