package com.haoyu.framework.modules.auth.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Sets;
import com.haoyu.framework.core.base.BaseException;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.config.AuthConfig;
import com.haoyu.framework.modules.auth.config.JwtConfig;
import com.haoyu.framework.modules.auth.entity.JwtResponse;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.service.LoginUserService;
import com.haoyu.framework.modules.auth.utils.AuthResultCode;
import com.haoyu.framework.modules.auth.utils.AuthUtils;
import com.haoyu.framework.utils.JsonMapper;
import com.haoyu.framework.utils.ResponseUtils;
import com.haoyu.framework.utils.ThreadContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * <p>
 * Jwt 认证过滤器
 * </p>
 *
 * @package: com.xkcoding.rbac.security.config
 * @description: Jwt 认证过滤器
 * @author: yangkai.shen
 * @date: Created in 2018-12-10 15:15
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Component
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private LoginUserService loginUserService;

    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("JwtAuthenticationFilter-RequestURI:" + request.getRequestURI());
        log.debug("JwtAuthenticationFilter-RequestURL:" + request.getRequestURL().toString());
        if (checkIgnores(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = authUtils.getJwtFromRequest(request);
        if (StrUtil.isNotBlank(jwt)) {
            try {
                String id = authUtils.getUserIdFromJWT(jwt);
                LoginUser loginUser = authUtils.getLoginUserCache(id);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(), loginUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //保证SpringSecurity中可以取得到授权信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
                //保证ThreadLocal里面能获取到登录人信息
                ThreadContext.bind(loginUser);
                //刷新Token，如果token有更新，则放在response中
                String newJwt=authUtils.refreshJWT(request,false);
                if(!StrUtil.equals(jwt,newJwt)){
                    JwtResponse jwtResponse = new JwtResponse(jwt,loginUser);
                    response.setHeader(jwtConfig.getTokenHeaderNameRefresh(), JsonMapper.defaultMapper().toJson(jwtResponse));
                }
                filterChain.doFilter(request, response);
            } catch (ExpiredJwtException e){
                ResponseUtils.renderJson(response, R.fail(AuthResultCode.TOKEN_EXPIRED));

            } catch (JwtException e) {
                ResponseUtils.renderJson(response, R.fail(AuthResultCode.UNAUTHORIZED,e.getMessage()));
//                throw e;
            } catch (BaseException e) {
                ResponseUtils.renderJson(response, e);
//                throw e;
            } catch (Exception e) {
                ResponseUtils.renderJson(response, R.fail(e.getMessage()));
//                throw e;
            }
        } else {
            ResponseUtils.renderJson(response, R.fail(AuthResultCode.UNAUTHORIZED));
//            throw new BaseException(AuthResultCode.UNAUTHORIZED);
        }

    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(authConfig.getIgnores().getGet());
                break;
            case PUT:
                ignores.addAll(authConfig.getIgnores().getPut());
                break;
            case HEAD:
                ignores.addAll(authConfig.getIgnores().getHead());
                break;
            case POST:
                ignores.addAll(authConfig.getIgnores().getPost());
                break;
            case PATCH:
                ignores.addAll(authConfig.getIgnores().getPatch());
                break;
            case TRACE:
                ignores.addAll(authConfig.getIgnores().getTrace());
                break;
            case DELETE:
                ignores.addAll(authConfig.getIgnores().getDelete());
                break;
            case OPTIONS:
                ignores.addAll(authConfig.getIgnores().getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(authConfig.getIgnores().getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;
    }

}
