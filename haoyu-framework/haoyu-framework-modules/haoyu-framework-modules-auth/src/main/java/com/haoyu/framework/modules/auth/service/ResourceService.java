package com.haoyu.framework.modules.auth.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.auth.entity.Module;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.haoyu.framework.modules.auth.mapper.ResourceMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限-资源 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface ResourceService extends BaseService<Resource,ResourceMapper> {

	List<Resource> getAllResource();

	List<Resource> getResourceByUser(String userId);

	Map<String, String> createTreeMenuJsonMap(List<Module> moduleList, List<Resource> resourceList);

	List<Map<String, Object>> createTreeMenuList(String moduleCode, List<Resource> resourceList);

	List<Map<String, Object>> createTreeMenuList(String moduleCode, List<Resource> resourceList, boolean showIndexResource);

}
