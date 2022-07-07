import {asyncRoutes, defaultRoutes} from '@/router';
import router from '@/router';

const state = {
  routes: defaultRoutes,
  addRoutes: []
};
const mutations = {
  setRoutes(state, routes) {
    state.routes = defaultRoutes.concat(routes);
    state.addRoutes = routes;
    router.addRoutes(routes);
  }
};

const actions = {
  setRoutes({commit}, routes) {
    commit('setRoutes', routes);
  }
};

export default {
  namespaced: true,
  state,
  mutations,
  actions
};
