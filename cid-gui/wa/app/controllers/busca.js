import Controller from '@ember/controller';

export default Controller.extend({
  actions: {
    filtraPorParte(param) {
      if (param != "") {
        return this.get("store").query("entrada", { city: param })
      } else {
        return this.get("store").findAll("cid-10");
      }
    }
  }
});
