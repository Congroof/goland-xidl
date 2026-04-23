package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.GlobalSearchScopesCore
import com.intellij.psi.util.PsiTreeUtil
import com.wps.cloud.xidl.language.XidlFileType
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import org.jetbrains.annotations.NotNull


class XidlReference(element: PsiElement) : PsiReferenceBase<PsiElement>(element), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val name = element.text
        val project = element.project
        val currentFile = element.containingFile.virtualFile ?: return ResolveResult.EMPTY_ARRAY
        val currentDir = currentFile.parent ?: return ResolveResult.EMPTY_ARRAY

        val schemas = XidlUtil.findSchemasInScopedDirectories(project, name, currentDir)
        return schemas.map { PsiElementResolveResult(it) }.toTypedArray()
    }

    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return when {
            resolveResults.isEmpty() -> null
            resolveResults.size == 1 -> resolveResults[0].element
            else -> null // 多个匹配项时返回 null，让 IDE 显示选择列表
        }
    }

    override fun getVariants(): Array<Any> = emptyArray()
}

object XidlUtil {

    /** Xidl 项目根的标记文件 (可以是文件或目录)。 */
    private const val MARKER_STAGE = ".stage"

    /** Xidl 项目根下放 schema 定义的约定目录。 */
    private const val MARKER_SPEC_DIR = "spec"

    /**
     * 向上查找当前位置所属的 Xidl 项目根目录。
     *
     * 项目根 = 同时包含 [MARKER_STAGE] (`.stage`, 文件或目录都可) 和
     * [MARKER_SPEC_DIR] (`spec/`) 子目录的那个目录。
     *
     * 这样可以天然避免"一个 IDE 里同时打开多个 Xidl 项目"时跨项目串扰:
     * 每个 Xidl 项目都有独立的 `.stage` + `spec/`, 作用域被严格限制在自己的根下。
     *
     * 找不到项目根返回 null, 调用方可自行 fallback。
     */
    fun findXidlProjectRoot(start: VirtualFile): VirtualFile? {
        var dir: VirtualFile? = if (start.isDirectory) start else start.parent
        while (dir != null) {
            if (isXidlProjectRoot(dir)) return dir
            dir = dir.parent
        }
        return null
    }

    private fun isXidlProjectRoot(dir: VirtualFile): Boolean {
        if (!dir.isDirectory) return false
        // .stage 允许是文件或目录
        if (dir.findChild(MARKER_STAGE) == null) return false
        val spec = dir.findChild(MARKER_SPEC_DIR) ?: return false
        return spec.isDirectory
    }

    /**
     * 在当前作用域内查找 schema 定义。
     *
     * - name 为空串: 返回作用域内全部 schema (供补全使用);
     * - name 非空: 按名字精确匹配。
     *
     * 作用域决策:
     * 1. 优先找到当前文件所属的 Xidl 项目根 ([findXidlProjectRoot]), 作用域限定为
     *    该根整棵子树 (含 `spec/`, 以及根下任何其他目录里的 xidl 文件);
     * 2. 找不到项目根时, 回退到旧逻辑: 从当前目录向上逐级直到遇到名为 `spec` 的目录,
     *    这些目录构成作用域 (仅限 IDE 的 project scope, 不会跨内容根)。
     */
    fun findSchemasInScopedDirectories(
        @NotNull project: Project,
        @NotNull name: String,
        @NotNull currentDir: VirtualFile,
    ): Array<PsiElement> {
        val psiManager = PsiManager.getInstance(project)
        val projectRoot = findXidlProjectRoot(currentDir)

        val xidlFiles: Collection<VirtualFile> = if (projectRoot != null) {
            val scope = GlobalSearchScopesCore.directoryScope(project, projectRoot, /* withSubdirectories = */ true)
            FileTypeIndex.getFiles(XidlFileType.INSTANCE, scope)
        } else {
            val dirsToSearch = collectFallbackDirs(currentDir)
            FileTypeIndex
                .getFiles(XidlFileType.INSTANCE, GlobalSearchScope.projectScope(project))
                .filter { isFileInTargetDirectories(it, dirsToSearch) }
        }

        val result = mutableListOf<PsiElement>()
        for (file in xidlFiles) {
            val psiFile = psiManager.findFile(file) as? XidlFile ?: continue
            val refs = PsiTreeUtil.findChildrenOfType(psiFile, XidlSchemaName::class.java)
            for (r in refs) {
                if (name.isEmpty() || r.text == name) {
                    result.add(r)
                }
            }
        }
        return result.toTypedArray()
    }

    fun findAllSchemasInScope(
        @NotNull project: Project,
        @NotNull currentDir: VirtualFile,
    ): Array<PsiElement> = findSchemasInScopedDirectories(project, "", currentDir)

    // ------------------ Fallback: 没有 .stage 标记时的旧作用域逻辑 ------------------

    private fun collectFallbackDirs(currentDir: VirtualFile): List<VirtualFile> {
        val dirs = mutableListOf(currentDir)
        if (currentDir.name == MARKER_SPEC_DIR) return dirs
        var parent = currentDir.parent
        while (parent != null) {
            dirs.add(parent)
            if (parent.name == MARKER_SPEC_DIR) break
            parent = parent.parent
        }
        return dirs
    }

    private fun isFileInTargetDirectories(file: VirtualFile, targetDirs: List<VirtualFile>): Boolean {
        var current = file.parent
        while (current != null) {
            if (current in targetDirs) return true
            current = current.parent
        }
        return false
    }
}
