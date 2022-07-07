import axios from 'axios';
import store from '@/store';
// 导入NProgress, 包对应的JS和CSS
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';
import {getAuthToken, getAuthTokenType, removeAuthToken, setAuthToken, setAuthTokenFromResponse} from './auth';
import {message} from 'ant-design-vue';
import qs from 'qs';

// 异常拦截处理器
const errorHandler = (error) => {
  console.log(error);
  if (error.response) {
    const data = error.response.data;
    // 从 localstorage 获取 token
    const token = getAuthToken();
    message.error(error.response.status + ':' + data.msg);
    if (error.response.status === 401) {
      if (token) {
        store.dispatch('Logout').then(() => {
          setTimeout(() => {
            window.location.href = axios.defaults.baseURL;
          }, 1500);
        });
      }
    }
  }
  return Promise.reject(error);
};

// 在request 拦截器中, 展示进度条 NProgress.start()
// 请求在到达服务器之前，先会调用use中的这个回调函数来添加请求头信息
axios.interceptors.request.use(config => {
  // config.params = qs.stringify(config.params, {arrayFormat: 'repeat'});
  NProgress.start();
  if (config.method !== 'get'){
    store.commit('setLoading', true);
  }
  // console.log(config)
  // 为请求头对象，添加token验证的Authorization字段
  // config.headers.Authorization = getAuthTokenType() + '_' + getAuthToken();

  if (config.data instanceof FormData) {
    config.data.append('Authorization', getAuthTokenType() + '_' + getAuthToken());
  }else{
    if(config.method == 'post'){
      config.data = {
        ...config.data,
        Authorization: getAuthTokenType() + '_' + getAuthToken(),
      }
    }else if(config.method == 'get'){
      config.params = {
        Authorization: getAuthTokenType() + '_' + getAuthToken(),
        ...config.params
      }
    }else{
      config.params = {
        Authorization: getAuthTokenType() + '_' + getAuthToken(),
        ...config.params
      }
    }
  }

  // 在最后必须 return config
  return config;
});
// response 拦截器中,  隐藏进度条NProgress.done()
axios.interceptors.response.use(config => {
  NProgress.done();
  store.commit('setLoading', false);
  // console.log(config);
  if (config.data.code == 200 || config.data.code == 400 || config.data.type == 'multipart/form-data') {
    setAuthTokenFromResponse(config);
    return config;
  } else {
    const data = config.data;
    // 从 localstorage 获取 token
    const token = getAuthToken();
    if (data.code !== 200) {
      message.error(data.msg);
    }
    if (data.code === 401 || data.code === 5002 || data.code === 5003) {
      if (token) {
        removeAuthToken();
        setTimeout(() => {
          window.location.href = store.getters.userClientDomain;
        }, 1500);
      }
    }
    // return Promise.reject(config);
  }
}, errorHandler);


const $http = axios;
export default $http;
