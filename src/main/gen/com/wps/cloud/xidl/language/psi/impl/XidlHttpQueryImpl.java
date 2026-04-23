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

public class XidlHttpQueryImpl extends ASTWrapperPsiElement implements XidlHttpQuery {

  public XidlHttpQueryImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XidlVisitor visitor) {
    visitor.visitHttpQuery(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XidlVisitor) accept((XidlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XidlBlockMeta getBlockMeta() {
    return findNotNullChildByClass(XidlBlockMeta.class);
  }

}
