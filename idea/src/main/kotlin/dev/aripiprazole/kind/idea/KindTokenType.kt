package dev.aripiprazole.kind.idea

import com.intellij.psi.tree.IElementType

class KindTokenType(debugName: String) : IElementType(debugName, KindLanguage) {
  override fun toString(): String = "KindToken.${super.toString()}"
}
