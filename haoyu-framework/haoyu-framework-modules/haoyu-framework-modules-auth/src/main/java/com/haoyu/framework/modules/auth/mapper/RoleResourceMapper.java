package com.haoyu.framework.modules.auth.mapper;

import com.haoyu.framework.modules.auth.entity.RoleResource;
import com.haoyu.framework.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限-角色资源关联 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Repository("roleResourceMapper")
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
	
	int deleteByRoleId(String roleId);

}
