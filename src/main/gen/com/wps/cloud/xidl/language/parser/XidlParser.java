// This is a generated file. Not intended for manual editing.
package com.wps.cloud.xidl.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.wps.cloud.xidl.language.psi.XidlTypes.*;
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
  // cmd_object | def_object | tag_object | (<<text_empty_block_object text_empty_object_key>>)
  //                       | include_object | schema_object | endpoints_object | mdepends_object | common_object
  static boolean all_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "all_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = cmd_object(b, l + 1);
    if (!r) r = def_object(b, l + 1);
    if (!r) r = tag_object(b, l + 1);
    if (!r) r = all_object_3(b, l + 1);
    if (!r) r = include_object(b, l + 1);
    if (!r) r = schema_object(b, l + 1);
    if (!r) r = endpoints_object(b, l + 1);
    if (!r) r = mdepends_object(b, l + 1);
    if (!r) r = common_object(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<text_empty_block_object text_empty_object_key>>
  private static boolean all_object_3(PsiBuilder b, int l) {
    return text_empty_block_object(b, l + 1, XidlParser::text_empty_object_key);
  }

  /* ********************************************************** */
  // zero_param_anno | single_param_anno | multi_param_anno | custom_anno
  static boolean annotation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "annotation")) return false;
    boolean r;
    r = zero_param_anno(b, l + 1);
    if (!r) r = single_param_anno(b, l + 1);
    if (!r) r = multi_param_anno(b, l + 1);
    if (!r) r = custom_anno(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // "{" <<param>> "}"
  public static boolean block_meta(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "block_meta")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACE);
    r = r && _param.parse(b, l);
    r = r && consumeToken(b, RBRACE);
    exit_section_(b, m, BLOCK_META, r);
    return r;
  }

  /* ********************************************************** */
  // annotation* "cmd" text <<block_meta (key_type_list | cmd_object)* >>?
  static boolean cmd_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmd_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = cmd_object_0(b, l + 1);
    r = r && consumeToken(b, "cmd");
    r = r && text(b, l + 1);
    r = r && cmd_object_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean cmd_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmd_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cmd_object_0", c)) break;
    }
    return true;
  }

  // <<block_meta (key_type_list | cmd_object)* >>?
  private static boolean cmd_object_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmd_object_3")) return false;
    block_meta(b, l + 1, XidlParser::cmd_object_3_0_0);
    return true;
  }

  // (key_type_list | cmd_object)*
  private static boolean cmd_object_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmd_object_3_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!cmd_object_3_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "cmd_object_3_0_0", c)) break;
    }
    return true;
  }

  // key_type_list | cmd_object
  private static boolean cmd_object_3_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "cmd_object_3_0_0_0")) return false;
    boolean r;
    r = key_type_list(b, l + 1);
    if (!r) r = cmd_object(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // <<param>> (","? <<param>>)* ","?
  static boolean comma_separated_list(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "comma_separated_list")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _param.parse(b, l);
    r = r && comma_separated_list_1(b, l + 1, _param);
    r = r && comma_separated_list_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // (","? <<param>>)*
  private static boolean comma_separated_list_1(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "comma_separated_list_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!comma_separated_list_1_0(b, l + 1, _param)) break;
      if (!empty_element_parsed_guard_(b, "comma_separated_list_1", c)) break;
    }
    return true;
  }

  // ","? <<param>>
  private static boolean comma_separated_list_1_0(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "comma_separated_list_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = comma_separated_list_1_0_0(b, l + 1);
    r = r && _param.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean comma_separated_list_1_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comma_separated_list_1_0_0")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  // ","?
  private static boolean comma_separated_list_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comma_separated_list_2")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // LINE_COMMENT | BLOCK_COMMENT
  static boolean comment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "comment")) return false;
    if (!nextTokenIs(b, "", BLOCK_COMMENT, LINE_COMMENT)) return false;
    boolean r;
    r = consumeToken(b, LINE_COMMENT);
    if (!r) r = consumeToken(b, BLOCK_COMMENT);
    return r;
  }

  /* ********************************************************** */
  // annotation* (!known_object_key KEYWORD) text? (key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block)?
  static boolean common_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = common_object_0(b, l + 1);
    r = r && common_object_1(b, l + 1);
    r = r && common_object_2(b, l + 1);
    r = r && common_object_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean common_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "common_object_0", c)) break;
    }
    return true;
  }

  // !known_object_key KEYWORD
  private static boolean common_object_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = common_object_1_0(b, l + 1);
    r = r && consumeToken(b, KEYWORD);
    exit_section_(b, m, null, r);
    return r;
  }

  // !known_object_key
  private static boolean common_object_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !known_object_key(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // text?
  private static boolean common_object_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_2")) return false;
    text(b, l + 1);
    return true;
  }

  // (key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block)?
  private static boolean common_object_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_3")) return false;
    common_object_3_0(b, l + 1);
    return true;
  }

  // key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block
  private static boolean common_object_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "common_object_3_0")) return false;
    boolean r;
    r = key_value_list_block(b, l + 1);
    if (!r) r = key_type_list_block(b, l + 1);
    if (!r) r = string_List_block(b, l + 1);
    if (!r) r = operation_list_block(b, l + 1);
    if (!r) r = mixed_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // !ZERO_PARAM_ANNOTATION !SINGLE_PARAM_ANNOTATION !MULTI_PARAM_ANNOTATION CUSTOM_ANNOTATION (STRING_LITERAL | <<block_meta string_list>>)?
  static boolean custom_anno(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno")) return false;
    if (!nextTokenIs(b, CUSTOM_ANNOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = custom_anno_0(b, l + 1);
    r = r && custom_anno_1(b, l + 1);
    r = r && custom_anno_2(b, l + 1);
    r = r && consumeToken(b, CUSTOM_ANNOTATION);
    r = r && custom_anno_4(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // !ZERO_PARAM_ANNOTATION
  private static boolean custom_anno_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno_0")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, ZERO_PARAM_ANNOTATION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !SINGLE_PARAM_ANNOTATION
  private static boolean custom_anno_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno_1")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, SINGLE_PARAM_ANNOTATION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // !MULTI_PARAM_ANNOTATION
  private static boolean custom_anno_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno_2")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NOT_);
    r = !consumeToken(b, MULTI_PARAM_ANNOTATION);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // (STRING_LITERAL | <<block_meta string_list>>)?
  private static boolean custom_anno_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno_4")) return false;
    custom_anno_4_0(b, l + 1);
    return true;
  }

  // STRING_LITERAL | <<block_meta string_list>>
  private static boolean custom_anno_4_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "custom_anno_4_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, STRING_LITERAL);
    if (!r) r = block_meta(b, l + 1, XidlParser::string_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // (<<range_scope INTEGER_LITERAL>>? ("*"? data_type0) <<range_scope INTEGER_LITERAL>>?)
  //   | ("map" "<" data_type "," data_type ">")
  //   | ("chan" ("<-" | "->") data_type)
  static boolean data_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type_0(b, l + 1);
    if (!r) r = data_type_1(b, l + 1);
    if (!r) r = data_type_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<range_scope INTEGER_LITERAL>>? ("*"? data_type0) <<range_scope INTEGER_LITERAL>>?
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

  // <<range_scope INTEGER_LITERAL>>?
  private static boolean data_type_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_0")) return false;
    range_scope(b, l + 1, INTEGER_LITERAL_parser_);
    return true;
  }

  // "*"? data_type0
  private static boolean data_type_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type_0_1_0(b, l + 1);
    r = r && data_type0(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "*"?
  private static boolean data_type_0_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_1_0")) return false;
    consumeToken(b, MULTIPLY);
    return true;
  }

  // <<range_scope INTEGER_LITERAL>>?
  private static boolean data_type_0_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_0_2")) return false;
    range_scope(b, l + 1, INTEGER_LITERAL_parser_);
    return true;
  }

  // "map" "<" data_type "," data_type ">"
  private static boolean data_type_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "map");
    r = r && consumeToken(b, LANGLE);
    r = r && data_type(b, l + 1);
    r = r && consumeToken(b, COMMA);
    r = r && data_type(b, l + 1);
    r = r && consumeToken(b, RANGLE);
    exit_section_(b, m, null, r);
    return r;
  }

  // "chan" ("<-" | "->") data_type
  private static boolean data_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "chan");
    r = r && data_type_2_1(b, l + 1);
    r = r && data_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // "<-" | "->"
  private static boolean data_type_2_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type_2_1")) return false;
    boolean r;
    r = consumeToken(b, CHAN_SEND);
    if (!r) r = consumeToken(b, CHAN_RECEIVE);
    return r;
  }

  /* ********************************************************** */
  // SIMPLE_TYPE | type_reference
  static boolean data_type0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "data_type0")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, SIMPLE_TYPE)) return false;
    boolean r;
    r = consumeToken(b, SIMPLE_TYPE);
    if (!r) r = type_reference(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // annotation* "def" key_value_list_block
  static boolean def_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "def_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = def_object_0(b, l + 1);
    r = r && consumeToken(b, "def");
    r = r && key_value_list_block(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean def_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "def_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "def_object_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "{" "}"
  static boolean empty_block(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "empty_block")) return false;
    if (!nextTokenIs(b, LBRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, LBRACE, RBRACE);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // annotation* "endpoints" (operation_list_block | empty_block)
  static boolean endpoints_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endpoints_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = endpoints_object_0(b, l + 1);
    r = r && consumeToken(b, "endpoints");
    r = r && endpoints_object_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean endpoints_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endpoints_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "endpoints_object_0", c)) break;
    }
    return true;
  }

  // operation_list_block | empty_block
  private static boolean endpoints_object_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "endpoints_object_2")) return false;
    boolean r;
    r = operation_list_block(b, l + 1);
    if (!r) r = empty_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // annotation* ("func" | "xapi") func_name "(" func_params? ")" func_return?
  static boolean func_declaration(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_declaration")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = func_declaration_0(b, l + 1);
    r = r && func_declaration_1(b, l + 1);
    r = r && func_name(b, l + 1);
    r = r && consumeToken(b, LPAREN);
    r = r && func_declaration_4(b, l + 1);
    r = r && consumeToken(b, RPAREN);
    r = r && func_declaration_6(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean func_declaration_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_declaration_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "func_declaration_0", c)) break;
    }
    return true;
  }

  // "func" | "xapi"
  private static boolean func_declaration_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_declaration_1")) return false;
    boolean r;
    r = consumeToken(b, "func");
    if (!r) r = consumeToken(b, "xapi");
    return r;
  }

  // func_params?
  private static boolean func_declaration_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_declaration_4")) return false;
    func_params(b, l + 1);
    return true;
  }

  // func_return?
  private static boolean func_declaration_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_declaration_6")) return false;
    func_return(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean func_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_name")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, FUNC_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER ("," IDENTIFIER)* data_type
  static boolean func_param_group(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_param_group")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    r = r && func_param_group_1(b, l + 1);
    r = r && data_type(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ("," IDENTIFIER)*
  private static boolean func_param_group_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_param_group_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!func_param_group_1_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "func_param_group_1", c)) break;
    }
    return true;
  }

  // "," IDENTIFIER
  private static boolean func_param_group_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_param_group_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, COMMA, IDENTIFIER);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<comma_separated_list func_param_group>>
  static boolean func_params(PsiBuilder b, int l) {
    return comma_separated_list(b, l + 1, XidlParser::func_param_group);
  }

  /* ********************************************************** */
  // "(" <<comma_separated_list data_type>> ")"
  static boolean func_return(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "func_return")) return false;
    if (!nextTokenIs(b, LPAREN)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LPAREN);
    r = r && comma_separated_list(b, l + 1, XidlParser::data_type);
    r = r && consumeToken(b, RPAREN);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "~" <<block_meta key_type_list>>
  static boolean http_cookie(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_cookie")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "~");
    r = r && block_meta(b, l + 1, XidlParser::key_type_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "!" <<block_meta key_type_list>>
  static boolean http_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_header")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "!");
    r = r && block_meta(b, l + 1, XidlParser::key_type_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // ("/" (text | <<block_meta (key_type ","?)>>))+
  static boolean http_path(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path")) return false;
    if (!nextTokenIs(b, DIVIDE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = http_path_0(b, l + 1);
    while (r) {
      int c = current_position_(b);
      if (!http_path_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "http_path", c)) break;
    }
    exit_section_(b, m, null, r);
    return r;
  }

  // "/" (text | <<block_meta (key_type ","?)>>)
  private static boolean http_path_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DIVIDE);
    r = r && http_path_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // text | <<block_meta (key_type ","?)>>
  private static boolean http_path_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = text(b, l + 1);
    if (!r) r = block_meta(b, l + 1, XidlParser::http_path_0_1_1_0);
    exit_section_(b, m, null, r);
    return r;
  }

  // key_type ","?
  private static boolean http_path_0_1_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_1_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_type(b, l + 1);
    r = r && http_path_0_1_1_0_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // ","?
  private static boolean http_path_0_1_1_0_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_path_0_1_1_0_1")) return false;
    consumeToken(b, COMMA);
    return true;
  }

  /* ********************************************************** */
  // "?" <<block_meta key_type_list>>
  static boolean http_query(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_query")) return false;
    if (!nextTokenIs(b, QUESTION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, QUESTION);
    r = r && block_meta(b, l + 1, XidlParser::key_type_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // "." STRING_LITERAL? (data_type | <<block_meta key_type_list>>)
  static boolean http_request_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_request_body")) return false;
    if (!nextTokenIs(b, DOT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, DOT);
    r = r && http_request_body_1(b, l + 1);
    r = r && http_request_body_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // STRING_LITERAL?
  private static boolean http_request_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_request_body_1")) return false;
    consumeToken(b, STRING_LITERAL);
    return true;
  }

  // data_type | <<block_meta key_type_list>>
  private static boolean http_request_body_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_request_body_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type(b, l + 1);
    if (!r) r = block_meta(b, l + 1, XidlParser::key_type_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // http_query | http_header | http_cookie | http_request_body
  static boolean http_request_info(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_request_info")) return false;
    boolean r;
    r = http_query(b, l + 1);
    if (!r) r = http_header(b, l + 1);
    if (!r) r = http_cookie(b, l + 1);
    if (!r) r = http_request_body(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // http_response_header? http_status_and_response_body http_response_header?
  static boolean http_response(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_response")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = http_response_0(b, l + 1);
    r = r && http_status_and_response_body(b, l + 1);
    r = r && http_response_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // http_response_header?
  private static boolean http_response_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_response_0")) return false;
    http_response_header(b, l + 1);
    return true;
  }

  // http_response_header?
  private static boolean http_response_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_response_2")) return false;
    http_response_header(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "!" http_header
  static boolean http_response_header(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_response_header")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, "!");
    r = r && http_header(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL STRING_LITERAL? (data_type | <<block_meta key_type_list>>)?
  static boolean http_status_and_response_body(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_status_and_response_body")) return false;
    if (!nextTokenIs(b, INTEGER_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, INTEGER_LITERAL);
    r = r && http_status_and_response_body_1(b, l + 1);
    r = r && http_status_and_response_body_2(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // STRING_LITERAL?
  private static boolean http_status_and_response_body_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_status_and_response_body_1")) return false;
    consumeToken(b, STRING_LITERAL);
    return true;
  }

  // (data_type | <<block_meta key_type_list>>)?
  private static boolean http_status_and_response_body_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_status_and_response_body_2")) return false;
    http_status_and_response_body_2_0(b, l + 1);
    return true;
  }

  // data_type | <<block_meta key_type_list>>
  private static boolean http_status_and_response_body_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "http_status_and_response_body_2_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = data_type(b, l + 1);
    if (!r) r = block_meta(b, l + 1, XidlParser::key_type_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // annotation* "include" <<block_meta string_list?>>
  static boolean include_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = include_object_0(b, l + 1);
    r = r && consumeToken(b, "include");
    r = r && block_meta(b, l + 1, XidlParser::include_object_2_0);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean include_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "include_object_0", c)) break;
    }
    return true;
  }

  // string_list?
  private static boolean include_object_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "include_object_2_0")) return false;
    string_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // annotation* text "?"? ":" data_type ("=" literal)?
  static boolean key_type(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_type_0(b, l + 1);
    r = r && text(b, l + 1);
    r = r && key_type_2(b, l + 1);
    r = r && consumeToken(b, COLON);
    r = r && data_type(b, l + 1);
    r = r && key_type_5(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean key_type_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_type_0", c)) break;
    }
    return true;
  }

  // "?"?
  private static boolean key_type_2(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_2")) return false;
    consumeToken(b, QUESTION);
    return true;
  }

  // ("=" literal)?
  private static boolean key_type_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_5")) return false;
    key_type_5_0(b, l + 1);
    return true;
  }

  // "=" literal
  private static boolean key_type_5_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_type_5_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, ASSIGN);
    r = r && literal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<comma_separated_list key_type>>
  static boolean key_type_list(PsiBuilder b, int l) {
    return comma_separated_list(b, l + 1, XidlParser::key_type);
  }

  /* ********************************************************** */
  // <<block_meta key_type_list>>
  static boolean key_type_list_block(PsiBuilder b, int l) {
    return block_meta(b, l + 1, XidlParser::key_type_list);
  }

  /* ********************************************************** */
  // annotation* text "=" literal
  static boolean key_value(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = key_value_0(b, l + 1);
    r = r && text(b, l + 1);
    r = r && consumeToken(b, ASSIGN);
    r = r && literal(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean key_value_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "key_value_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "key_value_0", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<comma_separated_list key_value>>
  static boolean key_value_list(PsiBuilder b, int l) {
    return comma_separated_list(b, l + 1, XidlParser::key_value);
  }

  /* ********************************************************** */
  // <<block_meta key_value_list>>
  static boolean key_value_list_block(PsiBuilder b, int l) {
    return block_meta(b, l + 1, XidlParser::key_value_list);
  }

  /* ********************************************************** */
  // "cmd" | "def" | "tag" | text_empty_object_key | "func" | "xapi" | "schema" | "endpoints" | "mdepends" | "include"
  static boolean known_object_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "known_object_key")) return false;
    boolean r;
    r = consumeToken(b, "cmd");
    if (!r) r = consumeToken(b, "def");
    if (!r) r = consumeToken(b, "tag");
    if (!r) r = text_empty_object_key(b, l + 1);
    if (!r) r = consumeToken(b, "func");
    if (!r) r = consumeToken(b, "xapi");
    if (!r) r = consumeToken(b, "schema");
    if (!r) r = consumeToken(b, "endpoints");
    if (!r) r = consumeToken(b, "mdepends");
    if (!r) r = consumeToken(b, "include");
    return r;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL | FLOAT_LITERAL | BOOLEAN_LITERAL | STRING_LITERAL
  static boolean literal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "literal")) return false;
    boolean r;
    r = consumeToken(b, INTEGER_LITERAL);
    if (!r) r = consumeToken(b, FLOAT_LITERAL);
    if (!r) r = consumeToken(b, BOOLEAN_LITERAL);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    return r;
  }

  /* ********************************************************** */
  // annotation* "mdepends" <<block_meta string_list?>>
  static boolean mdepends_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mdepends_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = mdepends_object_0(b, l + 1);
    r = r && consumeToken(b, "mdepends");
    r = r && block_meta(b, l + 1, XidlParser::mdepends_object_2_0);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean mdepends_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mdepends_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "mdepends_object_0", c)) break;
    }
    return true;
  }

  // string_list?
  private static boolean mdepends_object_2_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mdepends_object_2_0")) return false;
    string_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // <<block_meta (key_value_list | key_type_list | string_list | operation_list | common_object)*>>
  static boolean mixed_block(PsiBuilder b, int l) {
    return block_meta(b, l + 1, XidlParser::mixed_block_0_0);
  }

  // (key_value_list | key_type_list | string_list | operation_list | common_object)*
  private static boolean mixed_block_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixed_block_0_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!mixed_block_0_0_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "mixed_block_0_0", c)) break;
    }
    return true;
  }

  // key_value_list | key_type_list | string_list | operation_list | common_object
  private static boolean mixed_block_0_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "mixed_block_0_0_0")) return false;
    boolean r;
    r = key_value_list(b, l + 1);
    if (!r) r = key_type_list(b, l + 1);
    if (!r) r = string_list(b, l + 1);
    if (!r) r = operation_list(b, l + 1);
    if (!r) r = common_object(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // MULTI_PARAM_ANNOTATION <<block_meta string_list>>
  static boolean multi_param_anno(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "multi_param_anno")) return false;
    if (!nextTokenIs(b, MULTI_PARAM_ANNOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, MULTI_PARAM_ANNOTATION);
    r = r && block_meta(b, l + 1, XidlParser::string_list);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // annotation* HTTP_METHOD http_path http_request_info* http_response ";"
  static boolean operation(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = operation_0(b, l + 1);
    r = r && consumeToken(b, HTTP_METHOD);
    r = r && http_path(b, l + 1);
    r = r && operation_3(b, l + 1);
    r = r && http_response(b, l + 1);
    r = r && consumeToken(b, SEMICOLON);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean operation_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "operation_0", c)) break;
    }
    return true;
  }

  // http_request_info*
  private static boolean operation_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "operation_3")) return false;
    while (true) {
      int c = current_position_(b);
      if (!http_request_info(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "operation_3", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<comma_separated_list operation>>
  static boolean operation_list(PsiBuilder b, int l) {
    return comma_separated_list(b, l + 1, XidlParser::operation);
  }

  /* ********************************************************** */
  // <<block_meta operation_list>>
  static boolean operation_list_block(PsiBuilder b, int l) {
    return block_meta(b, l + 1, XidlParser::operation_list);
  }

  /* ********************************************************** */
  // "[" ((<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>))? "]"
  static boolean range_scope(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope")) return false;
    if (!nextTokenIs(b, LBRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, LBRACKET);
    r = r && range_scope_1(b, l + 1, _param);
    r = r && consumeToken(b, RBRACKET);
    exit_section_(b, m, null, r);
    return r;
  }

  // ((<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>))?
  private static boolean range_scope_1(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope_1")) return false;
    range_scope_1_0(b, l + 1, _param);
    return true;
  }

  // (<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>)
  private static boolean range_scope_1_0(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = range_scope_1_0_0(b, l + 1, _param);
    if (!r) r = range_scope_1_0_1(b, l + 1, _param);
    if (!r) r = range_scope_1_0_2(b, l + 1, _param);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<param>> "," <<param>>
  private static boolean range_scope_1_0_0(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope_1_0_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _param.parse(b, l);
    r = r && consumeToken(b, COMMA);
    r = r && _param.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  // <<param>> ","
  private static boolean range_scope_1_0_1(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope_1_0_1")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = _param.parse(b, l);
    r = r && consumeToken(b, COMMA);
    exit_section_(b, m, null, r);
    return r;
  }

  // "," <<param>>
  private static boolean range_scope_1_0_2(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "range_scope_1_0_2")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, COMMA);
    r = r && _param.parse(b, l);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // text
  public static boolean schema_name(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_name")) return false;
    if (!nextTokenIs(b, "<schema name>", IDENTIFIER, STRING_LITERAL)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCHEMA_NAME, "<schema name>");
    r = text(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // annotation* "schema" schema_name (key_value_list_block | key_type_list_block | empty_block)?
  public static boolean schema_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_object")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, SCHEMA_OBJECT, "<schema object>");
    r = schema_object_0(b, l + 1);
    r = r && consumeToken(b, "schema");
    r = r && schema_name(b, l + 1);
    r = r && schema_object_3(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // annotation*
  private static boolean schema_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "schema_object_0", c)) break;
    }
    return true;
  }

  // (key_value_list_block | key_type_list_block | empty_block)?
  private static boolean schema_object_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_object_3")) return false;
    schema_object_3_0(b, l + 1);
    return true;
  }

  // key_value_list_block | key_type_list_block | empty_block
  private static boolean schema_object_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "schema_object_3_0")) return false;
    boolean r;
    r = key_value_list_block(b, l + 1);
    if (!r) r = key_type_list_block(b, l + 1);
    if (!r) r = empty_block(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // SINGLE_PARAM_ANNOTATION STRING_LITERAL
  static boolean single_param_anno(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "single_param_anno")) return false;
    if (!nextTokenIs(b, SINGLE_PARAM_ANNOTATION)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, SINGLE_PARAM_ANNOTATION, STRING_LITERAL);
    exit_section_(b, m, null, r);
    return r;
  }

  /* ********************************************************** */
  // <<block_meta string_list>>
  static boolean string_List_block(PsiBuilder b, int l) {
    return block_meta(b, l + 1, XidlParser::string_list);
  }

  /* ********************************************************** */
  // <<comma_separated_list STRING_LITERAL>>
  static boolean string_list(PsiBuilder b, int l) {
    return comma_separated_list(b, l + 1, STRING_LITERAL_parser_);
  }

  /* ********************************************************** */
  // annotation* "tag" text <<block_meta string_list?>>?
  static boolean tag_object(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = tag_object_0(b, l + 1);
    r = r && consumeToken(b, "tag");
    r = r && text(b, l + 1);
    r = r && tag_object_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean tag_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "tag_object_0", c)) break;
    }
    return true;
  }

  // <<block_meta string_list?>>?
  private static boolean tag_object_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_object_3")) return false;
    block_meta(b, l + 1, XidlParser::tag_object_3_0_0);
    return true;
  }

  // string_list?
  private static boolean tag_object_3_0_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "tag_object_3_0_0")) return false;
    string_list(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER | STRING_LITERAL
  static boolean text(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text")) return false;
    if (!nextTokenIs(b, "", IDENTIFIER, STRING_LITERAL)) return false;
    boolean r;
    r = consumeToken(b, IDENTIFIER);
    if (!r) r = consumeToken(b, STRING_LITERAL);
    return r;
  }

  /* ********************************************************** */
  // annotation* <<param>> text empty_block?
  static boolean text_empty_block_object(PsiBuilder b, int l, Parser _param) {
    if (!recursion_guard_(b, l, "text_empty_block_object")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = text_empty_block_object_0(b, l + 1);
    r = r && _param.parse(b, l);
    r = r && text(b, l + 1);
    r = r && text_empty_block_object_3(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // annotation*
  private static boolean text_empty_block_object_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text_empty_block_object_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!annotation(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "text_empty_block_object_0", c)) break;
    }
    return true;
  }

  // empty_block?
  private static boolean text_empty_block_object_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text_empty_block_object_3")) return false;
    empty_block(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // "app" | "url" | "version" | "module" | "middleware" | "mcode" | "mname"
  static boolean text_empty_object_key(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "text_empty_object_key")) return false;
    boolean r;
    r = consumeToken(b, "app");
    if (!r) r = consumeToken(b, "url");
    if (!r) r = consumeToken(b, "version");
    if (!r) r = consumeToken(b, "module");
    if (!r) r = consumeToken(b, "middleware");
    if (!r) r = consumeToken(b, "mcode");
    if (!r) r = consumeToken(b, "mname");
    return r;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean type_reference(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "type_reference")) return false;
    if (!nextTokenIs(b, IDENTIFIER)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, IDENTIFIER);
    exit_section_(b, m, TYPE_REFERENCE, r);
    return r;
  }

  /* ********************************************************** */
  // (comment | func_declaration | all_object  | operation)*
  static boolean xidlFile(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xidlFile")) return false;
    while (true) {
      int c = current_position_(b);
      if (!xidlFile_0(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "xidlFile", c)) break;
    }
    return true;
  }

  // comment | func_declaration | all_object  | operation
  private static boolean xidlFile_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "xidlFile_0")) return false;
    boolean r;
    r = comment(b, l + 1);
    if (!r) r = func_declaration(b, l + 1);
    if (!r) r = all_object(b, l + 1);
    if (!r) r = operation(b, l + 1);
    return r;
  }

  /* ********************************************************** */
  // ZERO_PARAM_ANNOTATION
  static boolean zero_param_anno(PsiBuilder b, int l) {
    return consumeToken(b, ZERO_PARAM_ANNOTATION);
  }

  static final Parser INTEGER_LITERAL_parser_ = (b, l) -> consumeToken(b, INTEGER_LITERAL);
  static final Parser STRING_LITERAL_parser_ = (b, l) -> consumeToken(b, STRING_LITERAL);
}
