package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult
import com.intellij.psi.search.FilenameIndex
import com.intellij.psi.search.GlobalSearchScope

/**
 * 用于 `@name "xxx"` 注解里的字符串字面量, 将其指向 Xidl 项目里
 * `modules/impl/http/${name}.go` 文件, 支持 Ctrl+Click 跳转。
 *
 * 解析策略按优先级:
 *  1. **项目根拼接**: `<xidl_project_root>/modules/impl/http/${name}.go`,
 *     其中项目根由 [XidlUtil.findXidlProjectRoot] 按 `.stage + spec/` 约定确定;
 *  2. **祖先拼接**: 沿 xidl 文件所在目录逐级向上, 拼 `<ancestor>/modules/impl/http/${name}.go`,
 *     命中即返回 (兜底 `.stage` 标记缺失或位置不典型的仓库);
 *  3. **全项目索引**: 通过 [FilenameIndex] 在 IDE 项目作用域内查 `${name}.go`,
 *     过滤路径必须恰好是 `.../modules/impl/http/`, 取第一个命中。
 *
 * soft = true: 找不到目标文件不报错, 允许 .go 文件尚未生成的场景。
 */
class XidlModuleImplReference(
    element: PsiElement,
    rangeInElement: TextRange,
) : PsiReferenceBase<PsiElement>(element, rangeInElement, /* soft = */ true),
    PsiPolyVariantReference {

    private val targetName: String = rangeInElement.substring(element.text)

    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> {
        val file = findTargetFile() ?: return ResolveResult.EMPTY_ARRAY
        val psiFile = PsiManager.getInstance(element.project).findFile(file)
            ?: return ResolveResult.EMPTY_ARRAY
        return arrayOf(PsiElementResolveResult(psiFile))
    }

    override fun resolve(): PsiElement? = multiResolve(false).firstOrNull()?.element

    override fun getVariants(): Array<Any> = emptyArray()

    private fun findTargetFile(): VirtualFile? {
        if (targetName.isEmpty()) return null
        val currentFile = element.containingFile?.virtualFile ?: return null
        val currentDir = currentFile.parent ?: return null
        val fileName = "$targetName.$GO_EXT"
        val relativePath = "$MODULES_DIR/$IMPL_DIR/$HTTP_DIR/$fileName"

        val debug = LOG.isDebugEnabled
        if (debug) LOG.debug("[xidl @name] resolving name='$targetName' currentFile=${currentFile.path}")

        // Strategy 1: xidl 项目根 + 固定相对路径
        val projectRoot = XidlUtil.findXidlProjectRoot(currentDir)
        if (projectRoot != null) {
            val byRoot = projectRoot.findFileByRelativePath(relativePath)
            if (debug) LOG.debug("[xidl @name] strategy=project-root root=${projectRoot.path} -> ${byRoot?.path}")
            if (byRoot != null && byRoot.exists()) return byRoot
        } else if (debug) {
            LOG.debug("[xidl @name] strategy=project-root skipped (no .stage/spec marker found)")
        }

        // Strategy 2: 沿祖先目录拼同样的相对路径
        val byAncestor = findInAncestors(currentDir, relativePath)
        if (debug) LOG.debug("[xidl @name] strategy=ancestor -> ${byAncestor?.path}")
        if (byAncestor != null) return byAncestor

        // Strategy 3: 项目全局 filename index, 路径必须恰好落在 modules/impl/http/
        val scope = GlobalSearchScope.projectScope(element.project)
        val byIndex = FilenameIndex.getVirtualFilesByName(fileName, scope)
            .firstOrNull { isUnderModulesImplHttp(it) }
        if (debug) LOG.debug("[xidl @name] strategy=filename-index -> ${byIndex?.path}")
        return byIndex
    }

    private fun findInAncestors(start: VirtualFile, relativePath: String): VirtualFile? {
        var dir: VirtualFile? = start
        while (dir != null) {
            val candidate = dir.findFileByRelativePath(relativePath)
            if (candidate != null && candidate.exists()) return candidate
            dir = dir.parent
        }
        return null
    }

    /** 判断 [file] 是否恰好位于某个 `.../modules/impl/http/` 目录下。 */
    private fun isUnderModulesImplHttp(file: VirtualFile): Boolean {
        val http = file.parent ?: return false
        if (http.name != HTTP_DIR) return false
        val impl = http.parent ?: return false
        if (impl.name != IMPL_DIR) return false
        val modules = impl.parent ?: return false
        return modules.name == MODULES_DIR
    }

    companion object {
        private val LOG = logger<XidlModuleImplReference>()
        private const val MODULES_DIR = "modules"
        private const val IMPL_DIR = "impl"
        private const val HTTP_DIR = "http"
        private const val GO_EXT = "go"
    }
}
