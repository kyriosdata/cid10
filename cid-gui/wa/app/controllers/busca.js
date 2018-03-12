import Controller from '@ember/controller';
import $ from "jquery";

const HOST = "https://cidapi2.azurewebsites.net/busca/";

function montaURL(argumentos, ordem) {
  return HOST + argumentos + "/" + ordem;
}

export default Controller.extend({
  actions: {
    filtraPorParte(param) {
      if (param != "") {
        const tratado = param.toString().trim();
        const url = montaURL(param, 0);
        console.log(url);
        return $.getJSON(url);
      } else {
        return $.getJSON(montaURL("a923", 0));
      }
    }
  }
});
