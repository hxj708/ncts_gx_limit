package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${cfg.packageUtils}.${package.ModuleName?cap_first}WrapperUtils;
import ${superServiceImplClassPackage};

import com.haoyu.framework.modules.cache.utils.RedisUtils;
import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service("${table.serviceName?uncap_first}")
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName},${entity}> implements ${table.serviceName} {

    @Autowired
    private ${table.mapperName} ${table.mapperName?uncap_first};
    @Value("${r'${spring.cache.redis.key-prefix}'}")
    private String redisKey;

    @Override
    public R<${entity}> create(${entity} entity) {
        return super.create(entity);
    }

    @Override
    public R<${entity}> update(${entity} entity) {
        return super.update(entity);
    }

    @Override
    public R<${entity}> update(${entity} entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<${entity}> wrapper = ${package.ModuleName?cap_first}WrapperUtils.get${entity}UpdateWrapper(map);
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
        ${entity} entity = new ${entity}();
        entity.setIsDeleted(${entity}.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<${entity}> wrapper = ${package.ModuleName?cap_first}WrapperUtils.get${entity}UpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<${entity}> wrapper = ${package.ModuleName?cap_first}WrapperUtils.get${entity}QueryWrapper(map);
        return super.count(wrapper);
    }

    @Override
    public List<${entity}> listByMap(IPage<${entity}> page, Map<String, Object> map, boolean isCache) {
        if(!isCache){
            return super.listByMap(page, map);
        }else{
            //根据业务自行定义
            String key = redisKey + "${entity?uncap_first}:listByMap";
            Object object = RedisUtils.get(key);
            if(object != null){
                return (List<${entity}>)object;
            }else{
                List<${entity}> entities = super.listByMap(page, map);
                RedisUtils.addOrUpdate(key, entities, 1, TimeUnit.HOURS);
                return entities;
            }
        }
    }

}
</#if>
