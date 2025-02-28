// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XidlOperation extends PsiElement {

  @NotNull
  List<XidlDataType> getDataTypeList();

  @NotNull
  List<XidlTypeReference> getTypeReferenceList();

  @NotNull
  PsiElement getKeyword();

}
