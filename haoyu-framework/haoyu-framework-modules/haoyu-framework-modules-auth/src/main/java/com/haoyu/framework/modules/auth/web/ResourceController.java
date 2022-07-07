package com.haoyu.framework.modules.auth.web;

import cn.hutool.core.util.StrUtil;
import com.haoyu.framework.core.base.BaseConstants;
import com.haoyu.framework.core.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haoyu.framework.modules.auth.service.ResourceService;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.haoyu.framework.modules.auth.entity.AuthVO;

import com.haoyu.framework.core.base.BaseController;

/**
 * <p>
 * 权限-资源 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@RestController("resourceController")
@RequestMapping("/api/v1/auth/resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    /**
    * 分页查询列表
    * 传参方式详见PageController注释
    * @return
    */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
    return R.success().setData(resourceService.pageByMap(super.getPage(true), super.getParam()));
    }

    /**
    * 根据条件查询列表
    * 传参方式详见PageController注释
    * @return
    */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
    return R.success().setData(resourceService.listByMap(super.getPage(false),super.getParam()));
    }

    /**
    * 根据ID查询对象
    * @param id
    * @return
    */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
        Resource entity = null;
        if(StrUtil.equals(id, BaseConstants.ID_EMPTY_OBJECT)){
            entity = new Resource();
        }
        else{
            entity = resourceService.getById(id);
        }
        return R.success().setData(entity);
    }
    
    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody Resource entity) {
        return resourceService.create(entity);
    }
    
    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody Resource entity) {
        return resourceService.update(entity);
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
    public R updateBatch(AuthVO vo) {
        return resourceService.update(vo.getResource(), super.getParam());
    }
    
    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return resourceService.deleteLogic(id);
    }
}
