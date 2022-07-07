package com.haoyu.framework.modules.auth.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.haoyu.framework.modules.auth.entity.UserRole;
import com.haoyu.framework.core.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * 权限-用户角色关联 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Repository("userRoleMapper")
public interface UserRoleMapper extends BaseMapper<UserRole> {

    int deleteByUserId(String userId);

    int deleteByRoleId(String roleId);

    int deleteByUserRole(UserRole userRole);

    <E extends IPage<Map<String,Object>>> E selectUserWithRole(E page, @Param(Constants.COLUMN_MAP) Map<String, Object> map);

}
