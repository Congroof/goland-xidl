// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.xidl.language.psi.impl.*;

public interface XidlTypes {

  IElementType DATA_TYPE = new XidlElementType("DATA_TYPE");
  IElementType FUNC_NAME = new XidlElementType("FUNC_NAME");
  IElementType OPERATION = new XidlElementType("OPERATION");
  IElementType SCHEMA_DECLARATION = new XidlElementType("SCHEMA_DECLARATION");
  IElementType TYPE_REFERENCE = new XidlElementType("TYPE_REFERENCE");

  IElementType ANNOTATION = new XidlTokenType("annotation");
  IElementType BLOCK_COMMENT = new XidlTokenType("block_comment");
  IElementType IDENTIFIER = new XidlTokenType("identifier");
  IElementType KEYWORD = new XidlTokenType("keyword");
  IElementType LEFT_BRACE = new XidlTokenType("left_brace");
  IElementType LEFT_BRACKET = new XidlTokenType("left_bracket");
  IElementType LEFT_PAREN = new XidlTokenType("left_paren");
  IElementType LINE_COMMENT = new XidlTokenType("line_comment");
  IElementType LITERAL_BOOLEAN = new XidlTokenType("literal_boolean");
  IElementType LITERAL_FLOAT = new XidlTokenType("literal_float");
  IElementType LITERAL_INTEGER = new XidlTokenType("literal_integer");
  IElementType LITERAL_STRING = new XidlTokenType("literal_string");
  IElementType RIGHT_BRACE = new XidlTokenType("right_brace");
  IElementType RIGHT_BRACKET = new XidlTokenType("right_bracket");
  IElementType RIGHT_PAREN = new XidlTokenType("right_paren");
  IElementType SIMPLE_TYPE = new XidlTokenType("simple_type");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == DATA_TYPE) {
        return new XidlDataTypeImpl(node);
      }
      else if (type == FUNC_NAME) {
        return new XidlFuncNameImpl(node);
      }
      else if (type == OPERATION) {
        return new XidlOperationImpl(node);
      }
      else if (type == SCHEMA_DECLARATION) {
        return new XidlSchemaDeclarationImpl(node);
      }
      else if (type == TYPE_REFERENCE) {
        return new XidlTypeReferenceImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
