package com.wps.cloud.xidl.language.highlighting

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import org.jetbrains.annotations.NotNull
import javax.swing.Icon


internal class ColorSettingPage : ColorSettingsPage {
    override fun getIcon(): Icon? {
        return null
    }

    @NotNull
    override fun getHighlighter(): SyntaxHighlighter {
        return XidlSyntaxHighlighter()
    }

    @NotNull
    override fun getDemoText(): String {
        return """
            // 行注释
            /* 块注释 */

            app demo
            version "1.0.0"

            @deprecated
            @desc "用户信息"
            schema user {
                id: int64
                name: string[1, 64]
                avatar?: RawMessage
            }

            func CreateOauthAuth(platformId, platformName string, oauthCfg *oauth_cfg_request) (*v1_oauth_auth_cfg)

            @server "public"
            @prefix_server {"admin", "public"}
            endpoints {
                @summary "获取 otp 二维码"
                @name "u_v1_get_otp_qr"
                @tags {"otp认证", "user"}
                @middleware_replace_map {"auth:validate-or-admin-session"}
                post /login/u/v1/otp/{
                    @xtype "int64"
                    user_id: string[1,]
                }/qr/read
                ~ {
                    "wps_sid": string[1, 127]
                }
                . v1_otp_qr_req_body
                200 v1_otp_qr_resp
              ;
            }
        """.trimIndent()
    }

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey>? {
        return null
    }

    override fun getAttributeDescriptors(): @NotNull Array<AttributesDescriptor> {
        return DESCRIPTORS
    }

    override fun getColorDescriptors(): @NotNull Array<ColorDescriptor> {
        return ColorDescriptor.EMPTY_ARRAY
    }

    @NotNull
    override fun getDisplayName(): String {
        return "Xidl"
    }

    companion object {
        private val DESCRIPTORS = arrayOf<AttributesDescriptor>(
            AttributesDescriptor("注释//行注释", XidlSyntaxHighlighter.LINE_COMMENT),
            AttributesDescriptor("注释//块注释", XidlSyntaxHighlighter.BLOCK_COMMENT),
            AttributesDescriptor("关键字//对象关键字 (schema/cmd/def/…)", XidlSyntaxHighlighter.KEYWORD),
            AttributesDescriptor("关键字//HTTP 方法 (get/post/…)", XidlSyntaxHighlighter.HTTP_METHOD),
            AttributesDescriptor("关键字//内建类型 (int/string/…)", XidlSyntaxHighlighter.BUILTIN_TYPE),
            AttributesDescriptor("标识符", XidlSyntaxHighlighter.IDENTIFIER),
            AttributesDescriptor("字面量//字符串", XidlSyntaxHighlighter.STRING),
            AttributesDescriptor("字面量//数字", XidlSyntaxHighlighter.NUMBER),
            AttributesDescriptor("字面量//布尔", XidlSyntaxHighlighter.BOOLEAN),
            AttributesDescriptor("括号//花括号 { }", XidlSyntaxHighlighter.BRACES),
            AttributesDescriptor("括号//方括号 [ ]", XidlSyntaxHighlighter.BRACKETS),
            AttributesDescriptor("括号//圆括号 ( )", XidlSyntaxHighlighter.PARENTHESES),
            AttributesDescriptor("注解", XidlSyntaxHighlighter.ANNOTATION),
            AttributesDescriptor("符号//分号", XidlSyntaxHighlighter.SEMICOLON),
            AttributesDescriptor("符号//逗号", XidlSyntaxHighlighter.COMMA),
            AttributesDescriptor("符号//点号", XidlSyntaxHighlighter.DOT),
            AttributesDescriptor("符号//运算符", XidlSyntaxHighlighter.SIGN),
        )
    }
}
