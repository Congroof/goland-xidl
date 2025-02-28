package org.jetbrains.xidl.language.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import org.jetbrains.xidl.language.XidlFileType
import org.jetbrains.xidl.language.XidlLanguage


class XidlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, XidlLanguage.INSTANCE) {

    override fun getFileType(): FileType = XidlFileType.INSTANCE

    override fun toString(): String = "Xidl File"
}