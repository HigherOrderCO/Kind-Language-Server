package dev.aripiprazole.kind.idea

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import dev.aripiprazole.kind.idea.parser.KindParser

class KindParserDefinition : ParserDefinition {
  override fun getFileNodeType(): IFileElementType = FILE
  override fun getCommentTokens(): TokenSet = KindTokenSets.COMMENTS
  override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

  override fun createLexer(project: Project?): Lexer {
    return KindLexerAdapter()
  }

  override fun createParser(project: Project?): PsiParser {
    return KindParser()
  }

  override fun createFile(viewProvider: FileViewProvider): PsiFile {
    return KindFile(viewProvider)
  }

  override fun createElement(node: ASTNode?): PsiElement {
    return KindTypes.Factory.createElement(node)
  }

  companion object {
    val FILE: IFileElementType = IFileElementType(KindLanguage)
  }
}
