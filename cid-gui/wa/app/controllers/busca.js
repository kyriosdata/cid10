import Controller from '@ember/controller';

export default Controller.extend({
  actions: {
    filtraPorParte(param) {
      if (param != "") {
        // return this.get("store").query("entrada", { city: param })
        console.log("Parametro: " + param);
        const sentenca = "código " + param + ";-; descricao " + param;
        return Promise.resolve([sentenca]);
      } else {
        return this.get("store").query("cid10", { texto: "a923" });
      }
    }
  }
});
