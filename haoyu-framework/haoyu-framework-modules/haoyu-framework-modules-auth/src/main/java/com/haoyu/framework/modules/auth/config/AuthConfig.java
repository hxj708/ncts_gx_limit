package com.haoyu.framework.modules.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 权限模块自定义配置
 *
 * @author Chaos
 */
@ConfigurationProperties(prefix = "auth.config")
@Data
public class AuthConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
