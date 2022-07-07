package com.haoyu.framework.modules.auth.web;

import com.haoyu.framework.core.base.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haoyu.framework.modules.auth.service.LoginLogStatService;
import com.haoyu.framework.modules.auth.entity.LoginLogStat;
import com.haoyu.framework.modules.auth.entity.AuthVO;

import com.haoyu.framework.core.base.BaseController;

/**
 * <p>
 * 权限-登陆情况统计 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@RestController("loginLogStatController")
@RequestMapping("/api/v1/auth/loginLogStat")
public class LoginLogStatController extends BaseController {

    @Autowired
    private LoginLogStatService loginLogStatService;

    /**
    * 分页查询列表
    * 传参方式详见PageController注释
    * @return
    */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
    return R.success().setData(loginLogStatService.pageByMap(super.getPage(true), super.getParam()));
    }

    /**
    * 根据条件查询列表
    * 传参方式详见PageController注释
    * @return
    */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
    return R.success().setData(loginLogStatService.listByMap(super.getPage(false),super.getParam()));
    }

    /**
    * 根据ID查询对象
    * @param id
    * @return
    */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
    return R.success().setData(loginLogStatService.getById(id));
    }
    
    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody LoginLogStat entity) {
        return loginLogStatService.create(entity);
    }
    
    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody LoginLogStat entity) {
        return loginLogStatService.update(entity);
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
        return loginLogStatService.update(vo.getLoginLogStat(), super.getParam());
    }
    
    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return loginLogStatService.deleteLogic(id);
    }
}
