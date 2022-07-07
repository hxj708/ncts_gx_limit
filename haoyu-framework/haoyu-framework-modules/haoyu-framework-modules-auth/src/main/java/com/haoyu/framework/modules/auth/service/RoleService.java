package com.haoyu.framework.modules.auth.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.auth.entity.Role;
import com.haoyu.framework.modules.auth.mapper.RoleMapper;

import java.util.List;

/**
 * <p>
 * 权限-角色 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface RoleService extends BaseService<Role,RoleMapper> {

	List<Role> getRoleByUser(String userId);

}
