package com.haoyu.framework.modules.auth.web;

import cn.hutool.core.map.MapUtil;
import com.haoyu.framework.core.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haoyu.framework.modules.auth.service.RoleResourceService;
import com.haoyu.framework.modules.auth.entity.RoleResource;
import com.haoyu.framework.modules.auth.entity.AuthVO;

import com.haoyu.framework.core.base.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限-角色资源关联 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@RestController("roleResourceController")
@RequestMapping("/api/v1/auth/roleResource")
public class RoleResourceController extends BaseController {

    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 分页查询列表
     * 传参方式详见PageController注释
     *
     * @return
     */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
        return R.success().setData(roleResourceService.pageByMap(super.getPage(true), super.getParam()));
    }

    /**
     * 根据条件查询列表
     * 传参方式详见PageController注释
     *
     * @return
     */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
        return R.success().setData(roleResourceService.listByMap(super.getPage(false), super.getParam()));
    }

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
        return R.success().setData(roleResourceService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
    public R create(@RequestBody RoleResource entity) {
        return roleResourceService.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
    public R update(@RequestBody RoleResource entity) {
        return roleResourceService.update(entity);
    }

    /**
     * 传参方式
     * set的字段前带上模块vo名. 如：entity.title
     * where的条件可直接传参数名，并在service实现类中手动补充wrapper
     *
     * @param vo
     * @return
     */
    @PutMapping(value = "batch")
    @ApiOperation(value = "批量更新对象记录", notes = "批量更新对象记录", produces = "application/json")
    public R updateBatch(AuthVO vo) {
        return roleResourceService.update(vo.getRoleResource(), super.getParam());
    }

    /**
     * 多个ID用，分隔
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return roleResourceService.deleteLogic(id);
    }

    /**
     * 根据ROLE ID查询
     *
     * @param roleId
     * @return
     */
    @GetMapping("listByRoleId/{roleId}")
    @ApiOperation(value = "根据ROLE ID查询对象", notes = "根据ROLE ID查询对象", produces = "application/json")
    public R listByRoleId(@PathVariable String roleId) {
        Map<String, Object> param = MapUtil.newHashMap();
        param.put("roleId", roleId);
        List<RoleResource> roleResourceList = roleResourceService.listByMap(param);
        return R.success().setData(roleResourceList);
    }

    /**
     * 更新角色对应的权限
     * 1.删除原角色权限
     * 2.新增新角色权限
     *
     * @return
     */
    @PostMapping(value = "updateByRoleId/{roleId}")
    @ApiOperation(value = "更新角色对应权限", notes = "批量更新对象记录", produces = "application/json")
    public R updateByRoleId(@PathVariable String roleId, @RequestBody Map<String,Object> param) {
        String[] resourceIds = MapUtil.get(param,"resourceIds",String[].class);
        return roleResourceService.updateByRoleId(roleId, resourceIds);
    }
}
