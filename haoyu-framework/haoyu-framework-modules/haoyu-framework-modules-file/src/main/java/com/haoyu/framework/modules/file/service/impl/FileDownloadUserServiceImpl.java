package com.haoyu.framework.modules.file.service.impl;

import com.haoyu.framework.modules.file.entity.FileDownloadUser;
import com.haoyu.framework.modules.file.mapper.FileDownloadUserMapper;
import com.haoyu.framework.modules.file.service.FileDownloadUserService;
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
 * 文件下载表 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Service("fileDownloadUserService")
public class FileDownloadUserServiceImpl extends BaseServiceImpl<FileDownloadUserMapper,FileDownloadUser> implements FileDownloadUserService {

    @Autowired
    private FileDownloadUserMapper fileDownloadUserMapper;

    @Override
    public R<FileDownloadUser> create(FileDownloadUser entity) {
        return super.create(entity);
    }

    @Override
    public R<FileDownloadUser> update(FileDownloadUser entity) {
        return super.update(entity);
    }

    @Override
    public R<FileDownloadUser> update(FileDownloadUser entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<FileDownloadUser> wrapper = FileWrapperUtils.getFileDownloadUserUpdateWrapper(map);
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
        FileDownloadUser entity = new FileDownloadUser();
        entity.setIsDeleted(FileDownloadUser.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<FileDownloadUser> wrapper = FileWrapperUtils.getFileDownloadUserUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<FileDownloadUser> wrapper = FileWrapperUtils.getFileDownloadUserQueryWrapper(map);
        return super.count(wrapper);
    }

}
