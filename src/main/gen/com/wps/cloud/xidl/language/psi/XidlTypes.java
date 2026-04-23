// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.wps.cloud.xidl.language.psi.impl.*;

public interface XidlTypes {

  IElementType ANNO_STRING = new XidlElementType("ANNO_STRING");
  IElementType BLOCK_META = new XidlElementType("BLOCK_META");
  IElementType FUNC_NAME = new XidlElementType("FUNC_NAME");
  IElementType HTTP_COOKIE = new XidlElementType("HTTP_COOKIE");
  IElementType HTTP_HEADER = new XidlElementType("HTTP_HEADER");
  IElementType HTTP_QUERY = new XidlElementType("HTTP_QUERY");
  IElementType HTTP_REQUEST_BODY = new XidlElementType("HTTP_REQUEST_BODY");
  IElementType HTTP_RESPONSE = new XidlElementType("HTTP_RESPONSE");
  IElementType INCLUDE_PATH = new XidlElementType("INCLUDE_PATH");
  IElementType OPERATION = new XidlElementType("OPERATION");
  IElementType SCHEMA_NAME = new XidlElementType("SCHEMA_NAME");
  IElementType SCHEMA_OBJECT = new XidlElementType("SCHEMA_OBJECT");
  IElementType TYPE_REFERENCE = new XidlElementType("TYPE_REFERENCE");

  IElementType ASSIGN = new XidlTokenType("=");
  IElementType BLOCK_COMMENT = new XidlTokenType("BLOCK_COMMENT");
  IElementType BOOLEAN_LITERAL = new XidlTokenType("BOOLEAN_LITERAL");
  IElementType CHAN = new XidlTokenType("chan");
  IElementType CHAN_RECEIVE = new XidlTokenType("->");
  IElementType CHAN_SEND = new XidlTokenType("<-");
  IElementType COLON = new XidlTokenType(":");
  IElementType COMMA = new XidlTokenType(",");
  IElementType CUSTOM_ANNOTATION = new XidlTokenType("CUSTOM_ANNOTATION");
  IElementType DIVIDE = new XidlTokenType("/");
  IElementType DOT = new XidlTokenType(".");
  IElementType FLOAT_LITERAL = new XidlTokenType("FLOAT_LITERAL");
  IElementType HTTP_METHOD = new XidlTokenType("HTTP_METHOD");
  IElementType IDENTIFIER = new XidlTokenType("IDENTIFIER");
  IElementType INTEGER_LITERAL = new XidlTokenType("INTEGER_LITERAL");
  IElementType KEYWORD = new XidlTokenType("KEYWORD");
  IElementType LANGLE = new XidlTokenType("<");
  IElementType LBRACE = new XidlTokenType("{");
  IElementType LBRACKET = new XidlTokenType("[");
  IElementType LINE_COMMENT = new XidlTokenType("LINE_COMMENT");
  IElementType LPAREN = new XidlTokenType("(");
  IElementType MAP = new XidlTokenType("map");
  IElementType MULTIPLY = new XidlTokenType("*");
  IElementType MULTI_PARAM_ANNOTATION = new XidlTokenType("MULTI_PARAM_ANNOTATION");
  IElementType QUESTION = new XidlTokenType("?");
  IElementType RANGLE = new XidlTokenType(">");
  IElementType RBRACE = new XidlTokenType("}");
  IElementType RBRACKET = new XidlTokenType("]");
  IElementType RPAREN = new XidlTokenType(")");
  IElementType SEMICOLON = new XidlTokenType(";");
  IElementType SIMPLE_TYPE = new XidlTokenType("SIMPLE_TYPE");
  IElementType SINGLE_PARAM_ANNOTATION = new XidlTokenType("SINGLE_PARAM_ANNOTATION");
  IElementType STRING_LITERAL = new XidlTokenType("STRING_LITERAL");
  IElementType ZERO_PARAM_ANNOTATION = new XidlTokenType("ZERO_PARAM_ANNOTATION");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == ANNO_STRING) {
        return new XidlAnnoStringImpl(node);
      }
      else if (type == BLOCK_META) {
        return new XidlBlockMetaImpl(node);
      }
      else if (type == FUNC_NAME) {
        return new XidlFuncNameImpl(node);
      }
      else if (type == HTTP_COOKIE) {
        return new XidlHttpCookieImpl(node);
      }
      else if (type == HTTP_HEADER) {
        return new XidlHttpHeaderImpl(node);
      }
      else if (type == HTTP_QUERY) {
        return new XidlHttpQueryImpl(node);
      }
      else if (type == HTTP_REQUEST_BODY) {
        return new XidlHttpRequestBodyImpl(node);
      }
      else if (type == HTTP_RESPONSE) {
        return new XidlHttpResponseImpl(node);
      }
      else if (type == INCLUDE_PATH) {
        return new XidlIncludePathImpl(node);
      }
      else if (type == OPERATION) {
        return new XidlOperationImpl(node);
      }
      else if (type == SCHEMA_NAME) {
        return new XidlSchemaNameImpl(node);
      }
      else if (type == SCHEMA_OBJECT) {
        return new XidlSchemaObjectImpl(node);
      }
      else if (type == TYPE_REFERENCE) {
        return new XidlTypeReferenceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
