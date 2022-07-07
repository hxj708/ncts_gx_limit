package com.haoyu.framework.modules.file.mapper;

import com.haoyu.framework.modules.file.entity.FileResource;
import com.haoyu.framework.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件资源表 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
public interface FileResourceMapper extends BaseMapper<FileResource> {

    /**
     * 修改文件数
     * @param cm
     */
   void updateFileNum(@Param("cm") Map<String,Object> cm);

}
