package ${package.Controller};

import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.core.base.BaseConstants;
import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.Entity}.${entity};
import ${package.Entity}.${package.ModuleName?cap_first}VO;

<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController("${table.controllerName?uncap_first}")
<#else>
@Controller("${table.controllerName?uncap_first}")
</#if>
@RequestMapping("/api/v1<#if package.ModuleName??>/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle??>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Autowired
    private ${table.serviceName} ${table.serviceName?uncap_first};

    /**
    * 分页查询列表
    * 传参方式详见BaseController注释
    * @return
    */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
        return R.success().setData(${table.serviceName?uncap_first}.pageByMap(super.getPage(true), super.getParam()));
    }

    /**
    * 根据条件查询列表
    * 传参方式详见BaseController注释
    * @return
    */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
        return R.success().setData(${table.serviceName?uncap_first}.listByMap(super.getPage(false), super.getParam()));
    }

    /**
    * 根据条件查询列表(有缓存)
    * 传参方式详见BaseController注释
    * @return
    */
    @GetMapping("listCache")
    @ApiOperation(value = "根据条件查询列表(有缓存)", notes = "根据条件查询列表(有缓存)", produces = "application/json")
    public R listCache() {
        return R.success().setData(${table.serviceName?uncap_first}.listByMap(super.getPage(false), super.getParam(), true));
    }

    /**
    * 根据ID查询对象
    * @param id
    * @return
    */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
        ${entity} entity = null;
        if(StrUtil.equals(id, BaseConstants.ID_EMPTY_OBJECT)){
            entity = new ${entity}();
        }
        else{
            entity = ${table.serviceName?uncap_first}.getById(id);
        }
        return R.success().setData(entity);
    }

    /**
    * 根据条件查询唯一对象
    * @return
    */
    @GetMapping("getOne")
    @ApiOperation(value = "根据条件查询唯一对象", notes = "根据条件查询唯一对象", produces = "application/json")
    public R getOne() {
        return R.success().setData(${table.serviceName?uncap_first}.getOne(super.getParam()));
    }

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody ${entity} entity) {
        return ${table.serviceName?uncap_first}.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody ${entity} entity) {
        return ${table.serviceName?uncap_first}.update(entity);
    }

    /**
     * 传参方式
     * set的字段前带上模块vo名. 如：entity.title
     * where的条件可直接传参数名，并在service实现类中手动补充wrapper
     * @param vo
     * @return
     */
    @PutMapping(value = "batch")
    @ApiOperation(value = "批量更新对象记录", notes = "批量更新对象记录", produces = "application/json")
    public R updateBatch(${package.ModuleName?cap_first}VO vo) {
        return ${table.serviceName?uncap_first}.update(vo.get${entity}(), super.getParam());
    }

    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return ${table.serviceName?uncap_first}.deleteLogic(id);
    }
}
</#if>
