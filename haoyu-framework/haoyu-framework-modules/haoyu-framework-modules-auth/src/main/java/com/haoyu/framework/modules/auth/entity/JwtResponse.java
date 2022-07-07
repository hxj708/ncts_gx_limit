package com.haoyu.framework.modules.auth.entity;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import com.haoyu.framework.utils.JsonMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * JWT 响应返回
 * </p>
 *
 * @package: com.xkcoding.rbac.security.vo
 * @description: JWT 响应返回
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 16:01
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    /**
     * token 字段
     */
    private String token;
    /**
     * token类型
     */
    private String tokenType = "Bearer";
    /**
     * 角色Code
     */
    private List<String> roleCodeList = null;
    /**
     * 资源Code
     */
    private List<String> resourceCodeList = null;
    /**
     * 用户信息 Json Base64
     */
    private String loginUser = null;

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse(String token, LoginUser loginUser) {
        this.token = token;
        this.roleCodeList = loginUser.getRoleCodeList();
        this.resourceCodeList = loginUser.getResourceCodeList();
        this.loginUser = Base64.encode(JsonMapper.defaultMapper().toJson(loginUser.getSimpleLoginUser()));
    }

}
