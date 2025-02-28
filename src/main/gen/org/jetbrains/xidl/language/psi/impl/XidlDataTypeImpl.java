// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.jetbrains.xidl.language.psi.XidlTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.jetbrains.xidl.language.psi.*;

public class XidlDataTypeImpl extends ASTWrapperPsiElement implements XidlDataType {

  public XidlDataTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XidlVisitor visitor) {
    visitor.visitDataType(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XidlVisitor) accept((XidlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getIdentifier() {
    return findChildByType(IDENTIFIER);
  }

  @Override
  @Nullable
  public PsiElement getSimpleType() {
    return findChildByType(SIMPLE_TYPE);
  }

}
