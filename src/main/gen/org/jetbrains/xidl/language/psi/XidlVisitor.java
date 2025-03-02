// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.psi;

import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiElement;

public class XidlVisitor extends PsiElementVisitor {

  public void visitDataType(@NotNull XidlDataType o) {
    visitPsiElement(o);
  }

  public void visitOperation(@NotNull XidlOperation o) {
    visitPsiElement(o);
  }

  public void visitSchemaDeclaration(@NotNull XidlSchemaDeclaration o) {
    visitNamedElement(o);
  }

  public void visitTypeReference(@NotNull XidlTypeReference o) {
    visitNamedElement(o);
  }

  public void visitNamedElement(@NotNull XidlNamedElement o) {
    visitPsiElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
