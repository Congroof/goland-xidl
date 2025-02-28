package org.jetbrains.xidl.language

import com.intellij.icons.AllIcons
import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension
import com.intellij.lang.Language
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import org.jetbrains.xidl.language.psi.XidlFile
import javax.swing.Icon


internal class XidlStructureAwareNavbar : StructureAwareNavBarModelExtension() {
    @get:NotNull
    override val language: Language
        get() = XidlLanguage.INSTANCE

    @Nullable
    override fun getPresentableText(`object`: Any): String? {
        if (`object` is XidlFile) {
            return `object`.name
        }
        return null
    }

    @Nullable
    override fun getIcon(`object`: Any): Icon? {
        return AllIcons.Nodes.Property
    }
}