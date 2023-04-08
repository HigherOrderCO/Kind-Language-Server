package dev.aripiprazole.kind.idea

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider

class KindFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, KindLanguage) {
  override fun getFileType(): FileType = KindFileType

  override fun toString(): String = "Kind File"
}
