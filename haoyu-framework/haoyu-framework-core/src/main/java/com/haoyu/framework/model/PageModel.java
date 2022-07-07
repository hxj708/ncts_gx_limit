package com.haoyu.framework.model;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PageModel {

  private List<Object> records = Lists.newArrayList();

//  private Map<String, Object> pageObj = null;

  private long current;

  private long total;

  private long size;


  public void setRecords(List<? extends Object> records) {
    this.records = (List<Object>) records;
  }

  public void setPageObj(Object page){
//    if (page instanceof IPage){
//      pageObj.put("current", ((IPage<?>) page).getCurrent());
//      pageObj.put("size",((IPage<?>) page).getSize());
//      pageObj.put("total",((IPage<?>) page).getTotal());
//    } else if (page instanceof Map){
//      pageObj.put("current", ((Map<?, ?>) page).get("current"));
//      pageObj.put("size",  ((Map<?, ?>) page).get("size"));
//      pageObj.put("total",  ((Map<?, ?>) page).get("total"));
//    }

    if (page instanceof IPage){
      this.current =  ((IPage<?>) page).getCurrent();
      this.size =  ((IPage<?>) page).getSize();
      this.total =  ((IPage<?>) page).getTotal();
    } else if (page instanceof Map){
      this.current =  (Long) ((Map<?, ?>) page).get("current");
      this.size =  (Long)((Map<?, ?>) page).get("size");
      this.total = (Long)((Map<?, ?>) page).get("total");
    }
  }




}
