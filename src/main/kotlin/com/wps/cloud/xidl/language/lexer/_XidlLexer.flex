package com.wps.cloud.xidl.language.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.wps.cloud.xidl.language.psi.XidlTypes.*;

%%

%{
  public _XidlLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _XidlLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

LINE_COMMENT="//".*
BLOCK_COMMENT="/"\*(.|\n)*\*"/"
INTEGER_LITERAL=[0-9]+
FLOAT_LITERAL=[0-9]+\.[0-9]+
BOOLEAN_LITERAL=true|false
STRING_LITERAL=(\"[^\"\n]*\")|(`[^`]*`)
SIMPLE_TYPE=byte|int|int8|int16|int32|int64|uint|uint8|uint16|uint32|uint64|float32|float64|bool|string|datetime|any|map|chan|interface
KEYWORD=cmd|def|tag|app|url|version|module|include|schema|endpoints|middleware|mcode|mname|mdepends|func|xapi
HTTP_METHOD=get|post|put|patch|delete|options|head
ZERO_PARAM_ANNOTATION=@(noescape|deprecated|omitempty|pflag|pflagenv|order|nowrap|nodoc|nonstandard|external|i18n)
SINGLE_PARAM_ANNOTATION=@(desc|title|summary|table|column|template|format|name|use|short|shorthand|long|min|server|parent|type|xtype|etype|level|timeout)
MULTI_PARAM_ANNOTATION=@(tags|allof|oneof|anyof|middlewares|middleware_replace_map|x|permissions|aliases|scopes|identities|accountTags|companyTypes)
CUSTOM_ANNOTATION=@[a-zA-Z_][a-zA-Z0-9_]*
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*

%%
<YYINITIAL> {
  {WHITE_SPACE}                   { return WHITE_SPACE; }

  "("                             { return LPAREN; }
  ")"                             { return RPAREN; }
  "{"                             { return LBRACE; }
  "}"                             { return RBRACE; }
  "["                             { return LBRACKET; }
  "]"                             { return RBRACKET; }
  "<"                             { return LANGLE; }
  ">"                             { return RANGLE; }
  ","                             { return COMMA; }
  ";"                             { return SEMICOLON; }
  ":"                             { return COLON; }
  "."                             { return DOT; }
  "*"                             { return MULTIPLY; }
  "/"                             { return DIVIDE; }
  "?"                             { return QUESTION; }
  "="                             { return ASSIGN; }
  "<-"                            { return CHAN_SEND; }
  "->"                            { return CHAN_RECEIVE; }

  {LINE_COMMENT}                  { return LINE_COMMENT; }
  {BLOCK_COMMENT}                 { return BLOCK_COMMENT; }
  {INTEGER_LITERAL}               { return INTEGER_LITERAL; }
  {FLOAT_LITERAL}                 { return FLOAT_LITERAL; }
  {BOOLEAN_LITERAL}               { return BOOLEAN_LITERAL; }
  {STRING_LITERAL}                { return STRING_LITERAL; }
  {SIMPLE_TYPE}                   { return SIMPLE_TYPE; }
  {KEYWORD}                       { return KEYWORD; }
  {HTTP_METHOD}                   { return HTTP_METHOD; }
  {ZERO_PARAM_ANNOTATION}         { return ZERO_PARAM_ANNOTATION; }
  {SINGLE_PARAM_ANNOTATION}       { return SINGLE_PARAM_ANNOTATION; }
  {MULTI_PARAM_ANNOTATION}        { return MULTI_PARAM_ANNOTATION; }
  {CUSTOM_ANNOTATION}             { return CUSTOM_ANNOTATION; }
  {IDENTIFIER}                    { return IDENTIFIER; }

}

[^] { return BAD_CHARACTER; }
