// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XidlHttpResponse extends PsiElement {

  @Nullable
  XidlBlockMeta getBlockMeta();

  @NotNull
  List<XidlHttpHeader> getHttpHeaderList();

  @Nullable
  XidlTypeReference getTypeReference();

  @Nullable
  PsiElement getSimpleType();

  @Nullable
  PsiElement getStringLiteral();

}
