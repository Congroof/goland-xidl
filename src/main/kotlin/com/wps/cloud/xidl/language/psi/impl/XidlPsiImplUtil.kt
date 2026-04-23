package com.wps.cloud.xidl.language.psi.impl

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.util.PsiTreeUtil
import com.wps.cloud.xidl.language.XidlFileType
import com.wps.cloud.xidl.language.psi.XidlFile
import com.wps.cloud.xidl.language.psi.XidlSchemaName

/**
 * Grammar-Kit 通过 psiImplUtilClass 调用的静态工具方法集合。
 *
 * 说明:
 * - XidlSchemaName 的 getName/setName/getNameIdentifier 已由 mixin
 *   (XidlNamedElementImpl) 直接实现。
 * - XidlTypeReference 的 getReference 已由 mixin (XidlTypeReferenceMixin) 实现。
 * 之所以都不走 psiImplUtilClass 的静态委托：Gradle 下 generateParser 比
 * Kotlin 编译先执行, Grammar-Kit 在 classpath 里反射找不到 Kotlin 源码里
 * 定义的静态方法签名, 会跳过方法生成并在接口里写 `WARNING ... is skipped`。
 */
object XidlPsiImplUtil {

    /**
     * 通过临时的 XidlFile 构造一个新的 XidlSchemaName, 返回其标识符 Leaf 节点。
     * 用于重命名时生成替换源。返回 null 表示 newName 不是合法的 Xidl 标识符。
     */
    fun createSchemaNameIdentifier(project: Project, newName: String): PsiElement? {
        val file = PsiFileFactory.getInstance(project)
            .createFileFromText(
                "_dummy.xidl",
                XidlFileType.INSTANCE,
                "schema $newName {}"
            ) as? XidlFile ?: return null
        val schemaName = PsiTreeUtil.findChildOfType(file, XidlSchemaName::class.java) ?: return null
        return schemaName.firstChild
    }
}
