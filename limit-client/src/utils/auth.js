import store from '@/store';
import {message} from 'ant-design-vue';
import CryptoJS from 'crypto-js';
import getters from '@/store/getters';
import $resource from '@/api/auth/resource';
import $loginUser from '@/api/auth/loginUser';
import router from '@/router';
import router_menu from '@/router_menu';
import LayOut from '@/pages/common/layout/index.vue';

export const AuthData = 'Auth-Data';
export const AuthTokenKey = 'Auth-Token';
export const AuthTokenType = 'Auth-Token-Type';
export const AuthTokenFresh = 'authorization-refresh';
export const AuthResourceCode = 'Auth-Resource-Code';
export const AuthResourceList = 'Auth-Resource-List';
export const AuthRoleCode = 'Auth-Role-Code';
export const AuthLoginUser = 'Auth-Login-User';

export function getAuthData() {
  return store.state.authData;
}

export function getAuthToken() {
  return getters.authToken(store.state);
  // return sessionStorage.getItem(AuthTokenKey);
}

export function getAuthTokenType() {
  return getters.authTokenType(store.state);
  // return sessionStorage.getItem(AuthTokenType);
}

export function getAuthRoleCode() {
  return getters.authRoleCode(store.state);
}

export function getAuthResourceCode() {
  return getters.authResourceCode(store.state);
}

export function getAuthResourceList() {
  return getters.authResourceList(store.state);
}

export function getAuthLoginUser() {
  return getters.authLoginUser(store.state);
}

export function setAuthToken(tokenInfo) {
  store.commit('setAuthToken', tokenInfo.token);
  store.commit('setAuthTokenType', tokenInfo.tokenType);
  store.commit('setAuthRoleCode', tokenInfo.roleCodeList);
  store.commit('setAuthResourceCode', tokenInfo.resourceCodeList);
  store.commit('setAuthLoginUser', tokenInfo.loginUser);
  store.commit('setAuthData', JSON.stringify(tokenInfo));
  return true;
}

export async function setAuthResourceList() {
  store.commit('router/setRoutes', router_menu);

  // let resourceList = await $loginUser.getCurrentResourceList();
  // store.commit('setAuthResourceList', resourceList);
  //
  // const list = store.getters.authResourceList;
  // forEachAuth(list);
  // forEachRedirect(list);
  // store.commit('router/setRoutes', list);

  function forEachAuth(list){
    list.forEach(item => {
      if(item.code){
        var title = item.name;
        var name = item.code;
        item.meta = {title : title, type : item.type};
        item.name = name.replace('/', '-');
        item.path = '/' + item.code;
        if(item.url){
          item.component = resolve => (require(['@/pages' + item.url], resolve))
        }else{
          item.component = LayOut
        }
        if (item.children && item.children.length > 0) {
          forEachAuth(item.children);
        }
      }
    });
  }

  function forEachRedirect(list){
    list.forEach(item => {
      if (item.children && item.children.length > 0) {
        try{
          item.children.forEach(item1 => {
            if(item1.type == 'menu'){
              item.redirect = item1.path;
              throw 'break';
            }
          });
        }catch(e){
        }
        forEachRedirect(item.children);
      }
    });
  }
  function compare (value1, value2) {
    if (value1.sortNo < value2.sortNo) {
      return -1;
    } else if (value1.sortNo > value2.sortNo) {
      return 1;
    } else {
      return 0;
    }
  }
}


export function setAuthTokenFromResponse(response) {
  var authFresh = response.data[AuthTokenFresh];
  // var authFresh = response.headers[AuthTokenFresh];
  // console.log(authFresh);
  if (authFresh != undefined && authFresh != '') {
    var tokenInfo = JSON.parse(authFresh);
    setAuthToken(tokenInfo);
  }
}

export function removeAuthToken() {
  return store.commit('delAuthToken');
}

/**
 * ??????Route??????????????????????????????????????????????????????
 * 1.meta????????????
 *  1.meta.title?????????,?????????
 *  2.meta.title????????????
 *    1.meta.code???????????????????????????
 *    2.meto.code??????????????????????????????????????????resourceCodeList???????????????????????????????????????????????????
 * @param route
 * @returns {boolean}
 */
export function checkRouteMenu(route, authResourceCode) {
  if (route.meta && route.meta.title) {
    if (route.meta.code) {
      if (authResourceCode.indexOf(route.meta.code) > -1) {
        return true;
      } else {
        return false;
      }
    }
    return true;
  }
  return false;
}

/**
 * ??????Route????????????????????????
 * 1.meta????????????????????????
 * 2.meta????????????
 *  1.meta.code????????????????????????
 *  2.meta.code????????????????????????resourceCodeList????????????????????????
 *    1.?????????????????????
 *    2.????????????????????????
 * @param route
 * @returns {boolean}
 */
export function checkRouteAccess(route, authResourceCode) {
  if (route.meta && route.meta.code) {
    if (authResourceCode.indexOf(route.meta.code) > -1) {
      return true;
    } else {
      return false;
    }
  }
  return true;
}

/**
 * ?????????????????????????????????
 * @param role
 * @returns {boolean}
 */
export function hasRole(role) {
  const roles = store.state.authRoleCode;
  return roles.includes(role);
}

/**
 * ???????????????????????????????????????
 * @param resource
 * @returns {boolean}
 */
export function hasResourceCode(resourceCode) {
  const resourceCodes = store.state.authResourceCode;
  return resourceCodes.includes(resourceCode);
}

export function getAreaParamByRole() {
  const roles = store.state.authRoleCode;
  if (roles.includes('OrganAdminProvince')){
    return 'province';
  }else if (roles.includes('OrganAdminCity')){
    return 'city';
  }else if (roles.includes('OrganAdminCounties')){
    return 'counties';
  }else if (roles.includes('SchoolAdmin')){
    return 'deptId';
  }
  return '';
}
