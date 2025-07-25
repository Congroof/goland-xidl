package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.wps.cloud.xidl.language.XidlFileType
import com.wps.cloud.xidl.language.psi.*
import org.jetbrains.annotations.NotNull


class XidlReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val name = element.text // 获取标识符名称
        val project = element.project
        val currentFile = element.containingFile.virtualFile
        val currentDir = currentFile.parent

        val schemas = XidlUtil.findSchemasInScopedDirectories(project, name, currentDir)
        val results: MutableList<ResolveResult> = ArrayList()
        for (schema in schemas) {
            results.add(PsiElementResolveResult(schema))
        }
        return results.toTypedArray<ResolveResult>()
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return when {
            resolveResults.isEmpty() -> null
            resolveResults.size == 1 -> resolveResults[0].element
            else -> null // 多个匹配项时返回 null，让 IDE 显示选择列表
        }
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }
}

object XidlUtil {
    fun findSchemas(@NotNull project: Project, @NotNull name: String): Array<PsiElement> {
        val result = mutableListOf<PsiElement>()

        val xidlFiles = FileTypeIndex.getFiles(XidlFileType.INSTANCE, GlobalSearchScope.allScope(project))
        for (file in xidlFiles) {
            val psiFile = PsiManager.getInstance(project).findFile(file)
            if (psiFile is XidlFile) {
                val refs = PsiTreeUtil.findChildrenOfType(
                    psiFile,
                    XidlSchemaName::class.java
                )
                for (r in refs) {
                    if (r.text.equals(name)) {
                        result.add(r)
                    }
                }
            }
        }
        return result.toTypedArray()
    }

    fun findSchemasInScopedDirectories(@NotNull project: Project, @NotNull name: String, @NotNull currentDir: com.intellij.openapi.vfs.VirtualFile): Array<PsiElement> {
        val result = mutableListOf<PsiElement>()
        val psiManager = PsiManager.getInstance(project)

        // 收集需要搜索的目录
        val dirsToSearch = mutableListOf<com.intellij.openapi.vfs.VirtualFile>()

        // 添加当前目录
        dirsToSearch.add(currentDir)
        println("currentDir: ${currentDir.path}")

        if (currentDir.name != "spec") {
            // 添加父目录直到遇到spec目录
            var parentDir = currentDir.parent
            while (parentDir != null) {
                println("parentDir: ${parentDir.path}")
                dirsToSearch.add(parentDir)

                if (parentDir.name == "spec") {
                    break
                }
                parentDir = parentDir.parent
            }
        }

        val allXidlFiles = FileTypeIndex.getFiles(XidlFileType.INSTANCE, GlobalSearchScope.projectScope(project))

        // 过滤出在目标目录下的文件
        for (file in allXidlFiles) {
            if (isFileInTargetDirectories(file, dirsToSearch)) {
                val psiFile = psiManager.findFile(file)
                if (psiFile is XidlFile) {
                    val refs = PsiTreeUtil.findChildrenOfType(
                        psiFile,
                        XidlSchemaName::class.java
                    )
                    for (r in refs) {
                        if (name.isEmpty() || r.text.equals(name)) {
                            result.add(r)
                        }
                    }
                }
            }
        }

        return result.toTypedArray()
    }

    fun findAllSchemasInScope(@NotNull project: Project, @NotNull currentDir: com.intellij.openapi.vfs.VirtualFile): Array<PsiElement> {
        return findSchemasInScopedDirectories(project, "", currentDir)
    }

    // 判断文件是否在目标目录列表中
    private fun isFileInTargetDirectories(file: com.intellij.openapi.vfs.VirtualFile, targetDirs: List<com.intellij.openapi.vfs.VirtualFile>): Boolean {
        var current = file.parent
        while (current != null) {
            if (targetDirs.contains(current)) {
                return true
            }
            current = current.parent
        }
        return false
    }
}
