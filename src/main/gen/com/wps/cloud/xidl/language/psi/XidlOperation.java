// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XidlOperation extends PsiElement {

  @NotNull
  List<XidlAnnoString> getAnnoStringList();

  @NotNull
  List<XidlBlockMeta> getBlockMetaList();

  @NotNull
  List<XidlHttpCookie> getHttpCookieList();

  @NotNull
  List<XidlHttpHeader> getHttpHeaderList();

  @NotNull
  List<XidlHttpQuery> getHttpQueryList();

  @NotNull
  List<XidlHttpRequestBody> getHttpRequestBodyList();

  @Nullable
  XidlHttpResponse getHttpResponse();

  @NotNull
  PsiElement getHttpMethod();

}
