// This is a generated file. Not intended for manual editing.
package org.jetbrains.xidl.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static org.jetbrains.xidl.language.psi.XidlTypes.*;
import static com.intellij.lang.parser.GeneratedParserUtilBase.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class XidlParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    r = parse_root_(t, b);
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b) {
    return parse_root_(t, b, 0);
  }

  static boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return xidlFile(b, l + 1);
  }

  /* ********************************************************** */
  // annotation (literal_string | literal_strings)?
  static boolean annotation_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_item")) return false;
    if (!nextTokenIs(b, ANNOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ANNOTATION);
    r = r && annotation_item_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (literal_string | literal_strings)?
  private static boolean annotation_item_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_item_1")) return false;
    annotation_item_1_0(b, l + 1);
    return true;
  }

  // literal_string | literal_strings
  private static boolean annotation_item_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation_item_1_0")) return false;
    boolean r;
    r = consumeToken(b, LITERAL_STRING);
    if (!r) r = literal_strings(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // line_comment|block_comment
  static boolean comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment")) return false;
    if (!nextTokenIs(b, "", BLOCK_COMMENT, LINE_COMMENT)) return false;
    boolean r;
    r = consumeToken(b, LINE_COMMENT);
    if (!r) r = consumeToken(b, BLOCK_COMMENT);
    return r;
  }

  /* ********************************************************** */
  // "~" key_type_items
  static boolean cookie(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cookie")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "~");
    r = r && key_type_items(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (integer_scope? ("*"? (simple_type | identifier)) integer_scope?) | float_data_type
  public static boolean data_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, DATA_TYPE, "<data type>");
    r = data_type_0(b, l + 1);
    if (!r) r = float_data_type(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // integer_scope? ("*"? (simple_type | identifier)) integer_scope?
  private static boolean data_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type_0_0(b, l + 1);
    r = r && data_type_0_1(b, l + 1);
    r = r && data_type_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // integer_scope?
  private static boolean data_type_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_0")) return false;
    integer_scope(b, l + 1);
    return true;
  }

  // "*"? (simple_type | identifier)
  private static boolean data_type_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type_0_1_0(b, l + 1);
    r = r && data_type_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "*"?
  private static boolean data_type_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_1_0")) return false;
    consumeToken(b, "*");
    return true;
  }

  // simple_type | identifier
  private static boolean data_type_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_1_1")) return false;
    boolean r;
    r = consumeToken(b, SIMPLE_TYPE);
    if (!r) r = consumeToken(b, IDENTIFIER);
    return r;
  }

  // integer_scope?
  private static boolean data_type_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_2")) return false;
    integer_scope(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // integer_scope? ("*"? ("float32" | "float64")) float_scope?
  static boolean float_data_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = float_data_type_0(b, l + 1);
    r = r && float_data_type_1(b, l + 1);
    r = r && float_data_type_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // integer_scope?
  private static boolean float_data_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type_0")) return false;
    integer_scope(b, l + 1);
    return true;
  }

  // "*"? ("float32" | "float64")
  private static boolean float_data_type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = float_data_type_1_0(b, l + 1);
    r = r && float_data_type_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "*"?
  private static boolean float_data_type_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type_1_0")) return false;
    consumeToken(b, "*");
    return true;
  }

  // "float32" | "float64"
  private static boolean float_data_type_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type_1_1")) return false;
    boolean r;
    r = consumeToken(b, "float32");
    if (!r) r = consumeToken(b, "float64");
    return r;
  }

  // float_scope?
  private static boolean float_data_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_data_type_2")) return false;
    float_scope(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "[" ((literal_float "," literal_float) | (literal_float ",") | ("," literal_float))? "]"
  static boolean float_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && float_scope_1(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((literal_float "," literal_float) | (literal_float ",") | ("," literal_float))?
  private static boolean float_scope_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope_1")) return false;
    float_scope_1_0(b, l + 1);
    return true;
  }

  // (literal_float "," literal_float) | (literal_float ",") | ("," literal_float)
  private static boolean float_scope_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = float_scope_1_0_0(b, l + 1);
    if (!r) r = float_scope_1_0_1(b, l + 1);
    if (!r) r = float_scope_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_float "," literal_float
  private static boolean float_scope_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LITERAL_FLOAT);
    r = r && consumeToken(b, ",");
    r = r && consumeToken(b, LITERAL_FLOAT);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_float ","
  private static boolean float_scope_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LITERAL_FLOAT);
    r = r && consumeToken(b, ",");
    exit_section_(b, m, null, r);
    return r;
  }

  // "," literal_float
  private static boolean float_scope_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "float_scope_1_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && consumeToken(b, LITERAL_FLOAT);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "." literal_string? (key_type_items | type_reference)?
  static boolean http_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_body")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ".");
    r = r && http_body_1(b, l + 1);
    r = r && http_body_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_string?
  private static boolean http_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_body_1")) return false;
    consumeToken(b, LITERAL_STRING);
    return true;
  }

  // (key_type_items | type_reference)?
  private static boolean http_body_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_body_2")) return false;
    http_body_2_0(b, l + 1);
    return true;
  }

  // key_type_items | type_reference
  private static boolean http_body_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_body_2_0")) return false;
    boolean r;
    r = key_type_items(b, l + 1);
    if (!r) r = type_reference(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "!" key_type_items
  static boolean http_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_header")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "!");
    r = r && key_type_items(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "get" | "post" | "put" | "patch" | "delete" | "options" | "head"
  static boolean http_method(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_method")) return false;
    boolean r;
    r = consumeToken(b, "get");
    if (!r) r = consumeToken(b, "post");
    if (!r) r = consumeToken(b, "put");
    if (!r) r = consumeToken(b, "patch");
    if (!r) r = consumeToken(b, "delete");
    if (!r) r = consumeToken(b, "options");
    if (!r) r = consumeToken(b, "head");
    return r;
  }

  /* ********************************************************** */
  // annotation_item* http_method http_path (http_query | http_header | cookie | http_body)* http_resp http_resp_header? ";"
  static boolean http_operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_operation")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = http_operation_0(b, l + 1);
    r = r && http_method(b, l + 1);
    r = r && http_path(b, l + 1);
    r = r && http_operation_3(b, l + 1);
    r = r && http_resp(b, l + 1);
    r = r && http_operation_5(b, l + 1);
    r = r && consumeToken(b, ";");
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation_item*
  private static boolean http_operation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_operation_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "http_operation_0", c)) break;
    }
    return true;
  }

  // (http_query | http_header | cookie | http_body)*
  private static boolean http_operation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_operation_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!http_operation_3_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "http_operation_3", c)) break;
    }
    return true;
  }

  // http_query | http_header | cookie | http_body
  private static boolean http_operation_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_operation_3_0")) return false;
    boolean r;
    r = http_query(b, l + 1);
    if (!r) r = http_header(b, l + 1);
    if (!r) r = cookie(b, l + 1);
    if (!r) r = http_body(b, l + 1);
    return r;
  }

  // http_resp_header?
  private static boolean http_operation_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_operation_5")) return false;
    http_resp_header(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // ("/" (text | ("{" key_type_item "}")))+ "/"?
  static boolean http_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = http_path_0(b, l + 1);
    r = r && http_path_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("/" (text | ("{" key_type_item "}")))+
  private static boolean http_path_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = http_path_0_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!http_path_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "http_path_0", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // "/" (text | ("{" key_type_item "}"))
  private static boolean http_path_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "/");
    r = r && http_path_0_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // text | ("{" key_type_item "}")
  private static boolean http_path_0_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = text(b, l + 1);
    if (!r) r = http_path_0_0_1_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "{" key_type_item "}"
  private static boolean http_path_0_0_1_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_0_1_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && key_type_item(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // "/"?
  private static boolean http_path_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_1")) return false;
    consumeToken(b, "/");
    return true;
  }

  /* ********************************************************** */
  // "?" key_type_items
  static boolean http_query(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_query")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "?");
    r = r && key_type_items(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // literal_integer literal_string? (key_type_items | type_reference)
  static boolean http_resp(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_resp")) return false;
    if (!nextTokenIs(b, LITERAL_INTEGER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LITERAL_INTEGER);
    r = r && http_resp_1(b, l + 1);
    r = r && http_resp_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_string?
  private static boolean http_resp_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_resp_1")) return false;
    consumeToken(b, LITERAL_STRING);
    return true;
  }

  // key_type_items | type_reference
  private static boolean http_resp_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_resp_2")) return false;
    boolean r;
    r = key_type_items(b, l + 1);
    if (!r) r = type_reference(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "!!" key_type_items
  static boolean http_resp_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_resp_header")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "!!");
    r = r && key_type_items(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "[" ((literal_integer "," literal_integer) | (literal_integer ",") | ("," literal_integer))? "]"
  static boolean integer_scope(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "[");
    r = r && integer_scope_1(b, l + 1);
    r = r && consumeToken(b, "]");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((literal_integer "," literal_integer) | (literal_integer ",") | ("," literal_integer))?
  private static boolean integer_scope_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope_1")) return false;
    integer_scope_1_0(b, l + 1);
    return true;
  }

  // (literal_integer "," literal_integer) | (literal_integer ",") | ("," literal_integer)
  private static boolean integer_scope_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = integer_scope_1_0_0(b, l + 1);
    if (!r) r = integer_scope_1_0_1(b, l + 1);
    if (!r) r = integer_scope_1_0_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_integer "," literal_integer
  private static boolean integer_scope_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LITERAL_INTEGER);
    r = r && consumeToken(b, ",");
    r = r && consumeToken(b, LITERAL_INTEGER);
    exit_section_(b, m, null, r);
    return r;
  }

  // literal_integer ","
  private static boolean integer_scope_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LITERAL_INTEGER);
    r = r && consumeToken(b, ",");
    exit_section_(b, m, null, r);
    return r;
  }

  // "," literal_integer
  private static boolean integer_scope_1_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "integer_scope_1_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    r = r && consumeToken(b, LITERAL_INTEGER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // annotation_item* text "?"? ":" data_type
  static boolean key_type_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_type_item_0(b, l + 1);
    r = r && text(b, l + 1);
    r = r && key_type_item_2(b, l + 1);
    r = r && consumeToken(b, ":");
    r = r && data_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation_item*
  private static boolean key_type_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_item_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_type_item_0", c)) break;
    }
    return true;
  }

  // "?"?
  private static boolean key_type_item_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_item_2")) return false;
    consumeToken(b, "?");
    return true;
  }

  /* ********************************************************** */
  // "{" key_type_item ((",")? key_type_item)* ","? "}"
  static boolean key_type_items(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && key_type_item(b, l + 1);
    r = r && key_type_items_2(b, l + 1);
    r = r && key_type_items_3(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((",")? key_type_item)*
  private static boolean key_type_items_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!key_type_items_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_type_items_2", c)) break;
    }
    return true;
  }

  // (",")? key_type_item
  private static boolean key_type_items_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_type_items_2_0_0(b, l + 1);
    r = r && key_type_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (",")?
  private static boolean key_type_items_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items_2_0_0")) return false;
    key_type_items_2_0_0_0(b, l + 1);
    return true;
  }

  // (",")
  private static boolean key_type_items_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items_2_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean key_type_items_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_items_3")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // annotation_item* text "=" (literal_string | literal_boolean | literal_float | literal_integer)
  static boolean key_value_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_item")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_value_item_0(b, l + 1);
    r = r && text(b, l + 1);
    r = r && consumeToken(b, "=");
    r = r && key_value_item_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation_item*
  private static boolean key_value_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_item_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_value_item_0", c)) break;
    }
    return true;
  }

  // literal_string | literal_boolean | literal_float | literal_integer
  private static boolean key_value_item_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_item_3")) return false;
    boolean r;
    r = consumeToken(b, LITERAL_STRING);
    if (!r) r = consumeToken(b, LITERAL_BOOLEAN);
    if (!r) r = consumeToken(b, LITERAL_FLOAT);
    if (!r) r = consumeToken(b, LITERAL_INTEGER);
    return r;
  }

  /* ********************************************************** */
  // "{" key_value_item ((",")? key_value_item)* ","? "}"
  static boolean key_value_items(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && key_value_item(b, l + 1);
    r = r && key_value_items_2(b, l + 1);
    r = r && key_value_items_3(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((",")? key_value_item)*
  private static boolean key_value_items_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!key_value_items_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_value_items_2", c)) break;
    }
    return true;
  }

  // (",")? key_value_item
  private static boolean key_value_items_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_value_items_2_0_0(b, l + 1);
    r = r && key_value_item(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (",")?
  private static boolean key_value_items_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items_2_0_0")) return false;
    key_value_items_2_0_0_0(b, l + 1);
    return true;
  }

  // (",")
  private static boolean key_value_items_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items_2_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean key_value_items_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_items_3")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // "{" literal_string ((",")? literal_string)* ","? "}"
  static boolean literal_strings(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && consumeToken(b, LITERAL_STRING);
    r = r && literal_strings_2(b, l + 1);
    r = r && literal_strings_3(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // ((",")? literal_string)*
  private static boolean literal_strings_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings_2")) return false;
    while (true) {
      int c = current_position_(b);
      if (!literal_strings_2_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "literal_strings_2", c)) break;
    }
    return true;
  }

  // (",")? literal_string
  private static boolean literal_strings_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = literal_strings_2_0_0(b, l + 1);
    r = r && consumeToken(b, LITERAL_STRING);
    exit_section_(b, m, null, r);
    return r;
  }

  // (",")?
  private static boolean literal_strings_2_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings_2_0_0")) return false;
    literal_strings_2_0_0_0(b, l + 1);
    return true;
  }

  // (",")
  private static boolean literal_strings_2_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings_2_0_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ",");
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean literal_strings_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal_strings_3")) return false;
    consumeToken(b, ",");
    return true;
  }

  /* ********************************************************** */
  // (literal_strings | key_value_items | key_type_items+ | ("{" http_operation* "}"))?
  static boolean object_item(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_item")) return false;
    object_item_0(b, l + 1);
    return true;
  }

  // literal_strings | key_value_items | key_type_items+ | ("{" http_operation* "}")
  private static boolean object_item_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_item_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = literal_strings(b, l + 1);
    if (!r) r = key_value_items(b, l + 1);
    if (!r) r = object_item_0_2(b, l + 1);
    if (!r) r = object_item_0_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // key_type_items+
  private static boolean object_item_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_item_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_type_items(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!key_type_items(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "object_item_0_2", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // "{" http_operation* "}"
  private static boolean object_item_0_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_item_0_3")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "{");
    r = r && object_item_0_3_1(b, l + 1);
    r = r && consumeToken(b, "}");
    exit_section_(b, m, null, r);
    return r;
  }

  // http_operation*
  private static boolean object_item_0_3_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "object_item_0_3_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!http_operation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "object_item_0_3_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // annotation_item* keyword text? object_item?
  public static boolean operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation")) return false;
    if (!nextTokenIs(b, "<operation>", ANNOTATION, KEYWORD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, OPERATION, "<operation>");
    r = operation_0(b, l + 1);
    r = r && consumeToken(b, KEYWORD);
    r = r && operation_2(b, l + 1);
    r = r && operation_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // annotation_item*
  private static boolean operation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation_item(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "operation_0", c)) break;
    }
    return true;
  }

  // text?
  private static boolean operation_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_2")) return false;
    text(b, l + 1);
    return true;
  }

  // object_item?
  private static boolean operation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_3")) return false;
    object_item(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // 'schema' identifier key_type_items
  public static boolean schema_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCHEMA_DECLARATION, "<schema declaration>");
    r = consumeToken(b, "schema");
    r = r && consumeToken(b, IDENTIFIER);
    r = r && key_type_items(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // identifier|literal_string
  static boolean text(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, LITERAL_STRING)) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, LITERAL_STRING);
    return r;
  }

  /* ********************************************************** */
  // identifier {
  // //  methods=[getName setName getNameIdentifier]
  // //  implements=["org.jetbrains.xidl.language.psi.XidlNamedElement"]
  // //  mixin="org.jetbrains.xidl.language.psi.impl.XidlNamedElementImpl"
  // }
  public static boolean type_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && type_reference_1(b, l + 1);
    exit_section_(b, m, TYPE_REFERENCE, r);
    return r;
  }

  // {
  // //  methods=[getName setName getNameIdentifier]
  // //  implements=["org.jetbrains.xidl.language.psi.XidlNamedElement"]
  // //  mixin="org.jetbrains.xidl.language.psi.impl.XidlNamedElementImpl"
  // }
  private static boolean type_reference_1(PsiBuilder b, int l) {
    return true;
  }

  /* ********************************************************** */
  // (comment|operation)*
  static boolean xidlFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xidlFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!xidlFile_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "xidlFile", c)) break;
    }
    return true;
  }

  // comment|operation
  private static boolean xidlFile_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xidlFile_0")) return false;
    boolean r;
    r = comment(b, l + 1);
    if (!r) r = operation(b, l + 1);
    return r;
  }

}
