package dev.aripiprazole.kind.idea

import com.intellij.psi.tree.TokenSet

object KindTokenSets {
  val IDENTIFIERS = TokenSet.create(KindTypes.KEY)
  val COMMENTS = TokenSet.create(KindTypes.COMMENT)
}
