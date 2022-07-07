import Vue from 'vue';
import Vuex from 'vuex';
import state from './states';
import getters from './getters';
import mutations from './mutations';
import actions from './actions';
import router from './modules/router';
import config from './modules/config';

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    router, config
  },
  state,
  getters,
  mutations,
  actions
});

export default store;
