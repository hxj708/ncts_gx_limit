package com.haoyu.framework.modules.video.mapper;

import com.haoyu.framework.modules.video.entity.FileInfo;
import com.haoyu.framework.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Repository("DecoderFileMapper")
public interface FileInfoMapper extends BaseMapper<FileInfo> {


    void deleteFileChangeDecoderByFileId(@Param("id") String id);

    void updateFileChangeDecoderByFileId(@Param("id") String id);

}
