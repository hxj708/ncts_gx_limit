package com.haoyu.framework.modules.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * com.haoyu.framework.modules.auth.entity
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
public class AuthVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private LoginLog loginLog = new LoginLog();
    private LoginLogStat loginLogStat = new LoginLogStat();
    private Module module = new Module();
    private Resource resource = new Resource();
    private Role role = new Role();
    private UserRole userRole = new UserRole();
    private RoleResource roleResource = new RoleResource();
    private LoginUser loginUser = new LoginUser();
}
