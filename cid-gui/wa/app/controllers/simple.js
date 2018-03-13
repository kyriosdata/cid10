import Controller from '@ember/controller';
import { A as emberA } from '@ember/array';
import { computed } from '@ember/object';

export default Controller.extend({
  rows : computed(function() {
    let rows = emberA();
    rows.pushObject({
      P: "x",
      U: "y"
    });

    rows.pushObject({
      P: "z",
      U: "w"
    });

    return rows;
  }),

  columns : [{columnName:"A", valuePath: "P"},{columnName:"B", valuePath: "U"}],

  actions : {
    onSelect(selectedRows) {
      this.set('selectedRows', selectedRows);
    }
  }
});
