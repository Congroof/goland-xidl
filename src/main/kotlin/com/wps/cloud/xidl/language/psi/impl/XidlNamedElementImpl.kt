package com.wps.cloud.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.NavigationItem
import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiElement
import com.intellij.util.PlatformIcons
import com.wps.cloud.xidl.language.psi.XidlNamedElement
import javax.swing.Icon

/**
 * XIDL 命名元素基础实现
 * 支持在导航时显示绝对路径
 */
abstract class XidlNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), XidlNamedElement, NavigationItem {

    override fun getPresentation(): ItemPresentation? {
        return XidlItemPresentation(this)
    }

    override fun getNavigationElement(): PsiElement = this

    override fun canNavigate(): Boolean = true

    override fun canNavigateToSource(): Boolean = true

}

/**
 * XIDL 元素的自定义展示
 * 在选择列表中显示带路径的信息
 */
class XidlItemPresentation(private val element: PsiElement) : ItemPresentation {

    override fun getPresentableText(): String? {
        return element.text
    }

    override fun getLocationString(): String? {
        val file = element.containingFile?.virtualFile
        return if (file != null) {
            // 显示相对于项目的路径
            val project = element.project
            val basePath = project.basePath

            if (basePath != null) {
                // 计算相对路径
                val relativePath = getRelativePath(basePath, file.path)
                "($relativePath)"
            } else {
                // 如果无法获取项目路径，显示文件名和父目录
                val parent = file.parent
                if (parent != null) {
                    "(${parent.name}/${file.name})"
                } else {
                    "(${file.name})"
                }
            }
        } else {
            null
        }
    }

    override fun getIcon(unused: Boolean): Icon? {
        return when {
            // 根据元素类型返回不同的图标
            element.text.matches(Regex("^[A-Z].*")) -> PlatformIcons.CLASS_ICON
            else -> PlatformIcons.VARIABLE_ICON
        }
    }

    /**
     * 计算相对路径
     */
    private fun getRelativePath(basePath: String, targetPath: String): String {
        return try {
            val base = java.io.File(basePath).toPath()
            val target = java.io.File(targetPath).toPath()
            base.relativize(target).toString().replace('\\', '/')
        } catch (e: Exception) {
            // 如果计算相对路径失败，返回文件名
            java.io.File(targetPath).name
        }
    }
}

