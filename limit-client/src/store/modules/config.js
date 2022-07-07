const state = {
  app: 'demo',
  //开发环境
  development:{
    appStyle: 'jiangmen',
    appName: '教师选学中心',
    serverDomain: 'http://127.0.0.1:8081/',
    // serverDomain: 'http://192.168.0.10:9100/',
    fileDomain: 'http://127.0.0.1:8780/',
    bmsClientDomain: 'http://127.0.0.1:82/bms/',
    userClientDomain: 'http://127.0.0.1:81/',
  },

  // 江门正式环境
  // demo:{
  //   appStyle: 'jiangmen',
  //   appName: '教师选学中心',
  //   serverDomain: 'http://tilc.gdjspx.net/server/',
  //   fileDomain: 'http://tilc.gdjspx.net/file/',
  //   bmsClientDomain: 'http://tilc.gdjspx.net/bms/',
  //   userClientDomain: 'http://tilc.gdjspx.net/',
  // },

  //docker环境
   demo:{
     appStyle: '',
     appName: '',
     serverDomain: 'http://192.168.0.10/server/',
     fileDomain: 'http://192.168.0.10/file/',
     bmsClientDomain: 'http://192.168.0.10/bms/',
     userClientDomain: 'http://192.168.0.10/bms/',
   },
};

const getters = {
  app: state => state.app,
  appStyle: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.appStyle;
    }else{
      const app = state.app;
      return state[app].appStyle;
    }
  },
  basicName: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.basicName;
    }else{
      const app = state.app;
      return state[app].basicName;
    }
  },
  appName: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.appName;
    }else{
      const app = state.app;
      return state[app].appName;
    }
  },
  serverDomain: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.serverDomain;
    }else{
      const app = state.app;
      return state[app].serverDomain;
    }
  },
  userClientDomain: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.userClientDomain;
    }else{
      const app = state.app;
      return state[app].userClientDomain;
    }
  },
  fileDomain: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.fileDomain;
    }else{
      const app = state.app;
      return state[app].fileDomain;
    }
  },
  bmsClientDomain: state => {
    if (process.env.NODE_ENV === 'development'){
      return state.development.bmsClientDomain;
    }else{
      const app = state.app;
      return state[app].bmsClientDomain;
    }
  },
};

const mutations = {
};

const actions = {
};

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
};
