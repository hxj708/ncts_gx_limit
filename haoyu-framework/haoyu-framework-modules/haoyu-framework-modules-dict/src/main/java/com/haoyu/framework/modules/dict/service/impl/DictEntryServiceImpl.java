package com.haoyu.framework.modules.dict.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.mapper.DictEntryMapper;
import com.haoyu.framework.modules.dict.service.DictEntryService;
import com.haoyu.framework.modules.dict.utils.DictWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础-字典项 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@Service("dictEntryService")
public class DictEntryServiceImpl extends BaseServiceImpl<DictEntryMapper,DictEntry> implements DictEntryService {

    @Autowired
    private DictEntryMapper dictEntryMapper;

    @Override
    @Cacheable(value = "dict", key = "#dictTypeCode")
    public List<DictEntry> listDictEntry(String dictTypeCode){
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("dictTypeCode", dictTypeCode);
        Page page = new Page<>();
        page.setSize(Integer.MAX_VALUE);
        List<OrderItem> orderItems = CollectionUtil.newArrayList();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("SORT_NO");
        orderItem.setAsc(true);
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        IPage<DictEntry> entries = super.pageByMap(page, map);
        return entries.getRecords();
    }

    @Override
    @Cacheable(value = "parent", key = "#dictTypeCode.concat(':').concat(#parentValue)")
    public List<DictEntry> listDictEntryByParentValue(String dictTypeCode, String parentValue) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("dictTypeCode", dictTypeCode);
        map.put("parentValue",parentValue);
        Page page = new Page<>();
        page.setSize(Integer.MAX_VALUE);
        List<OrderItem> orderItems = CollectionUtil.newArrayList();
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn("SORT_NO");
        orderItem.setAsc(true);
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        IPage<DictEntry> entries = super.pageByMap(page, map);
        return entries.getRecords();
    }

    @Override
    public R<DictEntry> create(DictEntry entity) {
        return super.create(entity);
    }

    @Override
    public R<DictEntry> update(DictEntry entity) {
        return super.update(entity);
    }

    @Override
    public R<DictEntry> update(DictEntry entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<DictEntry> wrapper = DictWrapperUtils.getDictEntryUpdateWrapper(map);
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
        DictEntry entity = new DictEntry();
        entity.setIsDeleted(DictEntry.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<DictEntry> wrapper = DictWrapperUtils.getDictEntryUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<DictEntry> wrapper = DictWrapperUtils.getDictEntryQueryWrapper(map);
        return super.count(wrapper);
    }

}
