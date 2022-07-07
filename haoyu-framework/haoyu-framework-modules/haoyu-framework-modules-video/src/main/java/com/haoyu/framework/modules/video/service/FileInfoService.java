package com.haoyu.framework.modules.video.service;


import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.video.mapper.FileInfoMapper;
import com.haoyu.framework.modules.video.entity.FileInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
public interface FileInfoService extends BaseService<FileInfo,FileInfoMapper> {

    void checkVideByMap(Map<String,Object> map);

}
