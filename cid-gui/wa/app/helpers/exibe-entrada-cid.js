import { helper } from '@ember/component/helper';

export function exibeEntradaCid(entrada) {
  const campos = entrada.toString().split(";");
  return campos[0] + " - " + campos[2];
}

export default helper(exibeEntradaCid);
