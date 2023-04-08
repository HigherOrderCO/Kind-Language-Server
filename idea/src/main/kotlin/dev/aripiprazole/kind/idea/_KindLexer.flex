package dev.aripiprazole.kind.idea;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static dev.aripiprazole.kind.idea.KindTypes.*;

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


%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "COMMENT"          { return COMMENT; }
  "CRLF"             { return CRLF; }
  "KEY"              { return KEY; }
  "SEPARATOR"        { return SEPARATOR; }
  "VALUE"            { return VALUE; }


}

[^] { return BAD_CHARACTER; }
