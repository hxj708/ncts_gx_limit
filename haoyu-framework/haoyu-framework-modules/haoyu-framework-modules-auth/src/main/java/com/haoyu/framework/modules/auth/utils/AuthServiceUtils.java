package com.haoyu.framework.modules.auth.utils;

import cn.hutool.core.map.MapUtil;
import com.haoyu.framework.modules.auth.entity.Resource;

import java.util.Map;

public class AuthServiceUtils {

    public static Map<String,Object> toAuthResourceMap(Resource resource){
        Map<String, Object> data = MapUtil.newHashMap();
        if(resource!=null) {
            data.put("id", resource.getId());
            data.put("isShow", resource.getIsShow());
            data.put("parentId", resource.getParentId());
            data.put("value", resource.getValue());
            data.put("meta", resource.getMeta());
            data.put("rule", resource.getRule());
            data.put("icon", resource.getIcon());
            data.put("train", resource.getTrain());
            data.put("name", resource.getName());
            data.put("target", resource.getTarget());
            data.put("code", resource.getCode());
            data.put("moduleCode", resource.getModuleCode());
//            data.put("component", resource.getComponent());
//            data.put("resources", resource.getResources());

        }
        return data;
    }
}
