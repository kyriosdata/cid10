import Route from '@ember/routing/route';

export default Route.extend({
  model() {
    return ["códigoA;-;descriçãoA", "códigoB;-;descriçãoB"];
  }
});
