import * as path from 'path';
import Grammar from './grammar';
import match from './match';

const kind = new Grammar('Kind', {
  path: path.join(__dirname, '..', 'tree-sitter-kind'),
  terms: {
    'f60_literal': 'number',
    'u60_literal': 'number',
    'u120_literal': 'number',
    'n_literal': 'number',
    'string': 'string',
    'char': 'string',
    // operators
    '"+="': 'operator',
    '"-="': 'operator',
    '"*="': 'operator',
    '"/="': 'operator',
    '"%="': 'operator',
    '"<<="': 'operator',
    '">>="': 'operator',
    '">>>="': 'operator',
    '"&="': 'operator',
    '"^="': 'operator',
    '"|="': 'operator',
    '"!"': 'operator',
    '"+"': 'operator',
    '"-"': 'operator',
    '"*"': 'operator',
    '"/"': 'operator',
    '"%"': 'operator',
    '"=="': 'operator',
    '"==="': 'operator',
    '"!="': 'operator',
    '"!=="': 'operator',
    '">="': 'operator',
    '"<="': 'operator',
    '"=>"': 'operator',
    '">"': 'operator',
    '"<"': 'operator',
    '"?"': 'operator',
    '"&&"': 'operator',
    '"||"': 'operator',
    '"&"': 'operator',
    '"~"': 'operator',
    '"^"': 'operator',
    '">>"': 'operator',
    '">>>"': 'operator',
    '"<<"': 'operator',
    '"|"': 'operator',
    '"++"': 'operator',
    '"--"': 'operator',
    '".."': 'operator',
    '"@"': 'operator',
    '"::"': 'operator',
    '"$"': 'operator',
    // comments
    'line_comment': 'comment',
    'doc_string': 'documentation',
    'hash_bang_line': 'comment',
    // keywords
    '"record"': 'keyword',
    '"type"': 'keyword',
    '"if"': 'keyword',
    '"let"': 'keyword',
    '"else"': 'keyword',
    '"match"': 'keyword',
    '"with"': 'keyword',
    '"into"': 'keyword',
    '"in"': 'keyword',
    '"do"': 'keyword',
    '"ask"': 'keyword',
    '"open"': 'keyword',
    '"specialize"': 'keyword',
    '"return"': 'keyword',
    // punctuation
    '"("': 'punctuation',
    '")"': 'punctuation',
    '"{"': 'punctuation',
    '"}"': 'punctuation',
    '";"': 'punctuation',
    '"["': 'punctuation',
    '"]"': 'punctuation',
    '"."': 'punctuation',
    '","': 'punctuation',
  },
  complex: [
    'identifier',
    'upper_id',
    'lower_id',
    'constructor_identifier',
    'field_signature',
    'member_signature',
    'rename_pattern',
    'local_name',
    'implicit_parameter',
    'explicit_parameter',
    'lam_parameter',
    'match_pattern',
    'dot_access',
    'constructor_match_pattern',
  ],
  scopes: {
    'constructor_identifier > lower_id': 'method',

    'field_signature > identifier': 'property',
    'field_signature > constructor_identifier': 'property',

    'implicit_parameter > identifier': 'type-parameter',
    'implicit_parameter > constructor_identifier': 'type-parameter',

    'lam_parameter > identifier': 'parameter',
    'lam_parameter > constructor_identifier': 'parameter',

    'lam_named_type_parameter > identifier': 'parameter',
    'lam_named_type_parameter > constructor_identifier': 'parameter',

    'explicit_parameter > identifier': 'parameter',
    'explicit_parameter > constructor_identifier': 'parameter',

    'member_signature > identifier': 'enum-member',
    'member_signature > constructor_identifier': 'enum-member',

    'constructor_match_pattern > constructor_identifier > dot_access > lower_id':
      'enum-member',

    'constructor_match_pattern > identifier > lower_id': 'variable',

    'rename_pattern > identifier': 'property',

    'constructor_pattern > constructor_identifier > dot_access > lower_id':
      'enum-member',

    'pattern > identifier': 'parameter',
    'pattern > constructor_identifier': 'parameter',

    'identifier': 'variable',
    'upper_id': ($) =>
      match($, {
        Pair: 'builtin-class',
        Type: 'builtin-class',
        Sigma: 'builtin-class',
        F60: 'builtin-class',
        U60: 'builtin-class',
        U120: 'builtin-class',
        Nat: 'builtin-class',
        String: 'builtin-class',
        Unit: 'builtin-class',
        Absurd: 'builtin-class',
        IO: 'builtin-class',
        Maybe: 'builtin-class',
        Monad: 'builtin-class',
        Map: 'builtin-class',
        Show: 'builtin-class',
        _: 'function',
      }),
  },
});

export default kind;
