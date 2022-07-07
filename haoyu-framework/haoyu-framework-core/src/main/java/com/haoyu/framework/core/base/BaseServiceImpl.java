package com.haoyu.framework.core.base;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author shibo
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T, M> {

  /**
   * 用于返回复杂结果的单条记录查询
   *
   * @param id
   * @return
   */
  @Override
  public T getByEntityId(Serializable id) {
    return baseMapper.selectByEntityId(id);
  }


  /**
   * 自定义查询，带分页
   * @param page
   * @param map
   * @param <E>
   * @return
   */
  @Override
  public <E extends IPage<T>> E pageByMap(E page, Map<String, Object> map) {
    map.put("nowDate",new Date());
    return baseMapper.selectByMap(page, map);
  }

  /**
   * 自定义查询，不分页
   * @param page
   * @param map
   * @return
   */
  @Override
  public List<T> listByMap(IPage<T> page, Map<String, Object> map) {
    map.put("nowDate",new Date());
    IPage<T> tPage = baseMapper.selectByMap(page, map);
    return tPage.getRecords();
  }

  @Override
  public R<T> create(T entity) {
    entity.setVersion(1);
    return super.save(entity) ? R.success() : R.fail();
  }

  @Override
  public R<T> update(T entity) {
    return super.updateById(entity) ? R.success() : R.fail();
  }

  @Override
  public R<T> update(T entity, Map<String, Object> map) {
    return null;
  }

  @Override
  public R deleteLogic(String id) {
    return null;
  }

  @Override
  public R deleteLogic(@NotEmpty List<String> ids) {
    return null;
  }

  @Override
  public R deleteLogic(Map<String, Object> map) {
    return null;
  }

  @Override
  public int count(Map<String, Object> map) {
    return 0;
  }

  @Override
  public Map<String, T> mapById(Map<String, Object> map) {
    List<T> list = this.listByMap(map);
    Map<String, T> tMap = MapUtil.newHashMap();
    if (CollectionUtil.isNotEmpty(list)) {
      tMap = list.stream().collect(Collectors.toMap(T::getId, T -> T));
    }
    return tMap;
  }

  @Override
  public List<T> listByMap(IPage<T> page, Map<String, Object> map, boolean isCache){
    return null;
  }

  @Override
  public T getOne(Map<String, Object> map){
    List<T> list = this.listByMap(map);
    if(CollectionUtil.isNotEmpty(list)){
      return list.get(0);
    }
    return null;
  }

}
