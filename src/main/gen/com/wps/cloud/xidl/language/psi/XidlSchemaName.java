// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XidlSchemaName extends XidlNamedElement {

  @Nullable
  PsiElement getIdentifier();

  @Nullable
  PsiElement getStringLiteral();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String newName);

  @NotNull
  PsiElement getNameIdentifier();

}
