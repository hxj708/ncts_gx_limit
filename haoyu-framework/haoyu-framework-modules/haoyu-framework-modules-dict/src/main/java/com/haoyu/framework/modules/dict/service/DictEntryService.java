package com.haoyu.framework.modules.dict.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.mapper.DictEntryMapper;

import java.util.List;

/**
 * <p>
 * 基础-字典项 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
public interface DictEntryService extends BaseService<DictEntry,DictEntryMapper> {

  List<DictEntry> listDictEntry(String dictTypeCode);

  List<DictEntry> listDictEntryByParentValue(String dictTypeCode,String parentValue);


}
