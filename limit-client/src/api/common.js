import { message } from 'ant-design-vue';

export function dealRes(res){
  console.log(res)
  if (res.code !== 200) {
    return message.error('获取数据失败！');
  }
  return res.data;
};

export function test() {
}
