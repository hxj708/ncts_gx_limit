package com.haoyu.framework.modules.dict.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.mapper.DictRegionMapper;

import java.util.List;

/**
 * <p>
 * 基础-行政区域 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface DictRegionService extends BaseService<DictRegion,DictRegionMapper> {

  List<DictRegion> listDictRegion(String level);

  List<DictRegion> listDictRegion(String level, String parentCode);

}
