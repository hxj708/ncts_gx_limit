import Vue from 'vue';
import store from '@/store';

Vue.filter('getActivityStateName', function(stateCode) {
  switch (stateCode) {
    case 'not_attempt':
      return '未完成';
    case 'in_progress':
      return '进行中';
    case 'complete':
      return '已完成';
    case 'nopass':
      return '未通过';
    default :
      return '未完成';
  }
});

Vue.filter('getAssignmentStateName', function(stateCode) {
  let stateName=store.state.assignmentState[stateCode];
  if (stateName===undefined||stateName==null){
    return '未提交';
  }
  return stateName;
});

Vue.filter('userIsLock', function(state) {
  switch (state) {
    case 'N':
      return '启用';
    case 'Y':
      return '停用';
    default :
      return '启用';
  }
});

Vue.filter('mhState', function(state) {
  switch (state) {
    case 'Y':
      return '公开';
    case 'N':
      return '不公开';
    default :
      return '不公开';
  }
});

Vue.filter('topicCertType', function(state) {
  switch (state) {
    case 'report':
      return '课题立项证书';
    case 'analyze':
      return '课题结题证书';
    default :
      return '';
  }
});

Vue.filter('getArchivesStateName', function(stateCode) {
  switch (stateCode) {
    case 'pass':
      return '通过';
    case 'auditing':
      return '审核中';
    case 'nopass':
      return '未通过';
    default:
      return '未通过';
  }
});

