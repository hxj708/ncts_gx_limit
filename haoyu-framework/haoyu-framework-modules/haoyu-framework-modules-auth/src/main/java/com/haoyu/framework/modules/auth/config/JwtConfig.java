package com.haoyu.framework.modules.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * JWT 配置
 */
@ConfigurationProperties(prefix = "jwt.config")
@Data
public class JwtConfig {
    /**
     * jwt 加密 key，要求Base64编码，长度≥256
     * 默认值明文：E:\eclipse\workspace\teacher-development-center\tdc-web-server\tdc-web-server-jiangmen||E:\eclipse\workspace\teacher-development-center\tdc-web-server\tdc-web-server-jiangmen
     * 默认值加密：RTpcZWNsaXBzZVx3b3Jrc3BhY2VcdGVhY2hlci1kZXZlbG9wbWVudC1jZW50ZXJcdGRjLXdlYi1zZXJ2ZXJcdGRjLXdlYi1zZXJ2ZXItamlhbmdtZW58fEU6XGVjbGlwc2Vcd29ya3NwYWNlXHRlYWNoZXItZGV2ZWxvcG1lbnQtY2VudGVyXHRkYy13ZWItc2VydmVyXHRkYy13ZWItc2VydmVyLWppYW5nbWVu.
     */
    private String key = "RTpcZWNsaXBzZVx3b3Jrc3BhY2VcdGVhY2hlci1kZXZlbG9wbWVudC1jZW50ZXJcdGRjLXdlYi1zZXJ2ZXJcdGRjLXdlYi1zZXJ2ZXItamlhbmdtZW58fEU6XGVjbGlwc2Vcd29ya3NwYWNlXHRlYWNoZXItZGV2ZWxvcG1lbnQtY2VudGVyXHRkYy13ZWItc2VydmVyXHRkYy13ZWItc2VydmVyLWppYW5nbWVu";

    /**
     * jwt 过期时间，单位秒，默认值：7200 {@code 120 分钟}.
     */
    private Long expire = 7200L;

    /**
     * 刷新 token 的间隔过期，单位秒，10 {@code 10 秒}
     */
    private Long refresh = 10L;

    /**
     * 开启 记住我 之后 jwt 过期时间，单位秒，默认值 300 {@code 5 分钟}
     */
    private Long remember = 604800L;

    /**
     * header中Token前缀
     */
    private String tokenPrefix="Bearer_";

    /**
     * header中Token前缀
     */
    private String tokenHeaderName="Authorization";

    /**
     * header中Token前缀
     */
    private String tokenHeaderNameRefresh="authorization-refresh";

    /**
     * 账号可同时在线人数,
     * -1：不限制(默认)
     * 1：只能同时1人在线
     * @Todo 默认是不限制，目前只有 -1/1 ,同账号多人在线需要增加session和count支持，尚未实现
     */
    private int concurrencyCount=-1;
}
