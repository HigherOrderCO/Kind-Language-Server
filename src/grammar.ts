import * as vscode from 'vscode';
import Parser = require('web-tree-sitter');
import * as path from 'path';

type GrammarBuiler = {
  path: string;
  terms: Record<string, TermFn>;
  complex: string[];
  scopes: Record<string, TermFn>;
};

type TermFn = ((range: string) => string) | string;

type Term = {
  term: (range: string) => string;
  range: vscode.Range;
};

class Grammar {
  public parser!: Parser;

  // Grammar
  public readonly terms: Record<string, TermFn> = {};
  public readonly complex: string[] = [];
  public readonly scopes: Record<string, TermFn> = {};
  public readonly depth: number = 0;
  public readonly order: boolean = false;

  // Constructor
  public constructor(public readonly lang: string, builder: GrammarBuiler) {
    this.terms = builder.terms;
    this.complex = builder.complex;
    this.scopes = builder.scopes;
    for (const scope in this.scopes) {
      const depth = scope.split('>').length;
      if (depth > this.depth) this.depth = depth;
      if (scope.indexOf('[') >= 0) this.order = true;
    }
    this.depth--;
  }

  // Parser initialization
  public async setup() {
    const language = path.join(
      __dirname,
      '../tree-sitter-kind',
      'tree-sitter-kind.wasm',
    );
    const object = await Parser.Language.load(language);
    this.parser = new Parser();
    this.parser.setLanguage(object);
  }

  public parse(text: string) {
    return this.parser.parse(text);
  }

  public highlight(tree: Parser.Tree) {
    let terms = new Array<Term>();
    let stack = new Array<Parser.SyntaxNode>();
    let node: Parser.SyntaxNode | undefined | null = tree.rootNode.firstChild;

    while (stack.length > 0 || node) {
      // Go deeper
      if (node) {
        stack.push(node);
        node = node.firstChild;
        continue;
      }
      node = stack.pop();

      const type = node!.isNamed() ? node!.type : `"${node!.type}"`;
      // Simple one-level terms
      const term = !this.complex.includes(type)
        ? this.terms[type]
        : this.handleComplex(type, node!);
      // If term is found add it
      if (term) {
        terms.push({
          term: ($) => (term instanceof Function ? term($) : term),
          range: new vscode.Range(
            new vscode.Position(
              node!.startPosition.row,
              node!.startPosition.column,
            ),
            new vscode.Position(
              node!.endPosition.row,
              node!.endPosition.column,
            ),
          ),
        });
      }
      // Go right
      node = node!.nextSibling;
    }

    return terms;
  }

  private handleComplex(type: string, node: Parser.SyntaxNode) {
    // Complex terms require multi-level analyzes
    // Build complex scopes
    let scopes = [type];
    let parent = node!.parent;

    for (let i = 0; i < this.depth && parent; i++) {
      let parentType = parent.type;
      if (!parent.isNamed()) parentType = '"' + parentType + '"';
      type = parentType + ' > ' + type;
      scopes.push(type);
      parent = parent.parent;
    }

    // If there is also order complexity
    if (this.order) {
      let rindex = -1;
      let index = 0;
      let sibling = node!.previousSibling;

      while (sibling) {
        if (sibling.type === node?.type) index++;
        sibling = sibling.previousSibling;
      }

      sibling = node!.nextSibling;
      while (sibling) {
        if (sibling.type === node?.type) rindex--;
        sibling = sibling.nextSibling;
      }

      scopes = scopes.flatMap((scope) => [
        scope,
        `${scope}[${index}]`,
        `${scope}[${rindex}]`,
      ]);
    }

    let term;
    // Use most complex scope
    for (const complex of scopes) {
      if (complex in this.scopes) {
        term = this.scopes[complex];
      }
    }
    return term;
  }
}

export default Grammar;
