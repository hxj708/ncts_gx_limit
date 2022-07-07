package com.haoyu.framework.modules.auth.config;

import com.haoyu.framework.modules.auth.security.AuthenticationProvider;
import com.haoyu.framework.modules.auth.security.JwtAuthenticationFilter;
import com.haoyu.framework.modules.auth.service.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * Security 配置
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Security 配置
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 16:46
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(AuthConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return new ProviderManager(authenticationProvider);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(loginUserService).passwordEncoder(encoder());
    }

    /**
     * <s:http auto-config="false" use-expressions="true" entry-point-ref="authenticationProcessingFilterEntryPoint">
     * <s:anonymous enabled="false"/>
     * <s:csrf disabled="true"/>
     * <s:intercept-url pattern="/**" access="authenticated" />
     * <s:access-denied-handler error-page="/common/403.jsp"/>
     * <s:session-management invalid-session-strategy-ref="" invalid-session-url="/gateway/route/goIndex.action"/>
     * <s:session-management session-authentication-error-url="/gateway/route/goIndex.action"/>
     * <s:session-management>
     * <s:concurrency-control error-if-maximum-exceeded="true" max-sessions="10" expired-url="/gateway/route/goIndex.action"/>
     * </s:session-management>
     * <s:headers>
     * <s:frame-options policy="SAMEORIGIN"/>
     * </s:headers>
     * <s:logout logout-url="/logout" logout-success-url="/gateway/route/goIndex.action" invalidate-session="true" delete-cookies="JSESSIONID"/>
     * <s:custom-filter ref="loginFilter" position="FORM_LOGIN_FILTER"/>
     * <s:custom-filter ref="webAccessFilter" before="FILTER_SECURITY_INTERCEPTOR"/>
     * </s:http>
     *
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        /*
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().logout().permitAll();
        */

        //开启跨域访问
        httpSecurity.cors();
        // 关闭 CSRF
        httpSecurity.csrf().disable();
        // 登录行为由自己实现，参考 AuthController#login
        httpSecurity.formLogin().disable();
        httpSecurity.httpBasic().disable();

        // 认证请求 - RBAC 动态 url 认证
//        httpSecurity.authorizeRequests().antMatchers("/**").access("@rbacAuthorityService.hasPermission(request,authentication)");
        // 认证请求 - 其它所有请求都需要登录访问
        httpSecurity.authorizeRequests().anyRequest().authenticated();

        // 登出行为由自己实现，参考 AuthController#logout
        httpSecurity.logout().disable();
        // 因为使用了JWT，所以这里不管理Session
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 异常处理
        httpSecurity.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
        //禁用页面缓存，返回的都是json
        httpSecurity.headers().frameOptions().disable().cacheControl();

        // 添加自定义 JWT 过滤器
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 放行所有不需要登录就可以访问的请求，参见 AuthController
     * 也可以在 {@link #configure(HttpSecurity)} 中配置
     * {@code httpSecurity.authorizeRequests().antMatchers("/api/auth/**").permitAll()}
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity and = web.ignoring().and();

        // 忽略 GET
        authConfig.getIgnores().getGet().forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url));

        // 忽略 POST
        authConfig.getIgnores().getPost().forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url));

        // 忽略 DELETE
        authConfig.getIgnores().getDelete().forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url));

        // 忽略 PUT
        authConfig.getIgnores().getPut().forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url));

        // 忽略 HEAD
        authConfig.getIgnores().getHead().forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url));

        // 忽略 PATCH
        authConfig.getIgnores().getPatch().forEach(url -> and.ignoring().antMatchers(HttpMethod.PATCH, url));

        // 忽略 OPTIONS
        authConfig.getIgnores().getOptions().forEach(url -> and.ignoring().antMatchers(HttpMethod.OPTIONS, url));

        // 忽略 TRACE
        authConfig.getIgnores().getTrace().forEach(url -> and.ignoring().antMatchers(HttpMethod.TRACE, url));

        // 按照请求格式忽略
        authConfig.getIgnores().getPattern().forEach(url -> and.ignoring().antMatchers(url));

    }
}
