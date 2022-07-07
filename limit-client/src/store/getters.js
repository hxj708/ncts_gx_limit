import CryptoJS from 'crypto-js';

const getters = {
  app: (state,getters,rootState,rootGetters) => rootGetters['config/app'],
  appStyle: (state,getters,rootState,rootGetters) => rootGetters['config/appStyle'],
  provinceCode: (state,getters,rootState,rootGetters) => rootGetters['config/provinceCode'],
  cityCode: (state,getters,rootState,rootGetters) => rootGetters['config/cityCode'],
  countiesCode: (state,getters,rootState,rootGetters) => rootGetters['config/countiesCode'],
  basicName: (state,getters,rootState,rootGetters) => rootGetters['config/basicName'],
  appName: (state,getters,rootState,rootGetters) => rootGetters['config/appName'],
  serverDomain: (state,getters,rootState,rootGetters) => rootGetters['config/serverDomain'],
  fileDomain: (state,getters,rootState,rootGetters) => rootGetters['config/fileDomain'],
  bmsClientDomain: (state,getters,rootState,rootGetters) => rootGetters['config/bmsClientDomain'],
  userClientDomain: (state,getters,rootState,rootGetters) => rootGetters['config/userClientDomain'],
  userServerSignDomain: (state,getters,rootState,rootGetters) => rootGetters['config/userServerSignDomain'],

  authData: state => state.authData,
  authToken: state => state.authToken,
  authTokenType: state => state.authTokenType,
  authRoleCode: state => state.authRoleCode,
  authResourceCode: state => state.authResourceCode,
  authResourceList: state => {
    if(state.authResourceList){
      try{
        let resourceListStr = CryptoJS.enc.Base64.parse(state.authResourceList);
        let resourceList = JSON.parse(resourceListStr.toString(CryptoJS.enc.Utf8));
        return resourceList;
      }
      catch (e){
        console.error(e);
        return null;
      }
    }
    return null;
  },
  authLoginUser: state => {
    if(state.authLoginUser){
      try{
        let loginUserStr = CryptoJS.enc.Base64.parse(state.authLoginUser);
        let loginUser = JSON.parse(loginUserStr.toString(CryptoJS.enc.Utf8));
        return loginUser;
      }
      catch (e){
        console.error(e);
        return null;
      }
    }
    return null;
  },
  routes: state => state.router.routes,
  dictMap: state => state.dictMap,
};
export default getters;
