package org.jetbrains.xidl.language.commenter

import com.intellij.lang.Commenter
import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.tree.IElementType

class XidlCommenter : Commenter {
    override fun getLineCommentPrefix(): String = "//"

    override fun getBlockCommentPrefix(): String = "/*"

    override fun getBlockCommentSuffix(): String = "*/"

    override fun getCommentedBlockCommentPrefix(): String = "/*"

    override fun getCommentedBlockCommentSuffix(): String = "*/"
}