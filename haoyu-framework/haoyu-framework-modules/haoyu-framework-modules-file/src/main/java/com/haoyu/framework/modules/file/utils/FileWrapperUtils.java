package com.haoyu.framework.modules.file.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.haoyu.framework.modules.file.entity.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工具类：用于Map转为MyBatisPlus的Wrapper
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
public class FileWrapperUtils {

  /**
   * FileDownloadUser
   */
  public static LambdaQueryWrapper<FileDownloadUser> getFileDownloadUserQueryWrapper(Map<String, Object> map) {
    LambdaQueryWrapper<FileDownloadUser> wrapper = Wrappers.lambdaQuery();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileDownloadUser::getId, ids);
    }
    return wrapper;
  }


  /**
   * FileDownloadUser
   */
  public static LambdaUpdateWrapper<FileDownloadUser> getFileDownloadUserUpdateWrapper(Map<String, Object> map) {
    LambdaUpdateWrapper<FileDownloadUser> wrapper = Wrappers.lambdaUpdate();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileDownloadUser::getId, ids);
    } else {
      throw new IllegalArgumentException("key is wrong");
    }
    return wrapper;
  }


  /**
   * FileInfo
   */
  public static LambdaQueryWrapper<FileInfo> getFileInfoQueryWrapper(Map<String, Object> map) {
    LambdaQueryWrapper<FileInfo> wrapper = Wrappers.lambdaQuery();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileInfo::getId, ids);
    }
    return wrapper;
  }


  /**
   * FileInfo
   */
  public static LambdaUpdateWrapper<FileInfo> getFileInfoUpdateWrapper(Map<String, Object> map) {
    LambdaUpdateWrapper<FileInfo> wrapper = Wrappers.lambdaUpdate();
    if (MapUtil.isEmpty(map)) {
      throw new IllegalArgumentException("key is wrong");
    }
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileInfo::getId, ids);
    }
    return wrapper;
  }


  /**
   * FileRelation
   */
  public static LambdaQueryWrapper<FileRelation> getFileRelationQueryWrapper(Map<String, Object> map) {
    LambdaQueryWrapper<FileRelation> wrapper = Wrappers.lambdaQuery();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileRelation::getId, ids);
    }
    if (map.containsKey("fileId")){
      wrapper.eq(FileRelation::getFileId, map.get("fileId"));
    }
    if (map.containsKey("relationId")){
      wrapper.eq(FileRelation::getRelationId, map.get("relationId"));
    }
    return wrapper;
  }


  /**
   * FileRelation
   */
  public static LambdaUpdateWrapper<FileRelation> getFileRelationUpdateWrapper(Map<String, Object> map) {
    LambdaUpdateWrapper<FileRelation> wrapper = Wrappers.lambdaUpdate();
    if (MapUtil.isEmpty(map)) {
      throw new IllegalArgumentException("map is empty");
    }
    boolean hasKey = false;
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileRelation::getId, ids);
      hasKey = true;
    }
    if (map.containsKey("fileId") && StrUtil.isNotEmpty(((String)map.get("fileId")))){
      wrapper.eq(FileRelation::getFileId, map.get("fileId"));
      hasKey = true;
    }
    if (map.containsKey("relationId") && StrUtil.isNotEmpty(((String)map.get("relationId")))){
      wrapper.eq(FileRelation::getRelationId, map.get("relationId"));
      hasKey = true;
    }
    if (!hasKey){
      throw new IllegalArgumentException("key is wrong");
    }
    return wrapper;
  }


  /**
   * FileResource
   */
  public static LambdaQueryWrapper<FileResource> getFileResourceQueryWrapper(Map<String, Object> map) {
    LambdaQueryWrapper<FileResource> wrapper = Wrappers.lambdaQuery();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileResource::getId, ids);
    }
    return wrapper;
  }


  /**
   * FileResource
   */
  public static LambdaUpdateWrapper<FileResource> getFileResourceUpdateWrapper(Map<String, Object> map) {
    LambdaUpdateWrapper<FileResource> wrapper = Wrappers.lambdaUpdate();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileResource::getId, ids);
    } else {
      throw new IllegalArgumentException("key is wrong");
    }
    return wrapper;
  }

  /**
   * FileChangeDecoder
   */
  public static LambdaQueryWrapper<FileChangeDecoder> getFileChangeDecoderQueryWrapper(Map<String, Object> map){
    LambdaQueryWrapper<FileChangeDecoder> wrapper = Wrappers.lambdaQuery();
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileChangeDecoder::getId, ids);
    }
    return wrapper;
  }
  /**
   * FileChangeDecoder
   */
  public static LambdaUpdateWrapper<FileChangeDecoder> getFileChangeDecoderUpdateWrapper(Map<String, Object> map){
    LambdaUpdateWrapper<FileChangeDecoder> wrapper = Wrappers.lambdaUpdate();
    if (MapUtil.isEmpty(map)) {
      throw new IllegalArgumentException("map is empty");
    }
    boolean hasKey = false;
    if (map.containsKey("ids")) {
      List<String> ids = (List<String>) map.get("ids");
      wrapper.in(FileChangeDecoder::getId, ids);
      hasKey = true;
    }
    if (!hasKey){
      throw new IllegalArgumentException("key is wrong");
    }
    return wrapper;
  }
}
