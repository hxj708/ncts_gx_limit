package com.haoyu.framework.modules.auth.service.impl;

import com.haoyu.framework.modules.auth.entity.Module;
import com.haoyu.framework.modules.auth.mapper.ModuleMapper;
import com.haoyu.framework.modules.auth.service.ModuleService;
import com.haoyu.framework.modules.auth.utils.AuthWrapperUtils;
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
 * 权限-模块 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("moduleService")
public class ModuleServiceImpl extends BaseServiceImpl<ModuleMapper,Module> implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;
    
    @Override
    public R<Module> create(Module entity) {
        return super.create(entity);
    }
    
    @Override
    public R<Module> update(Module entity) {
        return super.update(entity);
    }
    
    @Override
    public R<Module> update(Module entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<Module> wrapper = AuthWrapperUtils.getModuleUpdateWrapper(map);
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
        Module entity = new Module();
        entity.setIsDeleted(Module.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<Module> wrapper = AuthWrapperUtils.getModuleUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }
    
    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<Module> wrapper = AuthWrapperUtils.getModuleQueryWrapper(map);
        return super.count(wrapper);
    }

}
