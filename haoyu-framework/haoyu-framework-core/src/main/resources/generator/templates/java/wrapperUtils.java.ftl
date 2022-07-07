package ${cfg.packageUtils};

<#list config.tableInfoList as entity>
import ${package.Entity}.${entity.entityName};
</#list>

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.map.MapUtil;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工具类：用于Map转为MyBatisPlus的Wrapper
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public class ${package.ModuleName?cap_first}WrapperUtils {

	<#list config.tableInfoList as entity>
	/**
     * ${entity.entityName}
     */
	public static LambdaQueryWrapper<${entity.entityName}> get${entity.entityName}QueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<${entity.entityName}> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(${entity.entityName}::getId, ids);
		}
		return wrapper;
	}
	/**
	 * ${entity.entityName}
	 */
	public static LambdaUpdateWrapper<${entity.entityName}> get${entity.entityName}UpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<${entity.entityName}> wrapper = Wrappers.lambdaUpdate();
		if (MapUtil.isEmpty(map)) {
			throw new IllegalArgumentException("map is empty");
		}
		boolean hasKey = false;
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(${entity.entityName}::getId, ids);
			hasKey = true;
		}
		if (!hasKey){
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	</#list>

}
