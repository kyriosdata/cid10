import { helper } from '@ember/component/helper';
import { htmlSafe } from '@ember/string';

export function exibeEntradaCid(entrada) {
  const campos = entrada.toString().split(";");
  return htmlSafe(`<tr><td>${campos[0]}</td><td>${campos[2]}</td></tr>`);
}

export default helper(exibeEntradaCid);
