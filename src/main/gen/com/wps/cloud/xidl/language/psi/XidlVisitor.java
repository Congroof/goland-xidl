// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class XidlVisitor extends PsiElementVisitor {

  public void visitBlockMeta(@NotNull XidlBlockMeta o) {
    visitPsiElement(o);
  }

  public void visitFuncName(@NotNull XidlFuncName o) {
    visitPsiElement(o);
  }

  public void visitSchemaName(@NotNull XidlSchemaName o) {
    visitNamedElement(o);
  }

  public void visitSchemaObject(@NotNull XidlSchemaObject o) {
    visitPsiElement(o);
  }

  public void visitTypeReference(@NotNull XidlTypeReference o) {
    visitPsiElement(o);
  }

  public void visitNamedElement(@NotNull XidlNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
