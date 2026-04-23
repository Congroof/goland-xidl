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

public class XidlHttpResponseImpl extends ASTWrapperPsiElement implements XidlHttpResponse {

  public XidlHttpResponseImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XidlVisitor visitor) {
    visitor.visitHttpResponse(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XidlVisitor) accept((XidlVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XidlBlockMeta getBlockMeta() {
    return findChildByClass(XidlBlockMeta.class);
  }

  @Override
  @NotNull
  public List<XidlHttpHeader> getHttpHeaderList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XidlHttpHeader.class);
  }

  @Override
  @Nullable
  public XidlTypeReference getTypeReference() {
    return findChildByClass(XidlTypeReference.class);
  }

  @Override
  @Nullable
  public PsiElement getSimpleType() {
    return findChildByType(SIMPLE_TYPE);
  }

  @Override
  @Nullable
  public PsiElement getStringLiteral() {
    return findChildByType(STRING_LITERAL);
  }

}
