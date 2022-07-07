package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.haoyu.framework.modules.auth.entity.Role;
import com.haoyu.framework.modules.auth.entity.UserRole;
import com.haoyu.framework.modules.auth.mapper.RoleMapper;
import com.haoyu.framework.modules.auth.service.RoleService;
import com.haoyu.framework.modules.auth.service.UserRoleService;
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
 * 权限-角色 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public R<Role> create(Role entity) {
        if (this.checkCodeExist(entity.getId(), entity.getCode())) {
            return R.fail("代码标识已存在！");
        } else {
            return super.create(entity);
        }
    }

    @Override
    public R<Role> update(Role entity) {
        if (this.checkCodeExist(entity.getId(), entity.getCode())) {
            return R.fail("代码标识已存在！");
        } else {
            return super.update(entity);
        }
    }

    @Override
    public R<Role> update(Role entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<Role> wrapper = AuthWrapperUtils.getRoleUpdateWrapper(map);
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
        Role entity = new Role();
        entity.setIsDeleted(Role.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<Role> wrapper = AuthWrapperUtils.getRoleUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<Role> wrapper = AuthWrapperUtils.getRoleQueryWrapper(map);
        return super.count(wrapper);
    }

    /**
     * 读取UserId拥有的Role
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> getRoleByUser(String userId) {
        if (StrUtil.isNotBlank(userId)) {
            Map<String, Object> paramMap = MapUtil.newHashMap();
            paramMap.put("userId", userId);
            return baseMapper.selectByMap(paramMap);
        }
        return null;
    }

    /**
     * 校验Code是否存在
     *
     * @param id
     * @param code
     * @return
     */
    private boolean checkCodeExist(String id, String code) {
        LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(id)) {
            wrapper.ne(Role::getId, id);
        }
        wrapper.eq(Role::getCode, code);
        return super.count(wrapper) > 0 ? true : false;
    }

}
