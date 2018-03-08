import { helper } from '@ember/component/helper';

export function exibeEntradaCid(entrada) {
  const campos = entrada.split(";");
  return `Código: ${campos[0]} Descrição: ${campos[2]}`;
}

export default helper(exibeEntradaCid);
