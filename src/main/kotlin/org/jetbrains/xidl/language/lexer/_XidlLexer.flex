package org.jetbrains.xidl.language.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.jetbrains.xidl.language.psi.XidlTypes.*;

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
LITERAL_STRING=(\"[^\"\n]*\")|(`[^`]*`)
LITERAL_INTEGER=[0-9]+
LITERAL_BOOLEAN=true|false
LITERAL_FLOAT=[0-9]+\.[0-9]+
ANNOTATION=@[a-zA-Z_][a-zA-Z0-9_]*
KEYWORD=cmd|def|tag|app|url|version|module|include|schema|endpoints|middleware|mcode|mname|mdepends|func|xapi|get|post|put|patch|delete|options|head
SIMPLE_TYPE=byte|int|int8|int16|int32|int64|uint|uint8|uint16|uint32|uint64|float32|float64|bool|string|datetime|any|map|chan|interface|(map<.*>)
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*
LEFT_PAREN=\(
RIGHT_PAREN=\)
LEFT_BRACE=\{
RIGHT_BRACE=\}
LEFT_BRACKET=\[
RIGHT_BRACKET=\]

%%
<YYINITIAL> {
  {WHITE_SPACE}           { return WHITE_SPACE; }


  {LINE_COMMENT}          { return LINE_COMMENT; }
  {BLOCK_COMMENT}         { return BLOCK_COMMENT; }
  {LITERAL_STRING}        { return LITERAL_STRING; }
  {LITERAL_INTEGER}       { return LITERAL_INTEGER; }
  {LITERAL_BOOLEAN}       { return LITERAL_BOOLEAN; }
  {LITERAL_FLOAT}         { return LITERAL_FLOAT; }
  {ANNOTATION}            { return ANNOTATION; }
  {KEYWORD}               { return KEYWORD; }
  {SIMPLE_TYPE}           { return SIMPLE_TYPE; }
  {IDENTIFIER}            { return IDENTIFIER; }
  {LEFT_PAREN}            { return LEFT_PAREN; }
  {RIGHT_PAREN}           { return RIGHT_PAREN; }
  {LEFT_BRACE}            { return LEFT_BRACE; }
  {RIGHT_BRACE}           { return RIGHT_BRACE; }
  {LEFT_BRACKET}          { return LEFT_BRACKET; }
  {RIGHT_BRACKET}         { return RIGHT_BRACKET; }

}

[^] { return BAD_CHARACTER; }
