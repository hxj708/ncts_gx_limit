package com.haoyu.framework.modules.dict.service.impl;

import com.haoyu.framework.modules.dict.entity.DictType;
import com.haoyu.framework.modules.dict.mapper.DictTypeMapper;
import com.haoyu.framework.modules.dict.service.DictTypeService;
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
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础-字典 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@Service("dictTypeService")
public class DictTypeServiceImpl extends BaseServiceImpl<DictTypeMapper,DictType> implements DictTypeService {

    @Autowired
    private DictTypeMapper dictTypeMapper;

    @Override
    public R<DictType> create(DictType entity) {
        return super.create(entity);
    }

    @Override
    public R<DictType> update(DictType entity) {
        return super.update(entity);
    }

    @Override
    public R<DictType> update(DictType entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<DictType> wrapper = DictWrapperUtils.getDictTypeUpdateWrapper(map);
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
        DictType entity = new DictType();
        entity.setIsDeleted(DictType.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<DictType> wrapper = DictWrapperUtils.getDictTypeUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<DictType> wrapper = DictWrapperUtils.getDictTypeQueryWrapper(map);
        return super.count(wrapper);
    }

}
