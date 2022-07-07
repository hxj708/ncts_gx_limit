package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.haoyu.framework.modules.auth.entity.RoleResource;
import com.haoyu.framework.modules.auth.mapper.RoleResourceMapper;
import com.haoyu.framework.modules.auth.service.RoleResourceService;
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
 * 权限-角色资源关联 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends BaseServiceImpl<RoleResourceMapper, RoleResource> implements RoleResourceService {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public R<RoleResource> create(RoleResource entity) {
        return super.create(entity);
    }

    @Override
    public R<RoleResource> update(RoleResource entity) {
        return super.update(entity);
    }

    @Override
    public R<RoleResource> update(RoleResource entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<RoleResource> wrapper = AuthWrapperUtils.getRoleResourceUpdateWrapper(map);
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
        RoleResource entity = new RoleResource();
        entity.setIsDeleted(RoleResource.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<RoleResource> wrapper = AuthWrapperUtils.getRoleResourceUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<RoleResource> wrapper = AuthWrapperUtils.getRoleResourceQueryWrapper(map);
        return super.count(wrapper);
    }

    @Override
    public int deleteByRoleId(String roleId) {
        return roleResourceMapper.deleteByRoleId(roleId);
    }

    @Override
    public R updateByRoleId(String roleId, String[] resourceIds) {
        int count = 0;
        if(StrUtil.isNotBlank(roleId)) {
            count = roleResourceMapper.deleteByRoleId(roleId);
            if (ArrayUtil.isNotEmpty(resourceIds)) {
                for (String resourceId : resourceIds) {
                    RoleResource entity = new RoleResource();
                    entity.setRoleId(roleId);
                    entity.setResourceId(resourceId);
                    entity.setId(roleId + "-" + resourceId);
                    count += this.create(entity).isSuccess() ? 1 : 0;
                }
            }
        }
        return count > 0 ? R.success() : R.fail();
    }

}
