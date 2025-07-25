package com.wps.cloud.xidl.language.completion

import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.util.ProcessingContext

class XidlTemplateCompletionProvider : CompletionProvider<CompletionParameters>() {
    override fun addCompletions(parameters: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {

        // schema 模板
        resultSet.addElement(
            LookupElementBuilder.create("schema")
                .withPresentableText("schema")
                .withTypeText("Template")
                .withIcon(com.intellij.util.PlatformIcons.CLASS_ICON)
                .withInsertHandler { insertContext, _ ->
                    val template = """schema reply {
  greeting: string
}"""
                    val adjustedTemplate = adjustIndentation(insertContext, template)
                    insertContext.document.replaceString(
                        insertContext.startOffset,
                        insertContext.selectionEndOffset,
                        adjustedTemplate
                    )
                }
        )

        // func模板
        resultSet.addElement(
            LookupElementBuilder.create("func")
                .withPresentableText("func")
                .withTypeText("Template")
                .withIcon(com.intellij.util.PlatformIcons.FUNCTION_ICON)
                .withInsertHandler { insertContext, _ ->
                    val template = """func GetAccountInfo(accountId int64) (*v1_account)"""
                    val adjustedTemplate = adjustIndentation(insertContext, template)
                    insertContext.document.replaceString(
                        insertContext.startOffset,
                        insertContext.selectionEndOffset,
                        adjustedTemplate
                    )
                }
        )

        // endpoints模板
        resultSet.addElement(
            LookupElementBuilder.create("endpoints")
                .withPresentableText("endpoints")
                .withTypeText("Template")
                .withIcon(com.intellij.util.PlatformIcons.WEB_ICON)
                .withInsertHandler { insertContext, _ ->
                    val template = """endpoints {
  @summary "[api]注册第三方认证结果"
  @desc "[api]注册第三方认证结果"
  @name "v1_third_auth_register"
  @tags {"登录", "api"}
  post /login/api/v1/third/auth/register
    . third_auth_register_request
    200 empty
  ;
}"""
                    val adjustedTemplate = adjustIndentation(insertContext, template)
                    insertContext.document.replaceString(
                        insertContext.startOffset,
                        insertContext.selectionEndOffset,
                        adjustedTemplate
                    )
                }
        )

        // get 模板
        resultSet.addElement(
            LookupElementBuilder.create("get")
                .withPresentableText("get")
                .withTypeText("Template")
                .withIcon(com.intellij.util.PlatformIcons.WEB_ICON)
                .withInsertHandler { insertContext, _ ->
                    val template = """@desc "获取公钥信息"
@name "v7_get_privilege_pubkey"
get /v7/privileges/pubkey
  ? {
    @desc "公钥格式, 默认default(base64), 可选项 default, pem, jwk"
    type?: string
  }
  200 v7_privilege_pubkey
;"""
                    val adjustedTemplate = adjustIndentation(insertContext, template)
                    insertContext.document.replaceString(
                        insertContext.startOffset,
                        insertContext.selectionEndOffset,
                        adjustedTemplate
                    )
                }
        )

        // post 模板
        resultSet.addElement(
            LookupElementBuilder.create("post")
                .withPresentableText("post")
                .withTypeText("Template")
                .withIcon(com.intellij.util.PlatformIcons.WEB_ICON)
                .withInsertHandler { insertContext, _ ->
                    val template = """@desc "[dev]企业授权"
@name "v1_dev_company_entitle"
post /privileges/dev/v1/companies/{@xtype "int64" company_id:string}/entitle
  . v1_dev_company_entitle_req
  200 empty
;"""
                    val adjustedTemplate = adjustIndentation(insertContext, template)
                    insertContext.document.replaceString(
                        insertContext.startOffset,
                        insertContext.selectionEndOffset,
                        adjustedTemplate
                    )
                }
        )

    }

    private fun adjustIndentation(insertContext: com.intellij.codeInsight.completion.InsertionContext, template: String): String {
        val document = insertContext.document
        val offset = insertContext.startOffset
        val lineNumber = document.getLineNumber(offset)
        val lineStartOffset = document.getLineStartOffset(lineNumber)
        val textBeforeCursor = document.getText(com.intellij.openapi.util.TextRange(lineStartOffset, offset))
        val currentIndent = textBeforeCursor.takeWhile { it.isWhitespace() }
        val lines = template.split("\n")
        val adjustedLines = lines.mapIndexed { index, line ->
            if (index == 0) {
                line
            } else if (line.isBlank()) {
                line
            } else {
                currentIndent + line
            }
        }

        return adjustedLines.joinToString("\n")
    }
}
