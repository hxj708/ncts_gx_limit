package com.haoyu.framework.core.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author shibo
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    T selectByEntityId(Serializable id);

    <E extends IPage<T>> E selectByMap(E page, @Param(Constants.COLUMN_MAP) Map<String, Object> map);

    @Override
    List<T> selectByMap(@Param(Constants.COLUMN_MAP) Map<String, Object> map);
}
