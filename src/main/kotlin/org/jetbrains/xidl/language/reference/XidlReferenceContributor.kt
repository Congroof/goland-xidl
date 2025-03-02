package org.jetbrains.xidl.language.reference

import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.util.ProcessingContext
import org.jetbrains.xidl.language.psi.XidlTypeReference
import org.jetbrains.xidl.language.psi.XidlTypes


class XidlReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(XidlTypes.TYPE_REFERENCE),
            object : PsiReferenceProvider() {
                override fun getReferencesByElement(
                    element: PsiElement,
                    context: ProcessingContext
                ): Array<PsiReference> {
                    val typeRef = element as XidlTypeReference
                    val ref = typeRef.reference as XidlReference
                    val results = ref.multiResolve(false)
                    return results.map { it.element as PsiElement }.map { XidlReference(it) }.toTypedArray()
                }
            }
        )
    }
}