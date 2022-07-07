import {AuthData, AuthTokenKey, AuthTokenType, AuthRoleCode, AuthResourceCode, AuthLoginUser, AuthResourceList} from "@/utils/auth";
import $router from '@/router';

const mutations = {
  setLoading(state, loading) {
    state.loading = loading;
  },
  setAuthData(state, authData){
    state.authData = authData;
    sessionStorage.setItem(AuthData, authData);
  },
  setAuthToken(state, authToken) {
    state.authToken = authToken;
    sessionStorage.setItem(AuthTokenKey, authToken);
  },
  setAuthTokenType(state, authTokenType) {
    state.authTokenType = authTokenType;
    sessionStorage.setItem(AuthTokenType, authTokenType);
  },
  setAuthRoleCode(state, authRoleCode) {
    state.authRoleCode = authRoleCode;
    sessionStorage.setItem(AuthRoleCode, authRoleCode);
  },
  setAuthResourceCode(state, authResourceCode) {
    state.authResourceCode = authResourceCode;
    sessionStorage.setItem(AuthResourceCode, authResourceCode);
  },
  setAuthResourceList(state, authResourceList) {
    state.authResourceList = authResourceList;
    sessionStorage.setItem(AuthResourceList, authResourceList);
  },
  setAuthLoginUser(state, authLoginUser) {
    state.authLoginUser = authLoginUser;
    sessionStorage.setItem(AuthLoginUser, authLoginUser);
  },
  delAuthToken(state) {
    state.authToken = '';
    sessionStorage.removeItem(AuthTokenKey);
    state.authTokenType = '';
    sessionStorage.removeItem(AuthTokenType);
    state.authRoleCode = '';
    sessionStorage.removeItem(AuthRoleCode);
    state.authResourceCode = '';
    sessionStorage.removeItem(AuthResourceCode);
    state.authResourceList = '';
    sessionStorage.removeItem(AuthResourceList);
    state.authLoginUser = '';
    sessionStorage.removeItem(AuthLoginUser);
  },
  setDictMap(state,map){
    state.dictMap[map.type] = map.value;
  },

  addTab(state, tab){
    state.actTab = tab.name;
    let arr = [];
    if(state.keepAlive){
      arr = state.keepAlive.split(',');
    }
    if (!arr.find(x => x === tab.name)) {
      arr.push(tab.name);
      state.keepAlive = arr + '';

      let tabs = state.tabs;
      tabs.push(tab);
      state.tabs = tabs;
    }
  },
  deleteTab(state, name){
    let arr = [];
    if(state.keepAlive){
      arr = state.keepAlive.split(',');
    }
    const index = arr.findIndex(x => x === name);
    if (index >= 0) {
      arr.splice(index, 1);
      state.keepAlive = arr + '';

      if(state.actTab == name){
        state.actTab = arr[arr.length - 1];
        const tabs = state.tabs;
        $router.push(tabs[tabs.length - 1].path)
      }
    }
  }

};
export default mutations;
