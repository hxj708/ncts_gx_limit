package com.haoyu.framework.modules.file.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoyu.framework.model.PageModel;
import com.haoyu.framework.modules.file.entity.FileResource;

import java.util.List;

public class FileResourceModelUtil {

  /**
   * 转化单个实体
   * @param fileResource
   * @param targetClass
   * @return
   */
  public static Object getModel(FileResource fileResource, Class targetClass){
    Object targetEntity = null;
    try {
      targetEntity = targetClass.newInstance();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    BeanUtil.copyProperties(fileResource, targetEntity);
    return targetEntity;
  }

  /**
   * 转化带分页数据的实体列表
   * @param page
   * @param targetClass
   * @return
   */
  public static PageModel getPageModel(IPage<FileResource> page, Class targetClass){
    List<FileResource> records = page.getRecords();
    PageModel pageModel = new PageModel();
    pageModel.setPageObj(page);
    for (FileResource fileResource : records) {
      Object object = getModel(fileResource, targetClass);
      pageModel.getRecords().add(object);
    }
    return pageModel;
  }

  /**
   * 转化带分页数据的实体列表
   * @param fileResource
   * @param targetClass
   * @return
   */
  public static List<Object> getModelList(List<FileResource> fileResource, Class targetClass){
    List<Object> list = CollectionUtil.newArrayList();
    for (FileResource workshop : fileResource) {
      Object object = getModel(workshop, targetClass);
      list.add(object);
    }
    return list;
  }

}
