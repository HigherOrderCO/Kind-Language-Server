import * as vscode from 'vscode';

export const types: Record<string, KindTokenType> = {
  'type': {type: 'type'},
  'scope': {type: 'scope'},
  'function': {type: 'function'},
  'variable': {type: 'variable'},
  'parameter': {type: 'parameter'},
  'number': {type: 'number'},
  'comment': {type: 'comment'},
  'constant': {type: 'variable', modifiers: ['readonly', 'defaultLibrary']},
  'directive': {type: 'macro'},
  'operator': {type: 'operator'},
  'modifier': {type: 'operator', modifiers: ['modifier']},
  'punctuation': {type: 'punctuation'},
  'keyword': {type: 'keyword'},
  'class': {type: 'class'},
  'interface': {type: 'interface'},
  'method': {type: 'method'},
  'enum': {type: 'enum'},
  'enum-member': {type: 'enumMember'},
  'type-parameter': {type: 'typeParameter'},
  'property': {type: 'property'},
};

export const modifiers = [
  'declaration',
  'readonly',
  'defaultLibrary',
  'documentation',
];

type KindTokenType = {
  type: string;
  modifiers?: string[];
};

const legendTokenTypes = Object.values(types).map((a) => a.type);

const legend = new vscode.SemanticTokensLegend(legendTokenTypes, modifiers);

export default legend;
