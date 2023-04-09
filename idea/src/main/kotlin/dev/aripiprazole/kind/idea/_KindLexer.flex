package dev.aripiprazole.kind.idea;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.aripiprazole.kind.idea.KindTypes;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;

%%

%{
  public _KindLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _KindLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+
U60_LITERAL=[0-9]+(u60)?
U120_LITERAL=[0-9]+(u120)?
NAT_LITERAL=[0-9]+(n)?
F60_LITERAL=[0-9]+("." [0-9]+)?(f60)?
STRING_LITERAL = \" ([^\"\\\n\r]|\\[^\n\r])* \"
CHAR_LITERAL='[^'\\]'
LEFT_PAREN=\(
RIGHT_PAREN=\)
EQUAL=\=
CONSTRUCTOR=[a-zA-Z_][a-zA-Z0-9_/]*
ATOM=[a-z_][a-zA-Z0-9_/]*
CRLF=\R
DOT=\.
HELP_TOKEN=\?[a-zA-Z_.][a-zA-Z0-9_/.]*
UNARY_OP=[\+\-\*\/\%]
DOC_STRING="//!"[^\r\n]*\n
COMMENT="//"[^\r\n]*\n

%state WAITING_VALUE
%state EOF

%%

<YYINITIAL> {DOC_STRING}                                    { return KindTypes.DOC_STRING; }
<YYINITIAL> {COMMENT}                                       { return KindTypes.COMMENT; }
<YYINITIAL> "->"                                            { return KindTypes.ARROW; }
<YYINITIAL> "=>"                                            { return KindTypes.FAT_ARROW; }
<YYINITIAL> "~"                                             { return KindTypes.TILDE; }
<YYINITIAL> "."                                             { return KindTypes.DOT; }
<YYINITIAL> "<"                                             { return KindTypes.LT; }
<YYINITIAL> ">"                                             { return KindTypes.GT; }
<YYINITIAL> ":"                                             { return KindTypes.COLON; }
<YYINITIAL> "#"                                             { return KindTypes.HASHTAG; }
<YYINITIAL> ","                                             { return KindTypes.COMMA; }
<YYINITIAL> "["                                             { return KindTypes.LEFT_BRACKET; }
<YYINITIAL> "]"                                             { return KindTypes.RIGHT_BRACKET; }
<YYINITIAL> "{"                                             { return KindTypes.LEFT_BRACE; }
<YYINITIAL> "}"                                             { return KindTypes.RIGHT_BRACE; }
<YYINITIAL> "("                                             { return KindTypes.LEFT_PAREN; }
<YYINITIAL> ")"                                             { return KindTypes.RIGHT_PAREN; }
<YYINITIAL> "="                                             { return KindTypes.EQUAL; }
<YYINITIAL> "@"                                             { return KindTypes.AT; }
<YYINITIAL> ";"                                             { return KindTypes.SEMI; }
<YYINITIAL> {ATOM}                                          { return KindTypes.ATOM; }
<YYINITIAL> {CONSTRUCTOR}                                   { return KindTypes.CONSTRUCTOR; }
<YYINITIAL> {CHAR_LITERAL}                                  { return KindTypes.CHAR_LITERAL; }
<YYINITIAL> {F60_LITERAL}                                   { return KindTypes.F60_LITERAL; }
<YYINITIAL> {U60_LITERAL}                                   { return KindTypes.U60_LITERAL; }
<YYINITIAL> {U120_LITERAL}                                  { return KindTypes.U120_LITERAL; }
<YYINITIAL> {NAT_LITERAL}                                   { return KindTypes.NAT_LITERAL; }
<YYINITIAL> {STRING_LITERAL}                                { return KindTypes.STRING_LITERAL; }
<YYINITIAL> {CRLF}                                          { return KindTypes.CRLF; }
<YYINITIAL> <<EOF>>                                         { yybegin(EOF); return KindTypes.EOF; }
({WHITE_SPACE})+                                            { return WHITE_SPACE; }
[^]                                                         { return BAD_CHARACTER; }
