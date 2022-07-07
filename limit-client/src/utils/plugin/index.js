import $http from '@/utils/request';
import { message } from 'ant-design-vue';
import $dict from '@/api/dict';

export default {
  install (Vue, options) {
    Vue.prototype.$downloadFile = async function (file) {
      const {data: res} = await $http.post('/api/v1/file/fileInfo/download/byUrl', {url: file.url}, {responseType: 'blob'});
      let blob = new Blob([res]);
      let link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = file.fileName;
      link.click();
      link.remove();
    };
    Vue.prototype.$initDict = async function (...dictTypeCode) {
      dictTypeCode.forEach(item=>{
        $dict.getMap(item);
      })
    };
  }
}
