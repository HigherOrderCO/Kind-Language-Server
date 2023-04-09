package dev.aripiprazole.kind.idea

import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiLiteralValue
import dev.aripiprazole.kind.idea.psi.KindAsKeyword
import dev.aripiprazole.kind.idea.psi.KindAskKeyword
import dev.aripiprazole.kind.idea.psi.KindDoKeyword
import dev.aripiprazole.kind.idea.psi.KindIdentifier
import dev.aripiprazole.kind.idea.psi.KindLetKeyword
import dev.aripiprazole.kind.idea.psi.KindLocalName
import dev.aripiprazole.kind.idea.psi.KindParameterName
import dev.aripiprazole.kind.idea.psi.KindPredefinedName
import dev.aripiprazole.kind.idea.psi.KindQualified
import dev.aripiprazole.kind.idea.psi.KindRecordKeyword
import dev.aripiprazole.kind.idea.psi.KindReturnKeyword
import dev.aripiprazole.kind.idea.psi.KindTypeKeyword
import dev.aripiprazole.kind.idea.psi.KindValName

class KindSyntaxAnnotator : Annotator {
  override fun annotate(element: PsiElement, holder: AnnotationHolder) {
    if (element is PsiLiteralValue) return

    when (element) {
      is KindTypeKeyword,
      is KindRecordKeyword,
      is KindAsKeyword,
      is KindAskKeyword,
      is KindLetKeyword,
      is KindReturnKeyword,
      is KindDoKeyword,
      -> {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(element.textRange)
          .highlightType(ProblemHighlightType.INFORMATION)
          .textAttributes(KindHighlighter.KEYWORD_KEYS)
          .tooltip("keyword ${element.text}")
          .create()
      }

      is KindIdentifier -> when {
        element.isInsideAttribute() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.ATTRIBUTE_KEYS)
            .tooltip("attribute ${element.text}")
            .create()
        }

        element.isInsideRuleName() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.VAL_NAME_KEYS)
            .tooltip("type-entry ${element.text}")
            .create()
        }

        element.isInsideRulePattern() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.LOCAL_KEYS)
            .tooltip("local-name ${element.text}")
            .create()
        }

        element.isInsideTyping() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.TYPE_PARAMETER_KEYS)
            .tooltip("Defined type parameter ${element.text}: Type")
            .create()
        }
      }

      is KindQualified -> when {
        element.isInsideTyping() && element.text in SPECIAL_CONSTRUCTORS -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.TYPE_NAME_KEYS)
            .tooltip("type-name ${element.text}: Type")
            .create()
        }

        element.isInsideRuleValue() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.VAL_NAME_KEYS)
            .tooltip("call ${element.text}")
            .create()
        }

        element.isInsideTyping() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.VAL_NAME_KEYS)
            .tooltip("type-name ${element.text}: Type")
            .create()
        }
      }

      is KindLocalName -> when {
        !element.isInsideRuleName() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.LOCAL_KEYS)
            .tooltip("local-name ${element.text}")
            .create()
        }
      }

      is KindPredefinedName -> {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(element.textRange)
          .highlightType(ProblemHighlightType.INFORMATION)
          .textAttributes(KindHighlighter.KEYWORD_KEYS)
          .tooltip("special ${element.text}")
          .create()
      }

      is KindValName -> when {
        element.isInsideRulePattern() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.TYPE_NAME_KEYS)
            .tooltip("type-name ${element.text}")
            .create()
        }

        element.isInsideTyping() -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.TYPE_NAME_KEYS)
            .tooltip("type-name ${element.text}")
            .create()
        }

        else -> {
          holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element.textRange)
            .highlightType(ProblemHighlightType.INFORMATION)
            .textAttributes(KindHighlighter.VAL_NAME_KEYS)
            .tooltip("Function '${element.text}' defined")
            .create()
        }
      }

      is KindParameterName -> {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
          .range(element.textRange)
          .highlightType(ProblemHighlightType.INFORMATION)
          .textAttributes(KindHighlighter.PARAMETER_KEYS)
          .tooltip("Parameter '${element.text}' defined")
          .create()
      }
    }
  }

  companion object {
    private val SPECIAL_EXPRESSIONS = listOf(
      "Bool.false",
      "Bool.true",
    )
    private val SPECIAL_CONSTRUCTORS = listOf(
      "Not",
      "Bool",
      "Type",
      "String",
      "List",
      "Either",
      "Maybe",
      "Map",
      "Nat",
      "F60",
      "U60",
      "U120",
      "U240",
    )
  }
}