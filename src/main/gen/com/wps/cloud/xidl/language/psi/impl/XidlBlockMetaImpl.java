// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.wps.cloud.xidl.language.psi.XidlTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.wps.cloud.xidl.language.psi.*;

public class XidlBlockMetaImpl extends ASTWrapperPsiElement implements XidlBlockMeta {

  public XidlBlockMetaImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XidlVisitor visitor) {
    visitor.visitBlockMeta(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XidlVisitor) accept((XidlVisitor)visitor);
    else super.accept(visitor);
  }

}
