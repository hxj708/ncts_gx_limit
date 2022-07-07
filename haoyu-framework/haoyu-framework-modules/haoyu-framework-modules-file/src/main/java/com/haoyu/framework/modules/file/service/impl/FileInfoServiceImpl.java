package com.haoyu.framework.modules.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.haoyu.framework.modules.file.entity.FileInfo;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.mapper.FileInfoMapper;
import com.haoyu.framework.modules.file.service.FileInfoService;
import com.haoyu.framework.modules.file.service.FileRelationService;
import com.haoyu.framework.modules.file.utils.FileWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件表 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Service("fileInfoService")
public class FileInfoServiceImpl extends BaseServiceImpl<FileInfoMapper,FileInfo> implements FileInfoService {

    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private FileRelationService fileRelationService;

    @Override
    public R<FileInfo> create(FileInfo entity) {
        R r = super.create(entity);
        if (r.isSuccess()){
            if (CollectionUtil.isNotEmpty(entity.getFileRelations())){
                for (FileRelation fileRelation : entity.getFileRelations()){
                    if (StrUtil.isNotEmpty(fileRelation.getRelationId())){
                        if (StrUtil.isEmpty(fileRelation.getFileId())){
                            fileRelation.setFileId(entity.getId());
                        }
                        fileRelationService.create(fileRelation);
                    }
                }
            }
        }
        return r;
    }

    @Override
    public R<FileInfo> update(FileInfo entity) {
        return super.update(entity);
    }

    @Override
    public R<FileInfo> update(FileInfo entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<FileInfo> wrapper = FileWrapperUtils.getFileInfoUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R deleteLogic(@NotNull String id) {
        List<String> ids = Arrays.asList(StrUtil.split(id, StrUtil.COMMA));
        return this.deleteLogic(ids);
    }

    @Override
        public R deleteLogic(@NotEmpty List<String> ids) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("ids", ids);
        return this.deleteLogic(map);
    }

    @Override
    public R deleteLogic(Map<String, Object> map) {
        if (MapUtil.isEmpty(map)) {
            return R.fail("map is empty");
        }
        FileInfo entity = new FileInfo();
        entity.setIsDeleted(FileInfo.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<FileInfo> wrapper = FileWrapperUtils.getFileInfoUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<FileInfo> wrapper = FileWrapperUtils.getFileInfoQueryWrapper(map);
        return super.count(wrapper);
    }

}
