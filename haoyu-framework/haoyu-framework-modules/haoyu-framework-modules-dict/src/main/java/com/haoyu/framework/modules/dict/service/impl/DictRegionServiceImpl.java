package com.haoyu.framework.modules.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.mapper.DictRegionMapper;
import com.haoyu.framework.modules.dict.service.DictRegionService;
import com.haoyu.framework.modules.dict.utils.DictWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础-行政区域 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("dictRegionService")
public class DictRegionServiceImpl extends BaseServiceImpl<DictRegionMapper,DictRegion> implements DictRegionService {

    @Autowired
    private DictRegionMapper dictRegionMapper;

    @Override
    @Cacheable(value = "region", key = "#level.concat(':000000')")
    public List<DictRegion> listDictRegion(String level) {
        return this.listDictRegion(level, null);
    }

    @Override
    @Cacheable(value = "region", key = "#level.concat(':').concat(#parentCode)")
    public List<DictRegion> listDictRegion(String level, String parentCode){
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("level", level);
        map.put("parentCode", parentCode);
        Page page = new Page<>();
        page.setSize(Integer.MAX_VALUE);
        List<OrderItem> orderItems = CollectionUtil.newArrayList();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("CODE");
        orderItem.setAsc(true);
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        IPage<DictRegion> entries = super.pageByMap(page, map);
        return entries.getRecords();
    }

    @Override
    public R<DictRegion> create(DictRegion entity) {
        return super.create(entity);
    }

    @Override
    public R<DictRegion> update(DictRegion entity) {
        return super.update(entity);
    }

    @Override
    public R<DictRegion> update(DictRegion entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<DictRegion> wrapper = DictWrapperUtils.getDictRegionUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R deleteLogic(@NotNull String id) {
        List<String> ids = Arrays.asList(StrUtil.split(id, StrUtil.COMMA));
        return this.deleteLogic(ids);
    }

    @Override
        public R deleteLogic(@NotEmpty List<String> ids) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("ids", ids);
        return this.deleteLogic(map);
    }

    @Override
    public R deleteLogic(Map<String, Object> map) {
        if (MapUtil.isEmpty(map)) {
            return R.fail("map is empty");
        }
        DictRegion entity = new DictRegion();
        entity.setIsDeleted(DictRegion.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<DictRegion> wrapper = DictWrapperUtils.getDictRegionUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<DictRegion> wrapper = DictWrapperUtils.getDictRegionQueryWrapper(map);
        return super.count(wrapper);
    }

}
