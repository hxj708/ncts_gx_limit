package com.haoyu.framework.modules.video.service.impl;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.modules.video.entity.FileInfo;
import com.haoyu.framework.modules.video.mapper.FileInfoMapper;
import com.haoyu.framework.modules.video.service.FileInfoService;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;

//import com.haoyu.framework.modules.video.utils.MediaUtil;
//import it.sauronsoftware.jave.Encoder;
//import it.sauronsoftware.jave.EncoderException;
//import it.sauronsoftware.jave.MultimediaInfo;
//import it.sauronsoftware.jave.VideoInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;


/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Service("convertVideoFileService")
@Slf4j
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfoMapper,FileInfo> implements FileInfoService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Value("${file.remote-dir}")
    private String fileRemoteDir;

    private static final String targetExtension = ".mp4";

    @Override
    public R<FileInfo> update(FileInfo entity) {
        return super.update(entity);
    }

    //查询并检查文件
    public void checkVideByMap(Map<String,Object> map){
//        List<FileInfo> fileInfos = fileInfoMapper.selectByMap(map);
//
//        if (CollUtil.isNotEmpty(fileInfos)){
//            //先把查询出来的version 改成2，防止反复转码
//            for (FileInfo fileInfo : fileInfos){
//                fileInfoMapper.updateFileChangeDecoderByFileId(fileInfo.getId());
//            }
//
//            for (FileInfo fileInfo : fileInfos){
//                //获取数据信息
//                setMultimediaInfo(fileInfo);
//
//                MultimediaInfo multimediaInfo = fileInfo.getMultimediaInfo();
//                if (multimediaInfo != null){
//                    //获取视频信息
//                    VideoInfo videoInfo = multimediaInfo.getVideo();
//
//                    if (videoInfo != null){
//                        //获取视频编码器
//                        String videoInfoDecoder = videoInfo.getDecoder();
//
//                        if (StrUtil.isNotBlank(videoInfoDecoder)){
//                            if (!videoInfoDecoder.equals("h264")){
//                                fileInfo.setMultimediaInfo(multimediaInfo);
//                                //视频编码不正确，需要转码
//                                changeVideoDecoder(fileInfo);
//                            }else {
//                                fileInfo.setRemark("该文件不需要转码");
//                            }
//                        }else {
//                            fileInfo.setRemark("视频编码信息无法识别或为空，请重新转码上传");
//                        }
//
//                    }else {
//                        fileInfo.setRemark("文件没有视频流信息（没有画面），请重新上传");
//                    }
//
//                }else {
//                    fileInfo.setRemark("文件找不到或已经损坏，请重新上传。");
//                }
//
//                //更新
//                update(fileInfo);
//                fileInfoMapper.deleteFileChangeDecoderByFileId(fileInfo.getId());
//            }
//        }else {
//            log.info(new Date().toString() + "---> 没有查询到需要转码的mp4文件");
//        }
    }
//
//    //改变视频编码器
//    private void changeVideoDecoder(FileInfo fileInfo){
//        long oldTime = fileInfo.getMultimediaInfo().getDuration();
//
//        String url = fileInfo.getUrl();
//        String oldFilePath = fileRemoteDir + url;
//
//        String[] split = url.split("/");
//        String uuid = UUID.randomUUID().toString();
//        split[split.length-1] = uuid + targetExtension;
//        String newFilePath = StrUtil.join("/", Arrays.asList(split));
//
//        File inFile = new File(oldFilePath);
//        File outFile = new File(newFilePath);
//
//        try{
//            String remark = new MediaUtil().convertVideo(inFile, outFile);
//            fileInfo.setRemark(remark);
//            if (remark.equals("normal")){
//                MultimediaInfo newMI = getMultimediaInfo(newFilePath);
//
//                if (newMI != null){
//                    long newTime = newMI.getDuration();
//                    //视频时长相同
//                    if (newTime == oldTime){
//                        fileInfo.setUrl(newFilePath);
//                    }else {
//                        fileInfo.setRemark("转换成功后，视频时长不相同,原视频长度:"+ (oldTime / 1000) +"秒,新视频长度" + (newTime / 1000) +"秒");
//                    }
//                }else {
//                    fileInfo.setRemark("视频转码后，因未知原因无法获得新视频文件的信息");
//                }
//            }
//        }catch (Exception e){
//            fileInfo.setRemark("转码过程中，发生异常，转码失败 -->" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//
//
//    //设置文件信息
//    private void setMultimediaInfo(FileInfo fileInfo){
//        Encoder encoder = new Encoder();
//        File file = new File(fileRemoteDir + fileInfo.getUrl());
//
//        if (file.exists()){
//            try {
//                MultimediaInfo info = encoder.getInfo(file);
//                fileInfo.setMultimediaInfo(info);
//            } catch (EncoderException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    //获取文件信息
//    private MultimediaInfo getMultimediaInfo(String url){
//        Encoder encoder = new Encoder();
//        File file = new File(fileRemoteDir + url);
//
//        if (file.exists()){
//            try {
//                return encoder.getInfo(file);
//            } catch (EncoderException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return null;
//    }

}
