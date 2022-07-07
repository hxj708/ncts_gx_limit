package com.haoyu.framework.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.entity.UserRole;
import com.haoyu.framework.modules.auth.mapper.UserRoleMapper;

import java.util.Map;

/**
 * <p>
 * 权限-用户角色关联 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface UserRoleService extends BaseService<UserRole,UserRoleMapper> {

    R saveUserRole(String userId, String[] roleIds);

    R deleteByUserId(String userId);

    R deleteByRoleId(String roleId);

    R deleteByUserRole(UserRole userRole);

    IPage<Map<String,Object>> pageUserWithRole(IPage page, Map<String, Object> map);

    R updateByUserId(String userId, String[] roleIds);

    R createByRoleCode(String userId, String code);
}
