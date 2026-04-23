package com.wps.cloud.xidl.language.formatting

import com.intellij.formatting.ASTBlock
import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiComment
import com.intellij.psi.TokenType
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import com.wps.cloud.xidl.language.XidlLanguage
import com.wps.cloud.xidl.language.psi.XidlTypes

/**
 * Xidl 格式化入口。
 *
 * 分层策略:
 *  1) 缩进: 基于 composite 节点 (BLOCK_META / OPERATION) 挂 Indent.Normal;
 *  2) 空白/换行: 大部分走 SpacingBuilder, 少量上下文敏感的规则
 *     (典型如 `@tags { ... }` 要求单行) 在 [XidlBlock.getSpacing] 里拦截后返回
 *     自定义 Spacing;
 *  3) 其它留白全部保留用户原样 (KEEP_LINE_BREAKS 默认 true), 避免"格式化一下
 *     就大改文件"。
 */
class XidlFormattingModelBuilder : FormattingModelBuilder {

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val settings = formattingContext.codeStyleSettings
        val spacingBuilder = createSpacingBuilder(settings)
        val rootBlock = XidlBlock(formattingContext.node, null, null, spacingBuilder)
        return FormattingModelProvider.createFormattingModelForPsiFile(
            formattingContext.containingFile,
            rootBlock,
            settings,
        )
    }

    private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder {
        return SpacingBuilder(settings, XidlLanguage.INSTANCE)
            // ================= operation 布局 =================
            // 典型形状:
            //   get /a/b/c
            //     ? { ... }
            //     ! { ... }
            //     200 name
            //   ;
            // 每一段 (query/header/cookie/body/response) 前换行, 缩进一级;
            // 结尾 `;` 也换行, 和 HTTP_METHOD 同列 (indent=None 见 XidlBlock)。
            .beforeInside(XidlTypes.HTTP_QUERY, XidlTypes.OPERATION).lineBreakInCode()
            .beforeInside(XidlTypes.HTTP_HEADER, XidlTypes.OPERATION).lineBreakInCode()
            .beforeInside(XidlTypes.HTTP_COOKIE, XidlTypes.OPERATION).lineBreakInCode()
            .beforeInside(XidlTypes.HTTP_REQUEST_BODY, XidlTypes.OPERATION).lineBreakInCode()
            .beforeInside(XidlTypes.HTTP_RESPONSE, XidlTypes.OPERATION).lineBreakInCode()
            .beforeInside(XidlTypes.SEMICOLON, XidlTypes.OPERATION).lineBreakInCode()
            // HTTP_METHOD 和紧随的 `/` 之间必须有空格: `get /a/b` 而不是 `get/a/b`。
            .after(XidlTypes.HTTP_METHOD).spaces(1)

            // ================= 通用 block: `{` 后换行, `}` 前换行 =================
            // 注: `@tags / @allof / @anyof / @oneof / @<custom> { ... }` 这种注解 block
            // 需要保持单行, 由 XidlBlock.getSpacing 在 BLOCK_META 上下文里做拦截,
            // 规则这里照常写, 走 builder 时通过拦截跳过。
            .afterInside(XidlTypes.LBRACE, XidlTypes.BLOCK_META).lineBreakInCode()
            .beforeInside(XidlTypes.RBRACE, XidlTypes.BLOCK_META).lineBreakInCode()

            // 括号内侧紧贴
            .after(XidlTypes.LPAREN).spaces(0)
            .before(XidlTypes.RPAREN).spaces(0)
            .after(XidlTypes.LBRACKET).spaces(0)
            .before(XidlTypes.RBRACKET).spaces(0)
            .after(XidlTypes.LANGLE).spaces(0)
            .before(XidlTypes.RANGLE).spaces(0)
            .before(XidlTypes.LANGLE).spaces(0)

            // `? { ... }` http_query: `?` 和 `{` 之间 1 空格 (key_type 里的 `?`
            // 后面跟的是 COLON, 走下方 COLON 规则, 互不影响)
            .between(XidlTypes.QUESTION, XidlTypes.BLOCK_META).spaces(1)

            // 逗号/冒号/等号/可选标记/点/斜杠
            .before(XidlTypes.COMMA).spaces(0)
            .after(XidlTypes.COMMA).spaces(1)
            .before(XidlTypes.COLON).spaces(0)
            .after(XidlTypes.COLON).spaces(1)
            .around(XidlTypes.ASSIGN).spaces(1)
            .before(XidlTypes.QUESTION).spaces(0)
            .around(XidlTypes.DOT).spaces(0)
            // `/` 两侧默认紧贴 (http_path `/a/b` 连续); `HTTP_METHOD /` 之间由上面
            // `after(HTTP_METHOD).spaces(1)` 先匹配, 不会被这里覆盖。
            .around(XidlTypes.DIVIDE).spaces(0)

            // 分号前紧贴 (Spacing 覆盖 `lineBreakInCode` 后仍允许换行, 实际 operation
            // 里 `;` 前换行由上面 beforeInside(SEMICOLON, OPERATION) 控制)
            .before(XidlTypes.SEMICOLON).spaces(0)

            // chan 方向符: `<- T` / `-> T`
            .after(XidlTypes.CHAN_SEND).spaces(1)
            .after(XidlTypes.CHAN_RECEIVE).spaces(1)
    }
}

/**
 * `{` 和 `}` 之间保持单行 + 单个空格 的 Spacing, 给 annotation block 使用。
 * 例如 `@tags { "a", "b" }`。 minLineFeeds=0 + keepLineBreaks=false 强制在
 * 再次格式化时把多行折叠成单行。
 */
private val INLINE_BRACE_SPACING: Spacing =
    Spacing.createSpacing(1, 1, 0, /* keepLineBreaks = */ false, /* keepBlankLines = */ 0)

private val OPERATION_INNER_COMPOSITES: Set<com.intellij.psi.tree.IElementType> = setOf(
    XidlTypes.HTTP_QUERY,
    XidlTypes.HTTP_HEADER,
    XidlTypes.HTTP_COOKIE,
    XidlTypes.HTTP_REQUEST_BODY,
    XidlTypes.HTTP_RESPONSE,
)

/**
 * Xidl 的 AST Block。
 *
 * 缩进策略 (getIndent):
 *  - BLOCK_META 的子节点 (除 `{` `}` 外): 相对外层缩进一级;
 *  - OPERATION 的子节点里:
 *      * HTTP_QUERY / HTTP_HEADER / HTTP_COOKIE / HTTP_REQUEST_BODY / HTTP_RESPONSE
 *        相对 operation 首行缩进一级;
 *      * 其余 (HTTP_METHOD / path / annotations / `;`) 保持与外层同列,
 *        让 `;` 与 `get` 对齐, 符合 xidl 社区通用写法。
 *
 * Spacing 策略 (getSpacing):
 *  - 多参数注解 `@xxx { ... }`: 让 `{` 后 / `}` 前强制为单空格, 覆盖掉通用
 *    BLOCK_META 的"{ 后换行"规则;
 *  - 其它情况委托给 SpacingBuilder。
 */
private class XidlBlock(
    node: ASTNode,
    wrap: Wrap?,
    alignment: Alignment?,
    private val spacingBuilder: SpacingBuilder,
) : AbstractBlock(node, wrap, alignment) {

    override fun buildChildren(): List<Block> {
        val result = mutableListOf<Block>()
        var child: ASTNode? = myNode.firstChildNode
        while (child != null) {
            if (shouldCreateBlockFor(child)) {
                result.add(XidlBlock(child, null, null, spacingBuilder))
            }
            child = child.treeNext
        }
        return result
    }

    private fun shouldCreateBlockFor(child: ASTNode): Boolean {
        if (child.elementType === TokenType.WHITE_SPACE) return false
        return child.textRange.length > 0
    }

    override fun getIndent(): Indent {
        val parent = myNode.treeParent ?: return Indent.getNoneIndent()
        val parentType = parent.elementType
        val selfType = myNode.elementType

        if (parentType == XidlTypes.BLOCK_META) {
            return when (selfType) {
                XidlTypes.LBRACE, XidlTypes.RBRACE -> Indent.getNoneIndent()
                else -> Indent.getNormalIndent()
            }
        }

        if (parentType == XidlTypes.OPERATION) {
            return if (selfType in OPERATION_INNER_COMPOSITES) {
                Indent.getNormalIndent()
            } else {
                Indent.getNoneIndent()
            }
        }

        return Indent.getNoneIndent()
    }

    override fun getSpacing(child1: Block?, child2: Block): Spacing? {
        // 注解 block `@tags { ... }` 折叠成单行: 拦截 `{` 后 / `}` 前的默认"换行"规则
        if (myNode.elementType == XidlTypes.BLOCK_META && isAnnotationBlock(myNode)) {
            val c1Type = (child1 as? ASTBlock)?.node?.elementType
            val c2Type = (child2 as? ASTBlock)?.node?.elementType
            if (c1Type == XidlTypes.LBRACE || c2Type == XidlTypes.RBRACE) {
                return INLINE_BRACE_SPACING
            }
            // 其余 token 间 (例如逗号) 继续走 SpacingBuilder
        }
        return spacingBuilder.getSpacing(this, child1, child2)
    }

    override fun getChildAttributes(newChildIndex: Int): ChildAttributes {
        val indent = when (myNode.elementType) {
            XidlTypes.BLOCK_META, XidlTypes.OPERATION -> Indent.getNormalIndent()
            else -> Indent.getNoneIndent()
        }
        return ChildAttributes(indent, null)
    }

    override fun isLeaf(): Boolean = myNode.firstChildNode == null
}

/**
 * 判定一个 BLOCK_META 节点是否是"注解 block" (如 `@tags { "a", "b" }`)。
 *
 * 判定方式: 取该 BLOCK_META 的前一个非空白/注释兄弟节点, 若其 element type
 * 为 MULTI_PARAM_ANNOTATION 或 CUSTOM_ANNOTATION, 则认为是注解 block。
 *
 * 设计说明:
 *  - include/schema/cmd/tag/def/endpoints/mdepends 等对象的 BLOCK_META 前一个
 *    兄弟都是关键字/名字节点, 天然不会被这里命中, 所以不会被误折叠;
 *  - 如果未来添加了新的"希望单行"结构, 可以扩展这里的白名单。
 */
private fun isAnnotationBlock(blockMetaNode: ASTNode): Boolean {
    if (blockMetaNode.elementType != XidlTypes.BLOCK_META) return false
    var prev = blockMetaNode.treePrev
    while (prev != null && (
            prev.elementType === TokenType.WHITE_SPACE ||
                prev.psi is PsiComment
            )
    ) {
        prev = prev.treePrev
    }
    val type = prev?.elementType ?: return false
    return type == XidlTypes.MULTI_PARAM_ANNOTATION || type == XidlTypes.CUSTOM_ANNOTATION
}
