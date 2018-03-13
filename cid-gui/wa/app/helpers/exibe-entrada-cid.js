import { helper } from '@ember/component/helper';

export function exibeEntradaCid(entrada) {
  const campos = entrada.toString().split(";");
  return Ember.String.htmlSafe(`<tr><td>${campos[0]}</td><td>${campos[2]}</td></tr>`);
}

export default helper(exibeEntradaCid);
