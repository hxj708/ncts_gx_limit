import Vue from 'vue';
Vue.filter('getCagetroyName',getCagetroyName);
function getCagetroyName(cagetroyType) {
  switch (cagetroyType) {
    case 'stage':
      return '学段';
    case 'subject':
      return '学科';
  }
  return '';
}
export default getCagetroyName;
