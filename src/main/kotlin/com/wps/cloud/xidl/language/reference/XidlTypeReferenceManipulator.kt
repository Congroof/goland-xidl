package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.util.IncorrectOperationException
import com.wps.cloud.xidl.language.psi.impl.XidlPsiImplUtil
import com.wps.cloud.xidl.language.psi.impl.XidlTypeReferenceImpl

class XidlTypeReferenceManipulator : AbstractElementManipulator<XidlTypeReferenceImpl>() {
    @Throws(IncorrectOperationException::class)
    override fun handleContentChange(
        element: XidlTypeReferenceImpl,
        range: TextRange,
        newContent: String
    ): XidlTypeReferenceImpl {
        val oldText = element.text
        val newText = oldText.substring(0, range.startOffset) + newContent + oldText.substring(range.endOffset)

        val identifierRegex = Regex("^[a-zA-Z_][a-zA-Z0-9_]*$")
        if (!identifierRegex.matches(newText)) {
            throw IncorrectOperationException("Invalid Xidl type reference: $newText")
        }

        val replacement = XidlPsiImplUtil.createSchemaNameIdentifier(element.project, newText)
            ?: throw IncorrectOperationException("Failed to build replacement for: $newText")

        val identifier = element.identifier
        element.node.replaceChild(identifier.node, replacement.node)
        return element
    }

    override fun getRangeInElement(element: XidlTypeReferenceImpl): TextRange {
        return TextRange(0, element.textLength)
    }
}
