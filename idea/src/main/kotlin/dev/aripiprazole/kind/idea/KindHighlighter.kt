package dev.aripiprazole.kind.idea

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import kotlin.properties.ReadOnlyProperty

class KindHighlighter : SyntaxHighlighter {
  override fun getHighlightingLexer(): Lexer {
    return KindLexerAdapter()
  }

  override fun getTokenHighlights(tokenType: IElementType): Array<TextAttributesKey> {
    return when (tokenType) {
      KindTypes.SEPARATOR -> arrayOf(SEPARATOR_KEYS)
      KindTypes.KEY -> arrayOf(KEY_KEYS)
      KindTypes.VALUE -> arrayOf(VALUE_KEYS)
      KindTypes.COMMENT -> arrayOf(COMMENT_KEYS)
      TokenType.BAD_CHARACTER -> arrayOf(BAD_CHAR_KEYS)
      else -> emptyArray()
    }
  }

  companion object {
    val BAD_CHAR_KEYS by attributesKey(HighlighterColors.BAD_CHARACTER)
    val SEPARATOR_KEYS by attributesKey(DefaultLanguageHighlighterColors.OPERATION_SIGN)
    val KEY_KEYS by attributesKey(DefaultLanguageHighlighterColors.KEYWORD)
    val VALUE_KEYS by attributesKey(DefaultLanguageHighlighterColors.STRING)
    val COMMENT_KEYS by attributesKey(DefaultLanguageHighlighterColors.LINE_COMMENT)

    private fun attributesKey(
      attribute: TextAttributesKey,
    ): ReadOnlyProperty<Any, TextAttributesKey> {
      var value: TextAttributesKey? = null

      return ReadOnlyProperty { _, property ->
        if (value == null) {
          value = TextAttributesKey.createTextAttributesKey(
            "KIND_${property.name.toUpperCase()}",
            attribute,
          )
        }

        value!!
      }
    }
  }
}
