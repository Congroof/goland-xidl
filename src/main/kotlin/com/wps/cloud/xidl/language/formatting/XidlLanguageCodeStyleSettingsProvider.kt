package com.wps.cloud.xidl.language.formatting

import com.intellij.lang.Language
import com.intellij.psi.codeStyle.*
import com.wps.cloud.xidl.language.XidlLanguage

class XidlLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    override fun getLanguage(): Language = XidlLanguage.INSTANCE

    override fun getCodeSample(settingsType: SettingsType): String {
        return ""
    }

    override fun customizeDefaults(commonSettings: CommonCodeStyleSettings, indentOptions: CommonCodeStyleSettings.IndentOptions) {
        indentOptions.INDENT_SIZE = 2
        indentOptions.CONTINUATION_INDENT_SIZE = 2
        indentOptions.TAB_SIZE = 2
        indentOptions.USE_TAB_CHARACTER = false
    }

    override fun getIndentOptionsEditor(): com.intellij.application.options.IndentOptionsEditor? {
        return com.intellij.application.options.SmartIndentOptionsEditor()
    }

    override fun customizeSettings(consumer: CodeStyleSettingsCustomizable, settingsType: SettingsType) {

    }
}


