import Vue from 'vue';

Vue.filter('numPercent', function(num1,num2,fixCount) {
    if (num2 && num1){
      var num = num1 / num2 * 100;
      num =  parseFloat(num).toFixed(fixCount);
      return num;
    }else {
      return 0
    }
});

Vue.filter('oneNumPercent', function(num,fixCount) {
  var percent = num * 100 ;
  percent = parseFloat(percent).toFixed(fixCount);
  return percent;
});

Vue.filter('numToUpperCase', function(originVal) {
  if (originVal === null) return '';
  return String.fromCharCode(64 + parseInt(originVal)).toUpperCase()
});

Vue.filter('numIsNull', function(number) {
  if (number===null||number===undefined||number===''||isNaN(number)){
    return 0;
  }
  return number;
});

Vue.filter('multiply', function(...number) {
  let res = 1;
  number.forEach(item=>{
    res*=parseInt(item);
  })
  return res;
});
