package com.haoyu;


import com.haoyu.framework.modules.video.service.FileInfoService;
import com.haoyu.framework.modules.video.utils.ConverVideoUtils;
//import com.haoyu.framework.modules.video.utils.MediaUtil;
//import it.sauronsoftware.jave.*;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;



@Slf4j
public class myTest {


    @Test
    public void test() throws Exception {
//
//        Encoder encoder = new Encoder();
//        File file = new File("C:\\Users\\Administrator\\Desktop\\test\\111.mp4");
//        MultimediaInfo encoderInfo = encoder.getInfo(file);
//        //视频播放时长
//        long duration = encoderInfo.getDuration();
//        System.out.println("视频播放时长:"+duration / 1000+"秒");
//        //多媒体文件格式名称
//        String encoderInfoFormat = encoderInfo.getFormat();
//        log.info("多媒体文件格式名称:{}", encoderInfoFormat);
//        //音频 返回一组特定于音频的信息。如果为空，则多媒体文件中没有音频*流。
//        AudioInfo audio = encoderInfo.getAudio();
//        if (audio != null) {
//            //音频流解码器名称
//            String audioDecoder = audio.getDecoder();
//            log.info("音频流解码器名称:{}", audioDecoder);
//        }
//        //视频
//        VideoInfo videoInfo = encoderInfo.getVideo();
//        if (videoInfo == null) {
//            throw new RuntimeException("多媒体文件中没有视频流");
//        }
//        //视频流解码器名称
//        String videoInfoDecoder = videoInfo.getDecoder();
//        log.info("视频流解码器名称:{}", videoInfoDecoder);
//        //返回视频大小。如果为空，则此信息不可用
//        VideoSize videoInfoSize = videoInfo.getSize();
//        if (videoInfoSize == null) {
//            throw new RuntimeException("视频分辨率获取失败");
//        }
//        //视频高度
//        int height = videoInfoSize.getHeight();
//        //视频宽度
//        int width = videoInfoSize.getWidth();
//        log.info("视频高度:{},视频宽度:{}",height,width);
    }

    @Test
    public void test2(){
        ConverVideoUtils converVideoUtils = new ConverVideoUtils();
        converVideoUtils.beginConver("C:\\Users\\Administrator\\Desktop\\test\\111.mp4");
    }

    @Test
    public void test3(){
        try {
            File fileInputStream = new File("C:\\Users\\Administrator\\Desktop\\test\\111.mp4");
            File fileOutputStream = new File("C:\\Users\\Administrator\\Desktop\\test\\666.mp4");
//            new MediaUtil().convertVideo(fileInputStream,fileOutputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
