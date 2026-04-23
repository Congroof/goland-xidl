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

  public ASTNode parse(IElementType root_, PsiBuilder builder_) {
    parseLight(root_, builder_);
    return builder_.getTreeBuilt();
  }

  public void parseLight(IElementType root_, PsiBuilder builder_) {
    boolean result_;
    builder_ = adapt_builder_(root_, builder_, this, null);
    Marker marker_ = enter_section_(builder_, 0, _COLLAPSE_, null);
    result_ = parse_root_(root_, builder_);
    exit_section_(builder_, 0, marker_, root_, result_, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType root_, PsiBuilder builder_) {
    return parse_root_(root_, builder_, 0);
  }

  static boolean parse_root_(IElementType root_, PsiBuilder builder_, int level_) {
    return xidlFile(builder_, level_ + 1);
  }

  /* ********************************************************** */
  // cmd_object | def_object | tag_object | (<<text_empty_block_object text_empty_object_key>>)
  //                       | include_object | schema_object | endpoints_object | mdepends_object | common_object
  static boolean all_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "all_object")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = cmd_object(builder_, level_ + 1);
    if (!result_) result_ = def_object(builder_, level_ + 1);
    if (!result_) result_ = tag_object(builder_, level_ + 1);
    if (!result_) result_ = all_object_3(builder_, level_ + 1);
    if (!result_) result_ = include_object(builder_, level_ + 1);
    if (!result_) result_ = schema_object(builder_, level_ + 1);
    if (!result_) result_ = endpoints_object(builder_, level_ + 1);
    if (!result_) result_ = mdepends_object(builder_, level_ + 1);
    if (!result_) result_ = common_object(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // <<text_empty_block_object text_empty_object_key>>
  private static boolean all_object_3(PsiBuilder builder_, int level_) {
    return text_empty_block_object(builder_, level_ + 1, XidlParser::text_empty_object_key);
  }

  /* ********************************************************** */
  // STRING_LITERAL
  public static boolean anno_string(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "anno_string")) return false;
    if (!nextTokenIs(builder_, STRING_LITERAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_LITERAL);
    exit_section_(builder_, marker_, ANNO_STRING, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<comma_separated_list anno_string>>
  static boolean anno_string_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::anno_string);
  }

  /* ********************************************************** */
  // zero_param_anno | single_param_anno | multi_param_anno | custom_anno
  static boolean annotation(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "annotation")) return false;
    boolean result_;
    result_ = zero_param_anno(builder_, level_ + 1);
    if (!result_) result_ = single_param_anno(builder_, level_ + 1);
    if (!result_) result_ = multi_param_anno(builder_, level_ + 1);
    if (!result_) result_ = custom_anno(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // "{" <<param>> "}"
  public static boolean block_meta(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "block_meta")) return false;
    if (!nextTokenIs(builder_, LBRACE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACE);
    result_ = result_ && param.parse(builder_, level_);
    result_ = result_ && consumeToken(builder_, RBRACE);
    exit_section_(builder_, marker_, BLOCK_META, result_);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "cmd" text <<block_meta (key_type_list | cmd_object)* >>?
  static boolean cmd_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cmd_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = cmd_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "cmd");
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, text(builder_, level_ + 1));
    result_ = pinned_ && cmd_object_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean cmd_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cmd_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "cmd_object_0", pos_)) break;
    }
    return true;
  }

  // <<block_meta (key_type_list | cmd_object)* >>?
  private static boolean cmd_object_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cmd_object_3")) return false;
    block_meta(builder_, level_ + 1, XidlParser::cmd_object_3_0_0);
    return true;
  }

  // (key_type_list | cmd_object)*
  private static boolean cmd_object_3_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cmd_object_3_0_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!cmd_object_3_0_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "cmd_object_3_0_0", pos_)) break;
    }
    return true;
  }

  // key_type_list | cmd_object
  private static boolean cmd_object_3_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "cmd_object_3_0_0_0")) return false;
    boolean result_;
    result_ = key_type_list(builder_, level_ + 1);
    if (!result_) result_ = cmd_object(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // <<param>> (","? <<param>>)* ","?
  static boolean comma_separated_list(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "comma_separated_list")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = param.parse(builder_, level_);
    result_ = result_ && comma_separated_list_1(builder_, level_ + 1, param);
    result_ = result_ && comma_separated_list_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // (","? <<param>>)*
  private static boolean comma_separated_list_1(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "comma_separated_list_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!comma_separated_list_1_0(builder_, level_ + 1, param)) break;
      if (!empty_element_parsed_guard_(builder_, "comma_separated_list_1", pos_)) break;
    }
    return true;
  }

  // ","? <<param>>
  private static boolean comma_separated_list_1_0(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "comma_separated_list_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = comma_separated_list_1_0_0(builder_, level_ + 1);
    result_ = result_ && param.parse(builder_, level_);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ","?
  private static boolean comma_separated_list_1_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comma_separated_list_1_0_0")) return false;
    consumeToken(builder_, COMMA);
    return true;
  }

  // ","?
  private static boolean comma_separated_list_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comma_separated_list_2")) return false;
    consumeToken(builder_, COMMA);
    return true;
  }

  /* ********************************************************** */
  // LINE_COMMENT | BLOCK_COMMENT
  static boolean comment(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "comment")) return false;
    if (!nextTokenIs(builder_, "", BLOCK_COMMENT, LINE_COMMENT)) return false;
    boolean result_;
    result_ = consumeToken(builder_, LINE_COMMENT);
    if (!result_) result_ = consumeToken(builder_, BLOCK_COMMENT);
    return result_;
  }

  /* ********************************************************** */
  // annotation* (!known_object_key KEYWORD) text? (key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block)?
  static boolean common_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = common_object_0(builder_, level_ + 1);
    result_ = result_ && common_object_1(builder_, level_ + 1);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, common_object_2(builder_, level_ + 1));
    result_ = pinned_ && common_object_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean common_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "common_object_0", pos_)) break;
    }
    return true;
  }

  // !known_object_key KEYWORD
  private static boolean common_object_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = common_object_1_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, KEYWORD);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !known_object_key
  private static boolean common_object_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !known_object_key(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // text?
  private static boolean common_object_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_2")) return false;
    text(builder_, level_ + 1);
    return true;
  }

  // (key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block)?
  private static boolean common_object_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_3")) return false;
    common_object_3_0(builder_, level_ + 1);
    return true;
  }

  // key_value_list_block | key_type_list_block | string_List_block | operation_list_block  | mixed_block
  private static boolean common_object_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "common_object_3_0")) return false;
    boolean result_;
    result_ = key_value_list_block(builder_, level_ + 1);
    if (!result_) result_ = key_type_list_block(builder_, level_ + 1);
    if (!result_) result_ = string_List_block(builder_, level_ + 1);
    if (!result_) result_ = operation_list_block(builder_, level_ + 1);
    if (!result_) result_ = mixed_block(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // !ZERO_PARAM_ANNOTATION !SINGLE_PARAM_ANNOTATION !MULTI_PARAM_ANNOTATION CUSTOM_ANNOTATION (STRING_LITERAL | <<block_meta anno_string_list>>)?
  static boolean custom_anno(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno")) return false;
    if (!nextTokenIs(builder_, CUSTOM_ANNOTATION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = custom_anno_0(builder_, level_ + 1);
    result_ = result_ && custom_anno_1(builder_, level_ + 1);
    result_ = result_ && custom_anno_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, CUSTOM_ANNOTATION);
    result_ = result_ && custom_anno_4(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // !ZERO_PARAM_ANNOTATION
  private static boolean custom_anno_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, ZERO_PARAM_ANNOTATION);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !SINGLE_PARAM_ANNOTATION
  private static boolean custom_anno_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, SINGLE_PARAM_ANNOTATION);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // !MULTI_PARAM_ANNOTATION
  private static boolean custom_anno_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !consumeToken(builder_, MULTI_PARAM_ANNOTATION);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // (STRING_LITERAL | <<block_meta anno_string_list>>)?
  private static boolean custom_anno_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno_4")) return false;
    custom_anno_4_0(builder_, level_ + 1);
    return true;
  }

  // STRING_LITERAL | <<block_meta anno_string_list>>
  private static boolean custom_anno_4_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "custom_anno_4_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = block_meta(builder_, level_ + 1, XidlParser::anno_string_list);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // ("<-" | "->")? data_type_body
  static boolean data_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type_0(builder_, level_ + 1);
    result_ = result_ && data_type_body(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ("<-" | "->")?
  private static boolean data_type_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_0")) return false;
    data_type_0_0(builder_, level_ + 1);
    return true;
  }

  // "<-" | "->"
  private static boolean data_type_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_0_0")) return false;
    boolean result_;
    result_ = consumeToken(builder_, CHAN_SEND);
    if (!result_) result_ = consumeToken(builder_, CHAN_RECEIVE);
    return result_;
  }

  /* ********************************************************** */
  // SIMPLE_TYPE | type_reference
  static boolean data_type0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type0")) return false;
    if (!nextTokenIs(builder_, "", IDENTIFIER, SIMPLE_TYPE)) return false;
    boolean result_;
    result_ = consumeToken(builder_, SIMPLE_TYPE);
    if (!result_) result_ = type_reference(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // (<<range_scope INTEGER_LITERAL>>? ("*"? data_type0) <<range_scope INTEGER_LITERAL>>?)
  //   | (MAP ("<" data_type "," data_type ">")?)
  //   | (CHAN data_type)
  static boolean data_type_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type_body_0(builder_, level_ + 1);
    if (!result_) result_ = data_type_body_1(builder_, level_ + 1);
    if (!result_) result_ = data_type_body_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // <<range_scope INTEGER_LITERAL>>? ("*"? data_type0) <<range_scope INTEGER_LITERAL>>?
  private static boolean data_type_body_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type_body_0_0(builder_, level_ + 1);
    result_ = result_ && data_type_body_0_1(builder_, level_ + 1);
    result_ = result_ && data_type_body_0_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // <<range_scope INTEGER_LITERAL>>?
  private static boolean data_type_body_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_0_0")) return false;
    range_scope(builder_, level_ + 1, INTEGER_LITERAL_parser_);
    return true;
  }

  // "*"? data_type0
  private static boolean data_type_body_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type_body_0_1_0(builder_, level_ + 1);
    result_ = result_ && data_type0(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // "*"?
  private static boolean data_type_body_0_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_0_1_0")) return false;
    consumeToken(builder_, MULTIPLY);
    return true;
  }

  // <<range_scope INTEGER_LITERAL>>?
  private static boolean data_type_body_0_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_0_2")) return false;
    range_scope(builder_, level_ + 1, INTEGER_LITERAL_parser_);
    return true;
  }

  // MAP ("<" data_type "," data_type ">")?
  private static boolean data_type_body_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, MAP);
    result_ = result_ && data_type_body_1_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ("<" data_type "," data_type ">")?
  private static boolean data_type_body_1_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_1_1")) return false;
    data_type_body_1_1_0(builder_, level_ + 1);
    return true;
  }

  // "<" data_type "," data_type ">"
  private static boolean data_type_body_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LANGLE);
    result_ = result_ && data_type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, COMMA);
    result_ = result_ && data_type(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, RANGLE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // CHAN data_type
  private static boolean data_type_body_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "data_type_body_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, CHAN);
    result_ = result_ && data_type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "def" key_value_list_block
  static boolean def_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "def_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = def_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "def");
    pinned_ = result_; // pin = 2
    result_ = result_ && key_value_list_block(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean def_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "def_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "def_object_0", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // "{" "}"
  static boolean empty_block(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "empty_block")) return false;
    if (!nextTokenIs(builder_, LBRACE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, LBRACE, RBRACE);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "endpoints" (operation_list_block | empty_block)
  static boolean endpoints_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endpoints_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = endpoints_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "endpoints");
    pinned_ = result_; // pin = 2
    result_ = result_ && endpoints_object_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean endpoints_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endpoints_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "endpoints_object_0", pos_)) break;
    }
    return true;
  }

  // operation_list_block | empty_block
  private static boolean endpoints_object_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "endpoints_object_2")) return false;
    boolean result_;
    result_ = operation_list_block(builder_, level_ + 1);
    if (!result_) result_ = empty_block(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // annotation* ("func" | "xapi") func_name "(" func_params? ")" func_return?
  static boolean func_declaration(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_declaration")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = func_declaration_0(builder_, level_ + 1);
    result_ = result_ && func_declaration_1(builder_, level_ + 1);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, func_name(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, LPAREN)) && result_;
    result_ = pinned_ && report_error_(builder_, func_declaration_4(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, consumeToken(builder_, RPAREN)) && result_;
    result_ = pinned_ && func_declaration_6(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean func_declaration_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_declaration_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "func_declaration_0", pos_)) break;
    }
    return true;
  }

  // "func" | "xapi"
  private static boolean func_declaration_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_declaration_1")) return false;
    boolean result_;
    result_ = consumeToken(builder_, "func");
    if (!result_) result_ = consumeToken(builder_, "xapi");
    return result_;
  }

  // func_params?
  private static boolean func_declaration_4(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_declaration_4")) return false;
    func_params(builder_, level_ + 1);
    return true;
  }

  // func_return?
  private static boolean func_declaration_6(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_declaration_6")) return false;
    func_return(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean func_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_name")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    exit_section_(builder_, marker_, FUNC_NAME, result_);
    return result_;
  }

  /* ********************************************************** */
  // IDENTIFIER ("," IDENTIFIER)* data_type
  static boolean func_param_group(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_param_group")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    result_ = result_ && func_param_group_1(builder_, level_ + 1);
    result_ = result_ && data_type(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ("," IDENTIFIER)*
  private static boolean func_param_group_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_param_group_1")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!func_param_group_1_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "func_param_group_1", pos_)) break;
    }
    return true;
  }

  // "," IDENTIFIER
  private static boolean func_param_group_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_param_group_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeTokens(builder_, 0, COMMA, IDENTIFIER);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<comma_separated_list func_param_group>>
  static boolean func_params(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::func_param_group);
  }

  /* ********************************************************** */
  // "(" <<comma_separated_list data_type>> ")"
  static boolean func_return(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "func_return")) return false;
    if (!nextTokenIs(builder_, LPAREN)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LPAREN);
    result_ = result_ && comma_separated_list(builder_, level_ + 1, XidlParser::data_type);
    result_ = result_ && consumeToken(builder_, RPAREN);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // "~" <<block_meta key_type_list>>
  public static boolean http_cookie(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_cookie")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, HTTP_COOKIE, "<http cookie>");
    result_ = consumeToken(builder_, "~");
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::key_type_list);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // "!" <<block_meta key_type_list>>
  public static boolean http_header(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_header")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, HTTP_HEADER, "<http header>");
    result_ = consumeToken(builder_, "!");
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::key_type_list);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // ("/" (text | <<block_meta (key_type ","?)>>))+
  static boolean http_path(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_path")) return false;
    if (!nextTokenIs(builder_, DIVIDE)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = http_path_0(builder_, level_ + 1);
    while (result_) {
      int pos_ = current_position_(builder_);
      if (!http_path_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "http_path", pos_)) break;
    }
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // "/" (text | <<block_meta (key_type ","?)>>)
  private static boolean http_path_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_path_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, DIVIDE);
    result_ = result_ && http_path_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // text | <<block_meta (key_type ","?)>>
  private static boolean http_path_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_path_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = text(builder_, level_ + 1);
    if (!result_) result_ = block_meta(builder_, level_ + 1, XidlParser::http_path_0_1_1_0);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // key_type ","?
  private static boolean http_path_0_1_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_path_0_1_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = key_type(builder_, level_ + 1);
    result_ = result_ && http_path_0_1_1_0_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ","?
  private static boolean http_path_0_1_1_0_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_path_0_1_1_0_1")) return false;
    consumeToken(builder_, COMMA);
    return true;
  }

  /* ********************************************************** */
  // "?" <<block_meta key_type_list>>
  public static boolean http_query(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_query")) return false;
    if (!nextTokenIs(builder_, QUESTION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, QUESTION);
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::key_type_list);
    exit_section_(builder_, marker_, HTTP_QUERY, result_);
    return result_;
  }

  /* ********************************************************** */
  // "." STRING_LITERAL? (data_type | <<block_meta key_type_list>>)
  public static boolean http_request_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_request_body")) return false;
    if (!nextTokenIs(builder_, DOT)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, DOT);
    result_ = result_ && http_request_body_1(builder_, level_ + 1);
    result_ = result_ && http_request_body_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, HTTP_REQUEST_BODY, result_);
    return result_;
  }

  // STRING_LITERAL?
  private static boolean http_request_body_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_request_body_1")) return false;
    consumeToken(builder_, STRING_LITERAL);
    return true;
  }

  // data_type | <<block_meta key_type_list>>
  private static boolean http_request_body_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_request_body_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type(builder_, level_ + 1);
    if (!result_) result_ = block_meta(builder_, level_ + 1, XidlParser::key_type_list);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // http_query | http_header | http_cookie | http_request_body
  static boolean http_request_info(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_request_info")) return false;
    boolean result_;
    result_ = http_query(builder_, level_ + 1);
    if (!result_) result_ = http_header(builder_, level_ + 1);
    if (!result_) result_ = http_cookie(builder_, level_ + 1);
    if (!result_) result_ = http_request_body(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // http_response_header? http_status_and_response_body http_response_header?
  public static boolean http_response(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_response")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, HTTP_RESPONSE, "<http response>");
    result_ = http_response_0(builder_, level_ + 1);
    result_ = result_ && http_status_and_response_body(builder_, level_ + 1);
    result_ = result_ && http_response_2(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  // http_response_header?
  private static boolean http_response_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_response_0")) return false;
    http_response_header(builder_, level_ + 1);
    return true;
  }

  // http_response_header?
  private static boolean http_response_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_response_2")) return false;
    http_response_header(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // "!" http_header
  static boolean http_response_header(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_response_header")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, "!");
    result_ = result_ && http_header(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL STRING_LITERAL? (data_type | <<block_meta key_type_list>>)?
  static boolean http_status_and_response_body(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_status_and_response_body")) return false;
    if (!nextTokenIs(builder_, INTEGER_LITERAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, INTEGER_LITERAL);
    result_ = result_ && http_status_and_response_body_1(builder_, level_ + 1);
    result_ = result_ && http_status_and_response_body_2(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // STRING_LITERAL?
  private static boolean http_status_and_response_body_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_status_and_response_body_1")) return false;
    consumeToken(builder_, STRING_LITERAL);
    return true;
  }

  // (data_type | <<block_meta key_type_list>>)?
  private static boolean http_status_and_response_body_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_status_and_response_body_2")) return false;
    http_status_and_response_body_2_0(builder_, level_ + 1);
    return true;
  }

  // data_type | <<block_meta key_type_list>>
  private static boolean http_status_and_response_body_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "http_status_and_response_body_2_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = data_type(builder_, level_ + 1);
    if (!result_) result_ = block_meta(builder_, level_ + 1, XidlParser::key_type_list);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "include" <<block_meta include_path_list?>>
  static boolean include_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "include_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = include_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "include");
    pinned_ = result_; // pin = 2
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::include_object_2_0);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean include_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "include_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "include_object_0", pos_)) break;
    }
    return true;
  }

  // include_path_list?
  private static boolean include_object_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "include_object_2_0")) return false;
    include_path_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // STRING_LITERAL
  public static boolean include_path(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "include_path")) return false;
    if (!nextTokenIs(builder_, STRING_LITERAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, STRING_LITERAL);
    exit_section_(builder_, marker_, INCLUDE_PATH, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<comma_separated_list include_path>>
  static boolean include_path_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::include_path);
  }

  /* ********************************************************** */
  // annotation* text "?"? ":" data_type ("=" literal)?
  static boolean key_type(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_type")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = key_type_0(builder_, level_ + 1);
    result_ = result_ && text(builder_, level_ + 1);
    result_ = result_ && key_type_2(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, COLON);
    result_ = result_ && data_type(builder_, level_ + 1);
    result_ = result_ && key_type_5(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // annotation*
  private static boolean key_type_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_type_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "key_type_0", pos_)) break;
    }
    return true;
  }

  // "?"?
  private static boolean key_type_2(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_type_2")) return false;
    consumeToken(builder_, QUESTION);
    return true;
  }

  // ("=" literal)?
  private static boolean key_type_5(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_type_5")) return false;
    key_type_5_0(builder_, level_ + 1);
    return true;
  }

  // "=" literal
  private static boolean key_type_5_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_type_5_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, ASSIGN);
    result_ = result_ && literal(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<comma_separated_list key_type>>
  static boolean key_type_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::key_type);
  }

  /* ********************************************************** */
  // <<block_meta key_type_list>>
  static boolean key_type_list_block(PsiBuilder builder_, int level_) {
    return block_meta(builder_, level_ + 1, XidlParser::key_type_list);
  }

  /* ********************************************************** */
  // annotation* ((text "=" literal) | STRING_LITERAL | INTEGER_LITERAL)
  static boolean key_value(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_value")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = key_value_0(builder_, level_ + 1);
    result_ = result_ && key_value_1(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // annotation*
  private static boolean key_value_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_value_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "key_value_0", pos_)) break;
    }
    return true;
  }

  // (text "=" literal) | STRING_LITERAL | INTEGER_LITERAL
  private static boolean key_value_1(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_value_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = key_value_1_0(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    if (!result_) result_ = consumeToken(builder_, INTEGER_LITERAL);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // text "=" literal
  private static boolean key_value_1_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "key_value_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = text(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, ASSIGN);
    result_ = result_ && literal(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<comma_separated_list key_value>>
  static boolean key_value_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::key_value);
  }

  /* ********************************************************** */
  // <<block_meta key_value_list>>
  static boolean key_value_list_block(PsiBuilder builder_, int level_) {
    return block_meta(builder_, level_ + 1, XidlParser::key_value_list);
  }

  /* ********************************************************** */
  // "cmd" | "def" | "tag" | text_empty_object_key | "func" | "xapi" | "schema" | "endpoints" | "mdepends" | "include"
  static boolean known_object_key(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "known_object_key")) return false;
    boolean result_;
    result_ = consumeToken(builder_, "cmd");
    if (!result_) result_ = consumeToken(builder_, "def");
    if (!result_) result_ = consumeToken(builder_, "tag");
    if (!result_) result_ = text_empty_object_key(builder_, level_ + 1);
    if (!result_) result_ = consumeToken(builder_, "func");
    if (!result_) result_ = consumeToken(builder_, "xapi");
    if (!result_) result_ = consumeToken(builder_, "schema");
    if (!result_) result_ = consumeToken(builder_, "endpoints");
    if (!result_) result_ = consumeToken(builder_, "mdepends");
    if (!result_) result_ = consumeToken(builder_, "include");
    return result_;
  }

  /* ********************************************************** */
  // INTEGER_LITERAL | FLOAT_LITERAL | BOOLEAN_LITERAL | STRING_LITERAL
  static boolean literal(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "literal")) return false;
    boolean result_;
    result_ = consumeToken(builder_, INTEGER_LITERAL);
    if (!result_) result_ = consumeToken(builder_, FLOAT_LITERAL);
    if (!result_) result_ = consumeToken(builder_, BOOLEAN_LITERAL);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "mdepends" <<block_meta string_list?>>
  static boolean mdepends_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mdepends_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = mdepends_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "mdepends");
    pinned_ = result_; // pin = 2
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::mdepends_object_2_0);
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean mdepends_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mdepends_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "mdepends_object_0", pos_)) break;
    }
    return true;
  }

  // string_list?
  private static boolean mdepends_object_2_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mdepends_object_2_0")) return false;
    string_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // <<block_meta (key_value_list | key_type_list | string_list | operation_list | common_object)*>>
  static boolean mixed_block(PsiBuilder builder_, int level_) {
    return block_meta(builder_, level_ + 1, XidlParser::mixed_block_0_0);
  }

  // (key_value_list | key_type_list | string_list | operation_list | common_object)*
  private static boolean mixed_block_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mixed_block_0_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!mixed_block_0_0_0(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "mixed_block_0_0", pos_)) break;
    }
    return true;
  }

  // key_value_list | key_type_list | string_list | operation_list | common_object
  private static boolean mixed_block_0_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "mixed_block_0_0_0")) return false;
    boolean result_;
    result_ = key_value_list(builder_, level_ + 1);
    if (!result_) result_ = key_type_list(builder_, level_ + 1);
    if (!result_) result_ = string_list(builder_, level_ + 1);
    if (!result_) result_ = operation_list(builder_, level_ + 1);
    if (!result_) result_ = common_object(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // MULTI_PARAM_ANNOTATION <<block_meta anno_string_list>>
  static boolean multi_param_anno(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "multi_param_anno")) return false;
    if (!nextTokenIs(builder_, MULTI_PARAM_ANNOTATION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, MULTI_PARAM_ANNOTATION);
    result_ = result_ && block_meta(builder_, level_ + 1, XidlParser::anno_string_list);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // annotation* HTTP_METHOD http_path http_request_info* http_response ";"
  public static boolean operation(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "operation")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, OPERATION, "<operation>");
    result_ = operation_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, HTTP_METHOD);
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, http_path(builder_, level_ + 1));
    result_ = pinned_ && report_error_(builder_, operation_3(builder_, level_ + 1)) && result_;
    result_ = pinned_ && report_error_(builder_, http_response(builder_, level_ + 1)) && result_;
    result_ = pinned_ && consumeToken(builder_, SEMICOLON) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean operation_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "operation_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "operation_0", pos_)) break;
    }
    return true;
  }

  // http_request_info*
  private static boolean operation_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "operation_3")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!http_request_info(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "operation_3", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // <<comma_separated_list operation>>
  static boolean operation_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, XidlParser::operation);
  }

  /* ********************************************************** */
  // <<block_meta operation_list>>
  static boolean operation_list_block(PsiBuilder builder_, int level_) {
    return block_meta(builder_, level_ + 1, XidlParser::operation_list);
  }

  /* ********************************************************** */
  // "[" ((<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>))? "]"
  static boolean range_scope(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope")) return false;
    if (!nextTokenIs(builder_, LBRACKET)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, LBRACKET);
    result_ = result_ && range_scope_1(builder_, level_ + 1, param);
    result_ = result_ && consumeToken(builder_, RBRACKET);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // ((<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>))?
  private static boolean range_scope_1(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope_1")) return false;
    range_scope_1_0(builder_, level_ + 1, param);
    return true;
  }

  // (<<param>> "," <<param>>) | (<<param>> ",") | ("," <<param>>)
  private static boolean range_scope_1_0(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope_1_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = range_scope_1_0_0(builder_, level_ + 1, param);
    if (!result_) result_ = range_scope_1_0_1(builder_, level_ + 1, param);
    if (!result_) result_ = range_scope_1_0_2(builder_, level_ + 1, param);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // <<param>> "," <<param>>
  private static boolean range_scope_1_0_0(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope_1_0_0")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = param.parse(builder_, level_);
    result_ = result_ && consumeToken(builder_, COMMA);
    result_ = result_ && param.parse(builder_, level_);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // <<param>> ","
  private static boolean range_scope_1_0_1(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope_1_0_1")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = param.parse(builder_, level_);
    result_ = result_ && consumeToken(builder_, COMMA);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // "," <<param>>
  private static boolean range_scope_1_0_2(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "range_scope_1_0_2")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, COMMA);
    result_ = result_ && param.parse(builder_, level_);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // text
  public static boolean schema_name(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "schema_name")) return false;
    if (!nextTokenIs(builder_, "<schema name>", IDENTIFIER, STRING_LITERAL)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SCHEMA_NAME, "<schema name>");
    result_ = text(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // annotation* "schema" schema_name (key_value_list_block | key_type_list_block | empty_block)?
  public static boolean schema_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "schema_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_, SCHEMA_OBJECT, "<schema object>");
    result_ = schema_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "schema");
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, schema_name(builder_, level_ + 1));
    result_ = pinned_ && schema_object_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean schema_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "schema_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "schema_object_0", pos_)) break;
    }
    return true;
  }

  // (key_value_list_block | key_type_list_block | empty_block)?
  private static boolean schema_object_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "schema_object_3")) return false;
    schema_object_3_0(builder_, level_ + 1);
    return true;
  }

  // key_value_list_block | key_type_list_block | empty_block
  private static boolean schema_object_3_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "schema_object_3_0")) return false;
    boolean result_;
    result_ = key_value_list_block(builder_, level_ + 1);
    if (!result_) result_ = key_type_list_block(builder_, level_ + 1);
    if (!result_) result_ = empty_block(builder_, level_ + 1);
    return result_;
  }

  /* ********************************************************** */
  // SINGLE_PARAM_ANNOTATION anno_string
  static boolean single_param_anno(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "single_param_anno")) return false;
    if (!nextTokenIs(builder_, SINGLE_PARAM_ANNOTATION)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, SINGLE_PARAM_ANNOTATION);
    result_ = result_ && anno_string(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  /* ********************************************************** */
  // <<block_meta string_list>>
  static boolean string_List_block(PsiBuilder builder_, int level_) {
    return block_meta(builder_, level_ + 1, XidlParser::string_list);
  }

  /* ********************************************************** */
  // <<comma_separated_list STRING_LITERAL>>
  static boolean string_list(PsiBuilder builder_, int level_) {
    return comma_separated_list(builder_, level_ + 1, STRING_LITERAL_parser_);
  }

  /* ********************************************************** */
  // annotation* "tag" text <<block_meta string_list?>>?
  static boolean tag_object(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tag_object")) return false;
    boolean result_, pinned_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = tag_object_0(builder_, level_ + 1);
    result_ = result_ && consumeToken(builder_, "tag");
    pinned_ = result_; // pin = 2
    result_ = result_ && report_error_(builder_, text(builder_, level_ + 1));
    result_ = pinned_ && tag_object_3(builder_, level_ + 1) && result_;
    exit_section_(builder_, level_, marker_, result_, pinned_, null);
    return result_ || pinned_;
  }

  // annotation*
  private static boolean tag_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tag_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "tag_object_0", pos_)) break;
    }
    return true;
  }

  // <<block_meta string_list?>>?
  private static boolean tag_object_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tag_object_3")) return false;
    block_meta(builder_, level_ + 1, XidlParser::tag_object_3_0_0);
    return true;
  }

  // string_list?
  private static boolean tag_object_3_0_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "tag_object_3_0_0")) return false;
    string_list(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // IDENTIFIER | STRING_LITERAL
  static boolean text(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text")) return false;
    if (!nextTokenIs(builder_, "", IDENTIFIER, STRING_LITERAL)) return false;
    boolean result_;
    result_ = consumeToken(builder_, IDENTIFIER);
    if (!result_) result_ = consumeToken(builder_, STRING_LITERAL);
    return result_;
  }

  /* ********************************************************** */
  // annotation* <<param>> text empty_block?
  static boolean text_empty_block_object(PsiBuilder builder_, int level_, Parser param) {
    if (!recursion_guard_(builder_, level_, "text_empty_block_object")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = text_empty_block_object_0(builder_, level_ + 1);
    result_ = result_ && param.parse(builder_, level_);
    result_ = result_ && text(builder_, level_ + 1);
    result_ = result_ && text_empty_block_object_3(builder_, level_ + 1);
    exit_section_(builder_, marker_, null, result_);
    return result_;
  }

  // annotation*
  private static boolean text_empty_block_object_0(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text_empty_block_object_0")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!annotation(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "text_empty_block_object_0", pos_)) break;
    }
    return true;
  }

  // empty_block?
  private static boolean text_empty_block_object_3(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text_empty_block_object_3")) return false;
    empty_block(builder_, level_ + 1);
    return true;
  }

  /* ********************************************************** */
  // "app" | "url" | "version" | "module" | "middleware" | "mcode" | "mname"
  static boolean text_empty_object_key(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "text_empty_object_key")) return false;
    boolean result_;
    result_ = consumeToken(builder_, "app");
    if (!result_) result_ = consumeToken(builder_, "url");
    if (!result_) result_ = consumeToken(builder_, "version");
    if (!result_) result_ = consumeToken(builder_, "module");
    if (!result_) result_ = consumeToken(builder_, "middleware");
    if (!result_) result_ = consumeToken(builder_, "mcode");
    if (!result_) result_ = consumeToken(builder_, "mname");
    return result_;
  }

  /* ********************************************************** */
  // comment | func_declaration | all_object | operation
  static boolean top_level_item(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "top_level_item")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NONE_);
    result_ = comment(builder_, level_ + 1);
    if (!result_) result_ = func_declaration(builder_, level_ + 1);
    if (!result_) result_ = all_object(builder_, level_ + 1);
    if (!result_) result_ = operation(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, XidlParser::top_level_recover);
    return result_;
  }

  /* ********************************************************** */
  // !top_level_start
  static boolean top_level_recover(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "top_level_recover")) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_, level_, _NOT_);
    result_ = !top_level_start(builder_, level_ + 1);
    exit_section_(builder_, level_, marker_, result_, false, null);
    return result_;
  }

  /* ********************************************************** */
  // KEYWORD
  //   | HTTP_METHOD
  //   | ZERO_PARAM_ANNOTATION
  //   | SINGLE_PARAM_ANNOTATION
  //   | MULTI_PARAM_ANNOTATION
  //   | CUSTOM_ANNOTATION
  //   | LINE_COMMENT
  //   | BLOCK_COMMENT
  static boolean top_level_start(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "top_level_start")) return false;
    boolean result_;
    result_ = consumeToken(builder_, KEYWORD);
    if (!result_) result_ = consumeToken(builder_, HTTP_METHOD);
    if (!result_) result_ = consumeToken(builder_, ZERO_PARAM_ANNOTATION);
    if (!result_) result_ = consumeToken(builder_, SINGLE_PARAM_ANNOTATION);
    if (!result_) result_ = consumeToken(builder_, MULTI_PARAM_ANNOTATION);
    if (!result_) result_ = consumeToken(builder_, CUSTOM_ANNOTATION);
    if (!result_) result_ = consumeToken(builder_, LINE_COMMENT);
    if (!result_) result_ = consumeToken(builder_, BLOCK_COMMENT);
    return result_;
  }

  /* ********************************************************** */
  // IDENTIFIER
  public static boolean type_reference(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "type_reference")) return false;
    if (!nextTokenIs(builder_, IDENTIFIER)) return false;
    boolean result_;
    Marker marker_ = enter_section_(builder_);
    result_ = consumeToken(builder_, IDENTIFIER);
    exit_section_(builder_, marker_, TYPE_REFERENCE, result_);
    return result_;
  }

  /* ********************************************************** */
  // top_level_item*
  static boolean xidlFile(PsiBuilder builder_, int level_) {
    if (!recursion_guard_(builder_, level_, "xidlFile")) return false;
    while (true) {
      int pos_ = current_position_(builder_);
      if (!top_level_item(builder_, level_ + 1)) break;
      if (!empty_element_parsed_guard_(builder_, "xidlFile", pos_)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // ZERO_PARAM_ANNOTATION
  static boolean zero_param_anno(PsiBuilder builder_, int level_) {
    return consumeToken(builder_, ZERO_PARAM_ANNOTATION);
  }

  static final Parser INTEGER_LITERAL_parser_ = (builder_, level_) -> consumeToken(builder_, INTEGER_LITERAL);
  static final Parser STRING_LITERAL_parser_ = (builder_, level_) -> consumeToken(builder_, STRING_LITERAL);
}
