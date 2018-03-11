import Controller from '@ember/controller';
import $ from "jquery";

export default Controller.extend({
  actions: {
    filtraPorParte(param) {
      if (param != "") {
        // return this.get("store").query("entrada", { city: param })
        const tratado = param.toString().trim();
        const url = "http://localhost:8080/busca/" + param + "/0";
        console.log(url);
        return $.getJSON(url);
      } else {
        return $.getJSON("http://localhost:8080/busca/a923/0");
      }
    }
  }
});
