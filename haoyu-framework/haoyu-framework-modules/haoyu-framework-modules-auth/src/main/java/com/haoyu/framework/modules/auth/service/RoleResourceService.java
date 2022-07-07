package com.haoyu.framework.modules.auth.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.entity.RoleResource;
import com.haoyu.framework.modules.auth.mapper.RoleResourceMapper;

/**
 * <p>
 * 权限-角色资源关联 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface RoleResourceService extends BaseService<RoleResource,RoleResourceMapper> {

	int deleteByRoleId(String roleId);

	R updateByRoleId(String roleId, String[] resourceIds);
}
