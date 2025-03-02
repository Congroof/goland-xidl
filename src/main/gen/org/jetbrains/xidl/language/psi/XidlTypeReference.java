// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;

public interface XidlTypeReference extends XidlNamedElement {

  @NotNull
  PsiElement getIdentifier();

  @NotNull
  String getName();

  @NotNull
  PsiElement setName(@NotNull String newName);

  @NotNull
  PsiElement getNameIdentifier();

  @NotNull
  PsiReference getReference();

}
