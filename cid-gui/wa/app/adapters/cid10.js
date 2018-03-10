import ApplicationAdapter from './application';

export default ApplicationAdapter.extend({
  findAll: function() {
    return this.ajax("http://localhost:8080/busca/a923/0", "GET");
  }

});
