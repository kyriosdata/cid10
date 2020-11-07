import EmberObject from '@ember/object';
import TableCommonMixin from 'wa/mixins/table-common';
import { module, test } from 'qunit';

module('Unit | Mixin | table-common', function() {
  // Replace this with your real tests.
  test('it works', function (assert) {
    let TableCommonObject = EmberObject.extend(TableCommonMixin);
    let subject = TableCommonObject.create();
    assert.ok(subject);
  });
});
