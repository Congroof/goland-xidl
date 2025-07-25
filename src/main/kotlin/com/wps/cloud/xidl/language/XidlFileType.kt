package com.wps.cloud.xidl.language

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

class XidlFileType private constructor() : LanguageFileType(XidlLanguage.INSTANCE) {
    companion object {
        @JvmStatic
        val INSTANCE = XidlFileType()
    }

    override fun getName(): String = "Xidl"
    override fun getDescription(): String = "Xidl language file"
    override fun getDefaultExtension(): String = "xidl"
    override fun getIcon(): Icon = IconLoader.getIcon("/icons/icon.png", XidlLanguage::class.java)

}
