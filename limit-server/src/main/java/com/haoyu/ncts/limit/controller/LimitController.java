package com.haoyu.ncts.limit.controller;

import cn.hutool.http.HttpUtil;
import com.haoyu.ncts.limit.base.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/limit")
public class LimitController {

  @Autowired
  private RedisTemplate redisTemplate;

  @GetMapping("tryLogin")
  public R tryLogin(String type){
    String key = "wait_num";
    long num = 0;
    if ("inc".equals(type)){
      num = redisTemplate.opsForValue().increment(key);
    }else{
      num = (Long) redisTemplate.opsForValue().get(key);
    }

    if (num <= 10){
      //发送心跳包，3秒内返回则表示成功，否则失败
      try {
        String result = HttpUtil.get("http://127.0.0.1:8380/nts/tryLogin", 3000);
        redisTemplate.opsForValue().decrement(key);
      }catch (Exception e){
        e.printStackTrace();
      }

    }

    return R.success().setData(num);
  }

}
