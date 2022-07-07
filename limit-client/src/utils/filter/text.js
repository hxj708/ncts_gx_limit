import Vue from 'vue';

Vue.filter('ellipsis', function(value,num) {
  if (!value) return ''
  if (value.length > num) {
    return value.slice(0,num) + '...'
  }
  return value
});

Vue.filter('paperworkNo', function(value,headNum,tailNum) {
  if (!value) return ''

  headNum = Number.parseInt(headNum)
  tailNum = Number.parseInt(tailNum)
  if (value.length > tailNum + headNum) {
    return value.slice(0,headNum) + '********' + value.substring(value.length-tailNum)
  }
  return value
});




