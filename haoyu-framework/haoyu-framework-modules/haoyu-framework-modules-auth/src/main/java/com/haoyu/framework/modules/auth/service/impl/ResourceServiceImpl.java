package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoyu.framework.modules.auth.entity.Module;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.haoyu.framework.modules.auth.mapper.ResourceMapper;
import com.haoyu.framework.modules.auth.service.ResourceService;
import com.haoyu.framework.modules.auth.utils.AuthConstants;
import com.haoyu.framework.modules.auth.utils.AuthWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.haoyu.framework.utils.JsonMapper;
import com.haoyu.framework.utils.PageUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限-资源 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("resourceService")
public class ResourceServiceImpl extends BaseServiceImpl<ResourceMapper,Resource> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public R<Resource> create(Resource entity) {
        R r = super.create(entity);
        if(r.isSuccess()){
            this.refreshCache();
        }
        return r;
    }

    @Override
    public R<Resource> update(Resource entity) {
        R r = super.update(entity);
        if(r.isSuccess()){
            this.refreshCache();
        }
        return r;
    }

    @Override
    public R<Resource> update(Resource entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<Resource> wrapper = AuthWrapperUtils.getResourceUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R deleteLogic(@NotNull String id) {
        List<String> ids = Arrays.asList(StrUtil.split(id, StrUtil.COMMA));
        R r = this.deleteLogic(ids);
        if(r.isSuccess()){
            this.refreshCache();
        }
        return r;
    }

    @Override
    public R deleteLogic(@NotEmpty List<String> ids) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("ids", ids);
        R r = this.deleteLogic(map);
        if(r.isSuccess()){
            this.refreshCache();
        }
        return r;
    }

    @Override
    public R deleteLogic(Map<String, Object> map) {
        if (MapUtil.isEmpty(map)) {
            return R.fail("map is empty");
        }
        Resource entity = new Resource();
        entity.setIsDeleted(Resource.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<Resource> wrapper = AuthWrapperUtils.getResourceUpdateWrapper(map);
            R r = super.update(entity, wrapper) ? R.success() : R.fail();
            if(r.isSuccess()){
                this.refreshCache();
            }
            return r;
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<Resource> wrapper = AuthWrapperUtils.getResourceQueryWrapper(map);
        return super.count(wrapper);
    }

    @Override
    public List<Resource> getAllResource() {
        List<Resource> resourceList = null;
        if (redisTemplate.hasKey(AuthConstants.KEY_ALL_RESOURCE_LIST)) {
            resourceList = (List<Resource>) redisTemplate.opsForValue().get(AuthConstants.KEY_ALL_RESOURCE_LIST);
        } else {
            resourceList = this.listByMap(MapUtil.newHashMap());
            redisTemplate.opsForValue().set(AuthConstants.KEY_ALL_RESOURCE_LIST,resourceList);
        }
        return resourceList;
    }

    private void refreshCache(){
        redisTemplate.delete(AuthConstants.KEY_ALL_RESOURCE_LIST);
    }


    /**
     * 读取用户拥有的权限
     *
     * @param userId
     * @return
     */
    @Override
    public List<Resource> getResourceByUser(String userId) {
        if (StrUtil.isNotBlank(userId)) {
            Map<String, Object> paramMap = MapUtil.newHashMap();
            paramMap.put("userId", userId);
//            Page page = new Page();
//            page.setSearchCount(false);
//            page.setCurrent(1);
//            page.setSize(Integer.MAX_VALUE);
//            page = PageUtils.setOrders(page, "TRAIN");
            return this.listByMap(paramMap);
        }
        return null;
    }

    /**
     * 菜单用，读取所有的菜单和模块列表，组成菜单
     *
     * @param moduleList
     * @param resourceList
     * @return
     */
    @Override
    public Map<String, String> createTreeMenuJsonMap(List<Module> moduleList, List<Resource> resourceList) {
        Map<String, String> menuMap = MapUtil.newHashMap();
        if (CollUtil.isNotEmpty(moduleList)) {
            for (Module module : moduleList) {
                List<Map<String, Object>> nodeMapList = CollUtil.newArrayList();
                if (CollUtil.isNotEmpty(resourceList)) {
                    nodeMapList = createTreeMenuList(module.getCode(), resourceList);
                    menuMap.put(module.getCode(), JsonMapper.defaultMapper().toJson(nodeMapList));
                }
            }
        }
        return menuMap;
    }

    @Override
    public List<Map<String, Object>> createTreeMenuList(String moduleCode, List<Resource> resourceList) {
        return createTreeMenuList(moduleCode, resourceList, false);
    }

    /**
     * 从权限列表中找出模块的权限，组成json
     *
     * @param moduleCode
     * @param resourceList
     * @return
     */
    @Override
    public List<Map<String, Object>> createTreeMenuList(String moduleCode, List<Resource> resourceList, boolean showIndexResource) {
        List<Map<String, Object>> nodeMapList = CollUtil.newArrayList();
        if (CollUtil.isNotEmpty(resourceList)) {
            for (Resource pri : resourceList) {
                if (StrUtil.equals(moduleCode, pri.getModuleCode()) && StrUtil.equals(AuthConstants.RESCOURCE_SHOW_YES, pri.getIsShow())) {
                    Map<String, Object> nodeMap = new LinkedHashMap<String, Object>();
                    nodeMap.put("id", pri.getId());
                    nodeMap.put("pid", pri.getParentId());
                    nodeMap.put("text", pri.getName());
                    nodeMap.put("detailText", pri.getName() + "(" + pri.getTrain() + ":" + pri.getValue() + ")");
                    nodeMap.put("iconCls", pri.getIcon());
                    nodeMap.put("open", StrUtil.isBlank(pri.getParentId()) ? true : false);
                    nodeMap.put("checked", false);
                    Map<String, Object> nodeAttributeMap = MapUtil.newHashMap();
                    nodeAttributeMap.put("target", pri.getTarget());
                    nodeAttributeMap.put("url", pri.getValue());
                    nodeAttributeMap.put("train", pri.getTrain());
                    nodeMap.put("attributes", nodeAttributeMap);
                    nodeMapList.add(nodeMap);
                }
            }
            return nodeMapList;
        }
        return null;
    }

}
