package com.haoyu.framework.modules.dict.web;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.utils.DictRegionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("regionController")
@RequestMapping("/api/v1/region")
public class RegionController extends BaseController {

    @GetMapping("list")
    public R list(String level, String parentCode) {
        return R.success().setData(DictRegionUtils.getRegionList(level, parentCode));
    }

    @GetMapping("map")
    public R map(String level, String parentCode) {
        return R.success().setData(DictRegionUtils.getRegionMap(level, parentCode));
    }

    @GetMapping("entry")
    public R entry(String level, String code) {
        return R.success().setData(DictRegionUtils.getRegion(level, code));
    }

    @GetMapping("getName")
    public R getName(String level, String code) {
        return R.success().setData(DictRegionUtils.getRegionName(level, code));
    }

    @GetMapping("getValue")
    public R getValue(String level, String name) {
        return R.success().setData(DictRegionUtils.getRegionCode(level, name));
    }

    @GetMapping("initVueData")
    public String initVueData() {
        List<DictRegion> province = DictRegionUtils.getRegionList("1","");
        List<Map<String, Object>> list = CollectionUtil.newArrayList();
        Map<String, Map<String, Object>> province_map = MapUtil.newHashMap();
        for (DictRegion region : province){
            Map<String, Object> map = MapUtil.newHashMap();
            map.put("label", region.getName());
            map.put("value", region.getCode());
            List<Map<String, Object>> children = CollectionUtil.newArrayList();
            map.put("children", children);
            list.add(map);
            province_map.put(region.getCode(), map);
        }
        List<DictRegion> cities = DictRegionUtils.getRegionList("2","");
        Map<String, Map<String, Object>> city_map = MapUtil.newHashMap();
        for (DictRegion region : cities){
            Map<String, Object> map = MapUtil.newHashMap();
            map.put("label", region.getName());
            map.put("value", region.getCode());
            List<Map<String, Object>> children = CollectionUtil.newArrayList();
            map.put("children", children);
            Map<String, Object> parent = province_map.get(region.getParentCode());
            List<Map<String, Object>> parent_children = (List<Map<String, Object>>)parent.get("children");
            parent_children.add(map);
            city_map.put(region.getCode(), map);
        }
        List<DictRegion> counties = DictRegionUtils.getRegionList("3","");
        for (DictRegion region : counties){
            Map<String, Object> map = MapUtil.newHashMap();
            map.put("label", region.getName());
            map.put("value", region.getCode());
            List<Map<String, Object>> children = CollectionUtil.newArrayList();
            map.put("children", children);
            Map<String, Object> parent = city_map.get(region.getParentCode());
            List<Map<String, Object>> parent_children = (List<Map<String, Object>>)parent.get("children");
            parent_children.add(map);
        }
        return JSONUtil.toJsonStr(list);
    }

}
