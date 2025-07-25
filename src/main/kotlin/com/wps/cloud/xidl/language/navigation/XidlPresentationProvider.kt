package com.wps.cloud.xidl.language.navigation

import com.intellij.navigation.ItemPresentation
import com.intellij.navigation.ItemPresentationProvider
import com.intellij.psi.PsiElement
import com.intellij.util.PlatformIcons
import com.wps.cloud.xidl.language.psi.XidlSchemaName
import javax.swing.Icon

class XidlSchemaPresentationProvider : ItemPresentationProvider<XidlSchemaName> {
    override fun getPresentation(element: XidlSchemaName): ItemPresentation {
        return XidlSchemaPresentation(element)
    }
}

class XidlSchemaPresentation(private val element: XidlSchemaName) : ItemPresentation {

    override fun getPresentableText(): String {
        return element.text
    }

    override fun getLocationString(): String? {
        return getElementLocationString(element)
    }

    override fun getIcon(unused: Boolean): Icon {
        return PlatformIcons.CLASS_ICON
    }
}


/**
 * 通用的位置字符串生成器
 */
private fun getElementLocationString(element: PsiElement): String? {
    val file = element.containingFile?.virtualFile ?: return null
    val project = element.project
    val basePath = project.basePath

    return if (basePath != null) {
        // 计算相对路径
        val relativePath = calculateRelativePath(basePath, file.path)
        "($relativePath)"
    } else {
        // 回退到显示文件名和父目录
        val parent = file.parent
        if (parent != null) {
            "(${parent.name}/${file.name})"
        } else {
            "(${file.name})"
        }
    }
}

private fun calculateRelativePath(basePath: String, targetPath: String): String {
    return try {
        val base = java.io.File(basePath).toPath().normalize()
        val target = java.io.File(targetPath).toPath().normalize()

        // 计算相对路径
        val relativePath = base.relativize(target).toString()

        // 处理路径分隔符
        relativePath.replace('\\', '/')
    } catch (e: Exception) {
        // 如果计算相对路径失败，返回文件名
        java.io.File(targetPath).name
    }
}
