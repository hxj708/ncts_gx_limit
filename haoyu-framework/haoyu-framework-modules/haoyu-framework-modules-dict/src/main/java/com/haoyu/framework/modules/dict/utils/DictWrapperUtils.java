package com.haoyu.framework.modules.dict.utils;

import cn.hutool.core.map.MapUtil;
import com.haoyu.framework.modules.dict.entity.DictEntry;
import com.haoyu.framework.modules.dict.entity.DictRegion;
import com.haoyu.framework.modules.dict.entity.DictType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工具类：用于Map转为MyBatisPlus的Wrapper
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
public class DictWrapperUtils {

	/**
     * DictEntry
     */
	public static LambdaQueryWrapper<DictEntry> getDictEntryQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<DictEntry> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictEntry::getId, ids);
		}
		return wrapper;
	}
	/**
	 * DictEntry
	 */
	public static LambdaUpdateWrapper<DictEntry> getDictEntryUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<DictEntry> wrapper = Wrappers.lambdaUpdate();
		if (MapUtil.isEmpty(map)) {
			throw new IllegalArgumentException("map is empty");
		}
		boolean hasKey = false;
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictEntry::getId, ids);
			hasKey = true;
		}
		if (!hasKey){
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * DictType
     */
	public static LambdaQueryWrapper<DictType> getDictTypeQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<DictType> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictType::getId, ids);
		}
		return wrapper;
	}
	/**
	 * DictType
	 */
	public static LambdaUpdateWrapper<DictType> getDictTypeUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<DictType> wrapper = Wrappers.lambdaUpdate();
		if (MapUtil.isEmpty(map)) {
			throw new IllegalArgumentException("map is empty");
		}
		boolean hasKey = false;
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictType::getId, ids);
			hasKey = true;
		}
		if (!hasKey){
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
	 * DictRegion
	 */
	public static LambdaQueryWrapper<DictRegion> getDictRegionQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<DictRegion> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
			List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictRegion::getId, ids);
		}
		return wrapper;
	}
	/**
	 * DictRegion
	 */
	public static LambdaUpdateWrapper<DictRegion> getDictRegionUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<DictRegion> wrapper = Wrappers.lambdaUpdate();
		if (MapUtil.isEmpty(map)) {
			throw new IllegalArgumentException("map is empty");
		}
		boolean hasKey = false;
		if (map.containsKey("ids")) {
			List<String> ids = (List<String>) map.get("ids");
			wrapper.in(DictRegion::getId, ids);
			hasKey = true;
		}
		if (!hasKey){
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}


}
