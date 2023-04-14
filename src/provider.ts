import * as vscode from 'vscode';
import kind from './kind';
import legend, {types} from './legend';

const provider: vscode.DocumentSemanticTokensProvider = {
  provideDocumentSemanticTokens(document: vscode.TextDocument) {
    // analyze the document and return semantic tokens
    if (document.languageId !== 'kind') return;

    const tree = kind.parser.parse(document.getText());
    const terms = kind.highlight(tree).map((term) => {
      const key = term.term(document.getText(term.range));
      const token = types[key];
      return {
        type: token?.type,
        modifiers: token?.modifiers,
        ...term,
      };
    });

    const builder = new vscode.SemanticTokensBuilder(legend);

    for (const term of terms) {
      const {type, modifiers = [], range} = term;
      if (!type) continue;

      if (range.start.line === range.end.line) {
        builder.push(range, type, modifiers);
        continue;
      }

      let line = range.start.line;
      builder.push(
        new vscode.Range(range.start, document.lineAt(line).range.end),
        type,
        modifiers,
      );

      for (line = line + 1; line < range.end.line; line++) {
        builder.push(document.lineAt(line).range, type, modifiers);
      }

      builder.push(
        new vscode.Range(document.lineAt(line).range.start, range.end),
        type,
        modifiers,
      );
    }

    return builder.build();
  },
};

export default provider;
