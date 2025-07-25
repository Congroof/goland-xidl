package com.wps.cloud.xidl.language.reference

import com.intellij.openapi.util.TextRange
import com.intellij.psi.AbstractElementManipulator
import com.intellij.util.IncorrectOperationException
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
//        return element.setName(newText) as XidlTypeReferenceImpl
        return element
    }

    override fun getRangeInElement(element: XidlTypeReferenceImpl): TextRange {
        return TextRange(0, element.textLength)
    }
}
