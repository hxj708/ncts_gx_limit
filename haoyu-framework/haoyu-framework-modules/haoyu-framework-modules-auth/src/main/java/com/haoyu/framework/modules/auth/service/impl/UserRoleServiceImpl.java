package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoyu.framework.modules.auth.entity.Role;
import com.haoyu.framework.modules.auth.entity.UserRole;
import com.haoyu.framework.modules.auth.mapper.RoleMapper;
import com.haoyu.framework.modules.auth.mapper.UserRoleMapper;
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
 * 权限-用户角色关联 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper,UserRole> implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public R<UserRole> create(UserRole entity) {
        return super.create(entity);
    }

    @Override
    public R<UserRole> update(UserRole entity) {
        return super.update(entity);
    }

    @Override
    public R<UserRole> update(UserRole entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<UserRole> wrapper = AuthWrapperUtils.getUserRoleUpdateWrapper(map);
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
        UserRole entity = new UserRole();
        entity.setIsDeleted(UserRole.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<UserRole> wrapper = AuthWrapperUtils.getUserRoleUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<UserRole> wrapper = AuthWrapperUtils.getUserRoleQueryWrapper(map);
        return super.count(wrapper);
    }

    @Override
    public R saveUserRole(String userId, String[] roleIds) {
        boolean isSuccess = true;
        if (StrUtil.isNotBlank(userId) && ArrayUtil.isNotEmpty(roleIds)) {
            for (String roleId : roleIds) {
                UserRole dat = new UserRole();
                dat.setUserId(userId);
                dat.setRoleId(roleId);
                dat.setId(userId + "_" + roleId);
                isSuccess = isSuccess && this.create(dat).isSuccess();
            }
        }
        return isSuccess ? R.success() : R.fail();
    }

    @Override
    public R deleteByUserId(String userId) {
        int count = userRoleMapper.deleteByUserId(userId);
        return R.status(count > 0);
    }

    @Override
    public R deleteByRoleId(String roleId) {
        int count = userRoleMapper.deleteByRoleId(roleId);
        return R.status(count > 0);
    }

    @Override
    public R deleteByUserRole(UserRole userRole) {
        int count = 0;
        return R.status(count > 0);
    }

    @Override
    public IPage<Map<String,Object>> pageUserWithRole(IPage page, Map<String, Object> map){
        return userRoleMapper.selectUserWithRole(page,map);
    }

    @Override
    public R updateByUserId(String userId,String[] roleIds) {
        int count = 0;
        if(StrUtil.isNotBlank(userId)){
            count = userRoleMapper.deleteByUserId(userId);
            if(ArrayUtil.isNotEmpty(roleIds)){
                for(String roleId : roleIds) {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(userId);
                    userRole.setRoleId(roleId);
                    count += this.create(userRole).isSuccess()?1:0;
                }
            }
        }
        return R.status(count > 0);
    }

    @Override
    public R createByRoleCode(String userId, String code) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("code", code);
        List<Role> roles = roleMapper.selectByMap(map);
        if (CollectionUtil.isNotEmpty(roles)){
            Role role = roles.get(0);
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getId());
            return this.create(userRole);
        }else{
            return R.fail("role not exists");
        }
    }
}
