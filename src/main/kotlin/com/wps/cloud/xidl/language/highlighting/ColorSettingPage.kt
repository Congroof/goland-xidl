package com.wps.cloud.xidl.language.highlighting

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
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
            // mapi
            func CreateOauthAuth(platformId, platformName, platformLogo, h5Ua string, oauthCfg *oauth_cfg_request) (*v1_oauth_auth_cfg)

            /* operation  */
            @server "public"
            endpoints {
                @summary "[u]获取otp二维码"
                @desc "[u]获取otp二维码"
                @name "u_v1_get_otp_qr"
                @tags {"管理后台otp认证相关","user"}
                @middleware_replace_map {"auth:validate-or-admin-session"}
                post /login/u/v1/otp/{
                    @desc "用户id"
                    @xtype "int64"
                    user_id: string[1,]
                }/qr/read
                ~ {
                    @desc "用户会话"
                    "wps_sid": string[1,127]
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
            AttributesDescriptor("注释", DefaultLanguageHighlighterColors.LINE_COMMENT),
            AttributesDescriptor("关键字", DefaultLanguageHighlighterColors.KEYWORD),
            AttributesDescriptor("标识符", DefaultLanguageHighlighterColors.IDENTIFIER),
            AttributesDescriptor("字符串", DefaultLanguageHighlighterColors.STRING),
            AttributesDescriptor("数字", DefaultLanguageHighlighterColors.NUMBER),
            AttributesDescriptor("括号", DefaultLanguageHighlighterColors.BRACES),
            AttributesDescriptor("注解", DefaultLanguageHighlighterColors.METADATA),
            AttributesDescriptor("类型引用", DefaultLanguageHighlighterColors.CLASS_REFERENCE),
            AttributesDescriptor("函数名", DefaultLanguageHighlighterColors.FUNCTION_DECLARATION),

        )
    }
}
