package dev.aripiprazole.kind.idea

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.tree.IElementType
import dev.aripiprazole.kind.idea.lexer.KindLexerAdapter
import kotlin.properties.ReadOnlyProperty

class KindHighlighter : SyntaxHighlighter {
  override fun getHighlightingLexer(): Lexer {
    return KindLexerAdapter()
  }

  override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {
    return emptyArray()
  }

  companion object {
    fun attributesKey(attribute: TextAttributesKey): ReadOnlyProperty<Any, TextAttributesKey> {
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
