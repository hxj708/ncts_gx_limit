package com.haoyu.framework.modules.dict.utils;

import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.service.DictRegionService;
import com.haoyu.framework.modules.dict.service.DictRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 区域字典工具类
 *
 * @author shisibo
 */
@Component
public class DictRegionUtils {

  private static DictRegionUtils dictRegionUtils;
  @Autowired
  private DictRegionService dictRegionService;

  @PostConstruct
  public void init() {
    dictRegionUtils = this;
    dictRegionUtils.dictRegionService = this.dictRegionService;
  }

  public static List<DictRegion> getRegionList(String level, String parentCode) {
    if (StrUtil.isEmpty(parentCode)){
      return dictRegionUtils.dictRegionService.listDictRegion(level);
    }else{
      return dictRegionUtils.dictRegionService.listDictRegion(level, parentCode);
    }
  }

  public static Map<String, DictRegion> getRegionMap(String level, String parentCode) {
    List<DictRegion> entries = getRegionList(level, parentCode);
    Map<String, DictRegion> regionMap = entries.stream().collect(Collectors.toMap(DictRegion::getCode, t -> t));
    return regionMap;
  }

  public static DictRegion getRegion(String level, String code) {
    Map<String, DictRegion> regionMap = getRegionMap(level, null);
    if (regionMap != null && regionMap.containsKey(code)) {
      return regionMap.get(code);
    }
    return null;
  }

  public static String getRegionName(String level, String code) {
    Map<String, DictRegion> regionMap = getRegionMap(level, null);
    if (regionMap != null && regionMap.containsKey(code)) {
      return regionMap.get(code).getName();
    }
    return "";
  }

  public static String getRegionCode(String level, String name) {
    Map<String, DictRegion> regionMap = getRegionMap(level, null);
    if (regionMap != null) {
      for (DictRegion dictRegion : regionMap.values()) {
        if (dictRegion.getName().equals(name)) {
          return dictRegion.getCode();
        }
      }
    }
    return "";
  }

}
