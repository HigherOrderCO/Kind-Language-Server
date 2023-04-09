package dev.aripiprazole.kind.idea

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import kotlin.properties.ReadOnlyProperty

class KindHighlighter : SyntaxHighlighterBase() {
  override fun getHighlightingLexer(): Lexer {
    return KindLexerAdapter()
  }

  override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
    return when (tokenType) {
      KindTypes.ATOM -> arrayOf(LOCAL_KEYS)
      KindTypes.CONSTRUCTOR -> arrayOf(CONSTRUCTOR_KEYS)
      KindTypes.PARAMETER -> arrayOf(KEYWORD_KEYS)
      KindTypes.STRING_LITERAL -> arrayOf(STRING_KEYS)

      KindTypes.U60_LITERAL,
      KindTypes.U120_LITERAL,
      KindTypes.F60_LITERAL,
      KindTypes.NAT_LITERAL,
      -> arrayOf(NUMBER_KEYS)

      KindTypes.DO,
      KindTypes.LET,
      KindTypes.ASK,
      KindTypes.AS,
      KindTypes.USE,
      KindTypes.TYPE,
      KindTypes.RECORD,
      -> arrayOf(KEYWORD_KEYS)

      KindTypes.HASHTAG -> arrayOf(ATTRIBUTE_KEYS)

      KindTypes.LEFT_BRACKET, KindTypes.RIGHT_BRACKET -> arrayOf(BRACKET_KEYS)
      KindTypes.LEFT_PAREN, KindTypes.RIGHT_PAREN -> arrayOf(PARENTHESES_KEYS)
      KindTypes.LEFT_BRACE, KindTypes.RIGHT_BRACE -> arrayOf(BRACE_KEYS)
      KindTypes.DOT -> arrayOf(DOT_KEYS)
      KindTypes.SEMI -> arrayOf(SEMI_KEYS)

      KindTypes.COMMENT -> arrayOf(COMMENT_KEYS)
      KindTypes.DOC_STRING -> arrayOf(DOC_STRING_KEYS)

      TokenType.BAD_CHARACTER -> arrayOf(BAD_CHAR_KEYS)
      else -> emptyArray()
    }
  }

  companion object {
    val BAD_CHAR_KEYS by attributesKey(HighlighterColors.BAD_CHARACTER)
    val COMMENT_KEYS by attributesKey(DefaultLanguageHighlighterColors.LINE_COMMENT)
    val DOC_STRING_KEYS by attributesKey(DefaultLanguageHighlighterColors.DOC_COMMENT)
    val CONSTRUCTOR_KEYS by attributesKey(DefaultLanguageHighlighterColors.GLOBAL_VARIABLE)
    val TYPE_NAME_KEYS by attributesKey(DefaultLanguageHighlighterColors.CLASS_REFERENCE)
    val TYPE_PARAMETER_KEYS by attributesKey(DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
    val VAL_NAME_KEYS by attributesKey(DefaultLanguageHighlighterColors.FUNCTION_DECLARATION)
    val PARAMETER_KEYS by attributesKey(DefaultLanguageHighlighterColors.PARAMETER)
    val LOCAL_KEYS by attributesKey(DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
    val STRING_KEYS by attributesKey(DefaultLanguageHighlighterColors.STRING)
    val NUMBER_KEYS by attributesKey(DefaultLanguageHighlighterColors.NUMBER)
    val KEYWORD_KEYS by attributesKey(DefaultLanguageHighlighterColors.KEYWORD)
    val SEMI_KEYS by attributesKey(DefaultLanguageHighlighterColors.SEMICOLON)
    val DOT_KEYS by attributesKey(DefaultLanguageHighlighterColors.DOT)
    val BRACKET_KEYS by attributesKey(DefaultLanguageHighlighterColors.BRACKETS)
    val BRACE_KEYS by attributesKey(DefaultLanguageHighlighterColors.BRACES)
    val PARENTHESES_KEYS by attributesKey(DefaultLanguageHighlighterColors.PARENTHESES)
    val ATTRIBUTE_KEYS by attributesKey(DefaultLanguageHighlighterColors.METADATA)

    private fun attributesKey(
      attribute: TextAttributesKey,
    ): ReadOnlyProperty<Any, TextAttributesKey> {
      var value: TextAttributesKey? = null

      return ReadOnlyProperty { _, property ->
        if (value == null) {
          value = createTextAttributesKey("KIND_${property.name.uppercase()}", attribute)
        }

        value!!
      }
    }
  }
}
