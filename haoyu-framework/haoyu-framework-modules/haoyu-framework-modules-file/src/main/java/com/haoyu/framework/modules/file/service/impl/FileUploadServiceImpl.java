package com.haoyu.framework.modules.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileChangeDecoder;
import com.haoyu.framework.modules.file.entity.FileInfo;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.service.FileChangeDecoderService;
import com.haoyu.framework.modules.file.service.FileInfoService;
import com.haoyu.framework.modules.file.service.FileRelationService;
import com.haoyu.framework.modules.file.service.FileUploadService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.relation.Relation;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class FileUploadServiceImpl implements FileUploadService {

  @Autowired
  private FileInfoService fileInfoService;
  @Autowired
  private FileRelationService fileRelationService;
  @Autowired
  private FileChangeDecoderService fileChangeDecoderService;

  @Value("${file.forbid-suffix}")
  private String fileForbidSuffix;
  @Value("${file.remote-dir}")
  private String fileRemoteDir;
  @Value("${file.temp-dir}")
  private String fileTempDir;

  @Override
  public R uploadFile(byte[] bytes, long size, FileInfo fileInfo, String originalFilename) {
    String subfix = StringUtils.substringAfterLast(originalFilename, ".");
    if (StringUtils.isNotEmpty(fileForbidSuffix) && fileForbidSuffix.contains(subfix)) {
      return R.fail("forbid file type");
    }
    try {
      String url = new StringBuilder().append(DateFormatUtils.format(new Date(), "yyyy-MM-dd")).append("/").append(IdUtil.simpleUUID()).append(".").append(StringUtils.substringAfterLast(originalFilename, ".")).toString();
      File destFile = new File(fileRemoteDir + url);
      if (!destFile.getParentFile().exists()) {
        destFile.getParentFile().mkdirs();
      }
      FileUtil.writeBytes(bytes, destFile);
      fileInfo.setFileName(originalFilename);
      fileInfo.setUrl(url);
      fileInfo.setFileSize(BigDecimal.valueOf(FileUtil.size(destFile)));
      fileInfoService.create(fileInfo);
      return R.success().setData(fileInfo);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
    return R.fail();
  }

  @Override
  public R uploadTemp(byte[] bytes, String originalFilename) {
    String subfix = StringUtils.substringAfterLast(originalFilename, ".");
    if (StringUtils.isNotEmpty(fileForbidSuffix) && fileForbidSuffix.contains(subfix)) {
      return R.fail("forbid file type");
    }
    try {
      String url = new StringBuilder().append(DateFormatUtils.format(new Date(), "yyyy-MM-dd")).append("/").append(IdUtil.simpleUUID()).append(".").append(StringUtils.substringAfterLast(originalFilename, ".")).toString();
      File destFile = new File(fileTempDir + url);
      if (!destFile.getParentFile().exists()) {
        destFile.getParentFile().mkdirs();
      }
      FileUtil.writeBytes(bytes, destFile);
      FileInfo fileInfo = new FileInfo();
      fileInfo.setFileName(originalFilename);
      fileInfo.setUrl(url);
      fileInfo.setFileSize(BigDecimal.valueOf(FileUtil.size(destFile)));
      return R.success().setData(fileInfo);
    } catch (IllegalStateException e) {
      e.printStackTrace();
    }
    return R.fail();
  }

  @Override
  public R createFile(FileInfo fileInfo, String relationId, String relationType) {
    if (fileInfo != null){
      return this.createFileList(CollectionUtil.newArrayList(fileInfo), relationId, relationType);
    }
    return R.fail();
  }

  @Override
  public R updateFile(FileInfo fileInfo, String relationId, String relationType) {
    if (fileInfo == null){
      fileInfo = new FileInfo();
    }
    return this.updateFileList(CollectionUtil.newArrayList(fileInfo), relationId, relationType);
  }

  @Override
  public R createFileList(List<FileInfo> fileInfos, String relationId, String relationType) {
    if (CollectionUtil.isNotEmpty(fileInfos)) {
      List<File> files = CollectionUtil.newArrayList();
      for (FileInfo fileInfo : fileInfos) {
        File file = new File(fileTempDir + fileInfo.getUrl());
        R r = this.uploadFileToServer(file, fileInfo.getFileName(), fileInfo.getPrefixDir());
        if (r.isSuccess()) {
          String url = (String)r.getData();
          FileRelation fileRelation = new FileRelation();
          fileRelation.setRelationId(relationId);
          fileRelation.setType(relationType);
          fileInfo.getFileRelations().add(fileRelation);
          fileInfo.setFileName(fileInfo.getFileName());
          fileInfo.setUrl(url);
//          fileInfo.setFileSize(BigDecimal.valueOf(file.length()));
          r = fileInfoService.create(fileInfo);
          if (r.isSuccess()) {
            files.add(file);
            checkMultimediaInfo(fileInfo);
          }
        }
      }
      for (File file : files) {
        FileUtil.del(file);
      }
      return R.success();
    }
    return R.fail();
  }

  public void checkMultimediaInfo(FileInfo fileInfo){
//    String url = fileInfo.getUrl();
//    if (url.toLowerCase().endsWith(".mp4")){
//      Encoder encoder = new Encoder();
//      File file = new File(fileRemoteDir + fileInfo.getUrl());
//
//      if (file.exists()){
//        try {
//          MultimediaInfo info = encoder.getInfo(file);
//          if (info != null){
//            VideoInfo video = info.getVideo();
//            if (video != null){
//              String decoder = video.getDecoder();
//              if (!decoder.equals("h264")){
//                //创建一条记录
//                FileChangeDecoder fileChangeDecoder = new FileChangeDecoder();
//                fileChangeDecoder.setFileId(fileInfo.getId());
//                fileChangeDecoderService.create(fileChangeDecoder);
//              }
//            }
//          }
//        } catch (EncoderException e) {
//          e.printStackTrace();
//        }
//      }
//    }
  }

  @Override
  public R updateFileList(List<FileInfo> newList, String relationId, String relationType) {
    Map<String, Object> param = MapUtil.newHashMap();
    param.put("relationId", relationId);
    param.put("relationType", relationType);
    List<FileInfo> oldList = fileInfoService.listByMap(param);
    if (CollectionUtil.isNotEmpty(newList) || CollectionUtil.isNotEmpty(oldList)) {
      List<FileInfo> addList = CollectionUtil.subtractToList(newList, oldList);
      List<FileInfo> deleteList = CollectionUtil.subtractToList(oldList, newList);
      if (CollectionUtil.isNotEmpty(deleteList)) {
        for (FileInfo fileInfo: deleteList) {
          Map<String, Object> deleteParam = MapUtil.newHashMap();
          deleteParam.put("fileId", fileInfo.getId());
          deleteParam.put("relationId", relationId);
          fileRelationService.deleteLogic(deleteParam);
        }
      }
      createFileList(addList, relationId, relationType);
    }
    return R.success();
  }

  private R uploadFileToServer(File file, String fileName, String prefixDir) {
    try {
        String url = null;
        if (StringUtils.isEmpty(prefixDir)) {
          url = new StringBuilder().append(DateFormatUtils.format(new Date(), "yyyy-MM-dd")).append("/").append(IdUtil.simpleUUID()).append(".").append(StringUtils.substringAfterLast(fileName, ".")).toString();
        }else{
          url = new StringBuilder().append(prefixDir).append("/").append(IdUtil.simpleUUID()).append(".").append(StringUtils.substringAfterLast(fileName, ".")).toString();
        }
        FileUtil.move(file, new File(fileRemoteDir + url), true);
        return R.success().setData(url);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return R.fail();
  }

}
