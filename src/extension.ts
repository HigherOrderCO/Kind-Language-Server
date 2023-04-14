import * as vscode from 'vscode';
import {init as setupTreeSitter} from 'web-tree-sitter';
import kind from './kind';
import legend from './legend';
import provider from './provider';

export async function activate(_context: vscode.ExtensionContext) {
  await setupTreeSitter();
  await kind.setup();

  vscode.languages.registerDocumentSemanticTokensProvider(
    {language: 'kind', scheme: 'file'},
    provider,
    legend,
  );
}

export async function deactivate() {}
