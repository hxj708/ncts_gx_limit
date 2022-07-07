import {AuthData, AuthTokenKey, AuthTokenType, AuthResourceCode, AuthRoleCode, AuthLoginUser, AuthResourceList} from "@/utils/auth";

const state = {
  loading:false,
  authData:sessionStorage.getItem(AuthData) || '',
  authToken: sessionStorage.getItem(AuthTokenKey) || '',
  authTokenType: sessionStorage.getItem(AuthTokenType) || '',
  authRoleCode: sessionStorage.getItem(AuthRoleCode) || '',
  authResourceCode: sessionStorage.getItem(AuthResourceCode) || '',
  authResourceList: sessionStorage.getItem(AuthResourceList) || '',
  authLoginUser: sessionStorage.getItem(AuthLoginUser) || '',
  dictMap:{},

  keepAlive:'',
  actTab:'',
  tabs:[],
};

export default state;
