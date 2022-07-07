package com.haoyu.framework.modules.dict.utils;

import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.service.DictEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典工具类
 *
 * @author shisibo
 */
@Component
public class DictUtils {

  private static DictUtils dictUtils;
  @Autowired
  private DictEntryService dictEntryService;

  @PostConstruct
  public void init() {
    dictUtils = this;
    dictUtils.dictEntryService = this.dictEntryService;
  }

  public static List<DictEntry> getEntryList(String dictTypeCode) {
    return dictUtils.dictEntryService.listDictEntry(dictTypeCode);
  }

  public static List<DictEntry> getEntryListByParentValue(String dictTypeCode,String parentValue) {
    return dictUtils.dictEntryService.listDictEntryByParentValue(dictTypeCode,parentValue);
  }

  public static Map<String, DictEntry> getEntryMap(String dictTypeCode) {
    List<DictEntry> entries = getEntryList(dictTypeCode);
    Map<String, DictEntry> entryMap = entries.stream().collect(Collectors.toMap(DictEntry::getDictValue, t -> t));
    return entryMap;
  }

  public static DictEntry getEntry(String dictTypeCode, String dictValue) {
    Map<String, DictEntry> entryMap = getEntryMap(dictTypeCode);
    if (entryMap != null && entryMap.containsKey(dictValue)) {
      return entryMap.get(dictValue);
    }
    return null;
  }

  public static String getEntryName(String dictTypeCode, String dictValue) {
    Map<String, DictEntry> entryMap = getEntryMap(dictTypeCode);
    if (entryMap != null && entryMap.containsKey(dictValue)) {
      return entryMap.get(dictValue).getDictName();
    }
    return "";
  }

  public static String getEntryValue(String dictTypeCode, String dictEntryName) {
    Map<String, DictEntry> entryMap = getEntryMap(dictTypeCode);
    if (entryMap != null) {
      for (DictEntry dictEntry : entryMap.values()) {
        if (dictEntry.getDictName().equals(dictEntryName)) {
          return dictEntry.getDictValue();
        }
      }
    }
    return "";
  }

}
