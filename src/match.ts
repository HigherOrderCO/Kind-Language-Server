function match<S>(value: S, cases: {[key: string | '_']: S}): any {
  const key = Object.keys(cases).find((key) => value === key);
  if (key) {
    return cases[key];
  }
  if (cases['_']) {
    return cases['_'];
  }
  return undefined;
}

export default match;
