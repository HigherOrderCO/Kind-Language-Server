package dev.aripiprazole.kind.idea

import com.intellij.psi.PsiElement
import dev.aripiprazole.kind.idea.psi.KindAttributeName
import dev.aripiprazole.kind.idea.psi.KindDoType
import dev.aripiprazole.kind.idea.psi.KindRuleBlockValue
import dev.aripiprazole.kind.idea.psi.KindRuleName
import dev.aripiprazole.kind.idea.psi.KindRulePattern
import dev.aripiprazole.kind.idea.psi.KindRuleValue
import dev.aripiprazole.kind.idea.psi.KindTypeDeclarationName
import dev.aripiprazole.kind.idea.psi.KindTypeExpr
import dev.aripiprazole.kind.idea.psi.KindTypeName

fun PsiElement.isInsideTyping(): Boolean {
  if (this is KindTypeExpr) return true
  if (this is KindTypeName) return true

  return parent?.isInsideTyping() ?: false
}

fun PsiElement.isInsideRuleName(): Boolean {
  if (this is KindRuleName) return true

  return parent?.isInsideRuleName() ?: false
}

fun PsiElement.isInsideTypeDeclarationName(): Boolean {
  if (this is KindTypeDeclarationName) return true

  return parent?.isInsideTypeDeclarationName() ?: false
}

fun PsiElement.isInsideRuleValue(): Boolean {
  if (this is KindRuleBlockValue) return true
  if (this is KindRuleValue) return true

  return parent?.isInsideRuleValue() ?: false
}

fun PsiElement.isInsideDoType(): Boolean {
  if (this is KindDoType) return true

  return parent?.isInsideDoType() ?: false
}

fun PsiElement.isInsideRulePattern(): Boolean {
  if (this is KindRulePattern) return true

  return parent?.isInsideRulePattern() ?: false
}

fun PsiElement.isInsideAttribute(): Boolean {
  if (this is KindAttributeName) return true

  return parent?.isInsideAttribute() ?: false
}
