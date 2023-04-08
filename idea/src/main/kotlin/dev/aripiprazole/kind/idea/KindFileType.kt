package dev.aripiprazole.kind.idea

import com.intellij.openapi.fileTypes.LanguageFileType
import javax.swing.Icon

object KindFileType : LanguageFileType(KindLanguage) {
  override fun getName(): String = "Kind File"
  override fun getDescription(): String = "Kind's language file"
  override fun getDefaultExtension(): String = "kind2"
  override fun getIcon(): Icon = KindIcons.FILE
}
