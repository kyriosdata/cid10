import { module, test } from 'qunit';
import { setupRenderingTest } from 'ember-qunit';
import { render } from '@ember/test-helpers';
import hbs from 'htmlbars-inline-precompile';

module('Integration | Helper | exibe-entrada-cid', function(hooks) {
  setupRenderingTest(hooks);

  test("formata entrada CID-10", async function(assert) {
    this.set('inputValue', "atenção;ok;descrição");

    await render(hbs`{{exibe-entrada-cid inputValue}}`);

    assert.equal(this.element.textContent.trim(), "atenção - descrição");
  });
});
