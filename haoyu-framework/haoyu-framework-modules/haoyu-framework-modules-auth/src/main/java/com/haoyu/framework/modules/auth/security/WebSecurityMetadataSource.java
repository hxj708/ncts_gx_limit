package com.haoyu.framework.modules.auth.security;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.haoyu.framework.modules.auth.service.ResourceService;
import com.haoyu.framework.modules.auth.utils.AuthConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 提供LinkedHashMap<String, String>形式的URL及授权关系定义，
 * 并最终转为SpringSecurity所需的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式的定义.
 *
 * @author calvin
 */
@Slf4j
@Component("webSecurityMetadataSource")
public class WebSecurityMetadataSource implements SecurityMetadataSource {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RedisTemplate redisTemplate;

    private static LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> requestMap = null;

    /**
     * 整理全部权限列表
     *
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String, String> getRequestMap() {
        if(redisTemplate==null){
            log.error("redisTemplate 获取失败!");
        }

        List<Resource> resourceList = resourceService.getAllResource();
        LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
        for (Resource resource : resourceList) {
            if (resource.getValue() != null && resource.getRoleIds() != null) {
                requestMap.put(resource.getValue(), resource.getRoleCodes());
            }
        }
        return requestMap;
    }

    /**
     * 将resourceDetailService提供LinkedHashMap<String, String>形式的URL及授权关系定义
     * 转化为DefaultFilterInvocationDefinitionSource需要的LinkedHashMap<RequestKey, ConfigAttributeDefinition>形式.
     */
    protected LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> buildRequestMap() {
        LinkedHashMap<String, String> srcMap = this.getRequestMap();
        LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> distMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

        for (Map.Entry<String, String> entry : srcMap.entrySet()) {
            RequestMatcher requestMatcher = new AntPathRequestMatcher(entry.getKey());
            List<String> roleCodeList = roleCodeList = StrUtil.split(entry.getValue(), CharUtil.COMMA, true, true);
            if(CollUtil.isNotEmpty(roleCodeList)) {
                distMap.put(requestMatcher, SecurityConfig.createList(roleCodeList.toArray(new String[roleCodeList.size()])));
            } else {
                distMap.put(requestMatcher, new ArrayList<ConfigAttribute>());
            }
        }

        return distMap;
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        if (requestMap == null) {
            requestMap = buildRequestMap();
        }
        Iterator<RequestMatcher> ite = requestMap.keySet().iterator();
        while (ite.hasNext()) {
            RequestMatcher requestMatcher = ite.next();
            if (requestMatcher.matches(filterInvocation.getHttpRequest())) {
                return requestMap.get(requestMatcher);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        if (requestMap == null) {
            requestMap = buildRequestMap();
        }
        Set<ConfigAttribute> all = new HashSet<ConfigAttribute>();
        Iterator<Collection<ConfigAttribute>> it = requestMap.values().iterator();
        while (it.hasNext()) {
            all.addAll(it.next());
        }
        return all;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
