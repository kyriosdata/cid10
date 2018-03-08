import { module, test } from 'qunit';
import { visit, click, currentURL } from '@ember/test-helpers';
import { setupApplicationTest } from 'ember-qunit';

module('Acceptance | acesso cid10', function(hooks) {
  setupApplicationTest(hooks);

  test("deve exibir busca como página principal", async function(assert) {
    await visit("/");
    assert.equal(currentURL(), "/busca", "deve redirecionar automaticamente");
  });

  test("deve orientar a fornecer entrada para filtro", async function(assert) {

  });

  test("deve informar que nenhuma entrada satisfaz busca",
    async function(assert) {

  });

  test("deve criar botão + para lista de 50 entradas", async function(assert) {

  });

  test("deve filtar conforme informação", async function(assert) {
  });

  test("deve possuir link para about", async function(assert) {
    await visit("/busca");
    await click(".about");
    assert.equal(currentURL(), "/about", "deve ir para informações");
  });
});
