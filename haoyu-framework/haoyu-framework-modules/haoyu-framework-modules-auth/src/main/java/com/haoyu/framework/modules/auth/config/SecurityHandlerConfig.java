package com.haoyu.framework.modules.auth.config;

import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.utils.AuthResultCode;
import com.haoyu.framework.utils.ResponseUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * <p>
 * Security 结果处理配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Security 结果处理配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:31
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> ResponseUtils.renderJson(response, R.fail(AuthResultCode.ACCESS_DENIED));
    }

}
