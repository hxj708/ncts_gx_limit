package com.haoyu.framework.modules.file.service.impl;

import com.haoyu.framework.modules.file.entity.FileChangeDecoder;
import com.haoyu.framework.modules.file.mapper.FileChangeDecoderMapper;
import com.haoyu.framework.modules.file.service.FileChangeDecoderService;
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
 * 需要修改编码的文件表 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2021-01-11
 */
@Service("fileChangeDecoderService")
public class FileChangeDecoderServiceImpl extends BaseServiceImpl<FileChangeDecoderMapper,FileChangeDecoder> implements FileChangeDecoderService {

    @Autowired
    private FileChangeDecoderMapper fileChangeDecoderMapper;
    
    @Override
    public R<FileChangeDecoder> create(FileChangeDecoder entity) {
        return super.create(entity);
    }
    
    @Override
    public R<FileChangeDecoder> update(FileChangeDecoder entity) {
        return super.update(entity);
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
        FileChangeDecoder entity = new FileChangeDecoder();
        entity.setIsDeleted(FileChangeDecoder.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<FileChangeDecoder> wrapper = FileWrapperUtils.getFileChangeDecoderUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }


}
