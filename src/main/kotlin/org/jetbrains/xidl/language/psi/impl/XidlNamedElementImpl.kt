package org.jetbrains.xidl.language.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import org.jetbrains.xidl.language.psi.XidlNamedElement


abstract class XidlNamedElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), XidlNamedElement

