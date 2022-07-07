package com.haoyu.framework.modules.video.scheduler;


import cn.hutool.core.map.MapUtil;
import com.haoyu.framework.modules.video.service.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;


/*
  视频转码定时器
 */
@Component
public class CheckVideoScheduler {

    @Autowired
    private FileInfoService fileInfoService;

    //每小时执行一次
    @Scheduled(cron = "0 0 0/1 * * ?")
    public void checkVideoDecoder() {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("fileName",".mp4");
        map.put("version","1");
        fileInfoService.checkVideByMap(map);
    }

}
