import DS from 'ember-data';

export default DS.Model.extend({
  codigo: DS.attr(),
  sexo: DS.attr(),
  descricao: DS.attr()
});
