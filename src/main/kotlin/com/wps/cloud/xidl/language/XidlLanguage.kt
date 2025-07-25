package com.wps.cloud.xidl.language

import com.intellij.lang.Language

class XidlLanguage private constructor() : Language("Xidl") {
    companion object {
        @JvmStatic
        val INSTANCE = XidlLanguage()
    }
}
