// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XidlSchemaObject extends PsiElement {

  @NotNull
  List<XidlAnnoString> getAnnoStringList();

  @NotNull
  List<XidlBlockMeta> getBlockMetaList();

  @Nullable
  XidlSchemaName getSchemaName();

}
