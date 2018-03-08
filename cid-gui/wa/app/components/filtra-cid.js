import Component from '@ember/component';

export default Component.extend({
  classNames : [ "filtra-cid" ],
  value: '',

  init() {
    this._super(...arguments);
    this.get('filter')('').then((results) => this.set('results', results));
  },

  actions : {
    handleEntradaFiltro() {
      let filterInputValue = this.get("value");
      let filterAction = this.get("filter");
      filterAction(filterInputValue).then((filterResults) => this.set("results", filterResults));
    }
  }
});
