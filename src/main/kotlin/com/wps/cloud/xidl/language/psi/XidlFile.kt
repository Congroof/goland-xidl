package com.wps.cloud.xidl.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.wps.cloud.xidl.language.XidlFileType
import com.wps.cloud.xidl.language.XidlLanguage


class XidlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, XidlLanguage.INSTANCE) {

    override fun getFileType(): FileType = XidlFileType.INSTANCE

    override fun toString(): String = "Xidl File"
}
