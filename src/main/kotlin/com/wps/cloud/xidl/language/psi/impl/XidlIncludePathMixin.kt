package com.wps.cloud.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.impl.providers.FileReferenceSet

/**
 * `include_path` 规则的 mixin 基类, 让 `include { "a/b.xidl" }` 这类结构中的
 * 字符串能 Ctrl+Click 跳转到实际被 include 的文件。
 *
 * 实现细节:
 *  - xidl 语义上 include 路径相对当前文件所在目录解析 (见 stage/core/stage.go
 *    `incFile := dir + "/" + p.Immutable.String`), 这恰好是 [FileReferenceSet]
 *    的默认行为, 无需自定义 base context;
 *  - 使用 [FileReferenceSet] 而不是手写 PsiReference 的好处:
 *      * 多段路径 `a/b/c.xidl` 每段独立成 FileReference, 可以分别跳转到目录 / 文件;
 *      * 自动提供文件名 / 目录名补全 (getVariants);
 *      * 能正确处理 rename / safe delete / find usages;
 *  - 走 mixin (composite PSI) 而非 PsiReferenceContributor(leaf STRING_LITERAL) 的
 *    原因与 [XidlAnnoStringMixin] 相同: leaf token 上的 contributor 并非在所有
 *    findReferenceAt 路径上都会被触发, composite 节点的 getReferences() 最稳定。
 */
abstract class XidlIncludePathMixin(node: ASTNode) : ASTWrapperPsiElement(node) {

    override fun getReferences(): Array<PsiReference> {
        val text = text
        // 至少是 `""` + 一个字符, 例如 "a"
        if (text.length < 3) return PsiReference.EMPTY_ARRAY
        val first = text.first()
        if (first != '"' && first != '`') return PsiReference.EMPTY_ARRAY
        val pathText = text.substring(1, text.length - 1)
        if (pathText.isEmpty()) return PsiReference.EMPTY_ARRAY
        // startInElement = 1, 跳过开头的引号, 让高亮 / 点击区域落在实际路径文本上。
        // FileReferenceSet#getAllReferences() 返回 Array<FileReference>, Kotlin 下
        // 数组不协变, 需显式转成 Array<PsiReference> 才能匹配 getReferences() 签名。
        @Suppress("UNCHECKED_CAST")
        return FileReferenceSet(pathText, this, 1, null, /* caseSensitive = */ true)
            .allReferences as Array<PsiReference>
    }

    override fun getReference(): PsiReference? = references.lastOrNull()
}
