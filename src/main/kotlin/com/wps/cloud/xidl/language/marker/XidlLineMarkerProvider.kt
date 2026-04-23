package com.wps.cloud.xidl.language.marker

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.wps.cloud.xidl.language.psi.XidlAnnoString
import com.wps.cloud.xidl.language.psi.XidlTypes
import com.wps.cloud.xidl.language.reference.XidlModuleImplReference

/**
 * 在 `.xidl` 文件的行号栏 (gutter) 上, 给 `@name "xxx"` 注解的字符串行放一个
 * 绿色 "跳转到实现" 图标, 点击或悬停可打开对应的 `modules/impl/http/xxx.go`。
 *
 * 行为与现有的 Ctrl+Click 跳转 ([XidlModuleImplReference]) 完全对齐:
 *  - 用同一套 `PsiReference.resolve()` 语义, 所以三级降级策略自动生效;
 *  - 解析不到目标文件时不显示图标 (而非显示问号), 避免视觉噪音;
 *  - 图标只挂在 leaf token (STRING_LITERAL) 上, 满足 platform 对
 *    LineMarker 必须挂在最小元素上的性能要求。
 */
class XidlLineMarkerProvider : LineMarkerProvider {

    /**
     * [LineMarkerProvider] 契约要求: 只在 leaf element 上返回 info, composite
     * 节点上返回 null, 否则高版本 platform 会打 WARN 并在未来版本抛异常。
     *
     * 我们关心的信号是 `@name "xxx"` 里的字符串字面量 token, 它的父节点是
     * composite [XidlAnnoString]; 通过判断 leaf 是不是 STRING_LITERAL 且父节点
     * 是 XidlAnnoString 且挂有 [XidlModuleImplReference], 即可快速过滤。
     */
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        val node = element.node ?: return null
        if (node.elementType != XidlTypes.STRING_LITERAL) return null
        val annoString = element.parent as? XidlAnnoString ?: return null

        // 仅当 XidlAnnoStringMixin 挂的是 XidlModuleImplReference (即 @name 场景)
        // 才出图标, 避免给 @allof/@anyof 等别的字符串也挂上。
        val ref = annoString.reference as? XidlModuleImplReference ?: return null
        val target = ref.resolve() ?: return null

        val annoName = findPrecedingAnnotationName(annoString) ?: return null

        return NavigationGutterIconBuilder
            .create(AllIcons.Gutter.ImplementedMethod)
            .setTarget(target)
            .setTooltipText("跳转到实现: ${target.containingFile?.virtualFile?.path ?: target.toString()}")
            .setAlignment(GutterIconRenderer.Alignment.RIGHT)
            .setPopupTitle("@$annoName 对应的实现")
            .createLineMarkerInfo(element)
    }

    /**
     * 取 [annoString] 前面最近的非空白 / 非注释兄弟节点, 当且仅当其 elementType 为
     * [XidlTypes.SINGLE_PARAM_ANNOTATION] 时返回去掉前导 `@` 的注解名 (如 `name`)。
     */
    private fun findPrecedingAnnotationName(annoString: XidlAnnoString): String? {
        var prev: PsiElement? = annoString.prevSibling
        while (prev is PsiWhiteSpace || prev is PsiComment) {
            prev = prev.prevSibling
        }
        val n = prev?.node ?: return null
        if (n.elementType != XidlTypes.SINGLE_PARAM_ANNOTATION) return null
        return prev!!.text.trimStart('@')
    }
}
