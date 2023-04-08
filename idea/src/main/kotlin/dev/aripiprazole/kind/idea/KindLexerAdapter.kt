@file:Suppress("UnusedImport")

package dev.aripiprazole.kind.idea

import com.intellij.lexer.FlexAdapter
import dev.aripiprazole.kind.idea._KindLexer

class KindLexerAdapter : FlexAdapter(_KindLexer(null))
