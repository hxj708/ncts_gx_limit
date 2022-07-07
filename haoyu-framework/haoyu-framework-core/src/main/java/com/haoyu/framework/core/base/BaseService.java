package com.haoyu.framework.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author shibo
 */
public interface BaseService<T extends BaseEntity, M extends BaseMapper<T>> extends IService<T> {

    /**
     * 用于返回复杂结果的单条记录查询
     *
     * @param id
     * @return
     */
    T getByEntityId(Serializable id);

    /**
     * 根据条件查询唯一对象
     *
     * @param map
     * @return
     */
    T getOne(Map<String, Object> map);

    /**
     * 自定义查询，带分页
     * @param page
     * @param map
     * @param <E>
     * @return
     */
    <E extends IPage<T>> E pageByMap(E page, Map<String, Object> map);

    /**
     * 自定义查询，不分页
     * @param page
     * @param map
     * @return
     */
    List<T> listByMap(IPage<T> page, Map<String, Object> map);

    /**
     * 自定义查询，带分页， 有缓存
     * @param page
     * @param map
     * @param isCache 是否需要缓存
     * @return
     */
    List<T> listByMap(IPage<T> page, Map<String, Object> map, boolean isCache);

    R<T> create(T entity);

    /**
     * 单条update带乐观锁
     *
     * @param entity 必须有ID和VERSION，否则update失败
     * @return
     */
    R<T> update(T entity);

    /**
     * 批量update绕过乐观锁
     *
     * @param entity set字段
     * @param map    where条件，需手动在各模块的实现类中补充wrapper
     * @return
     */
    R<T> update(T entity, Map<String, Object> map);

    R deleteLogic(String id);

    R deleteLogic(@NotEmpty List<String> ids);

    R deleteLogic(Map<String, Object> map);

    int count(Map<String, Object> map);

    Map<String, T> mapById(Map<String, Object> map);
}
