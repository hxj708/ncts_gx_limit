package com.haoyu.framework.modules.file.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.model.PageModel;
import com.haoyu.framework.modules.file.entity.FileResourceModel;
import com.haoyu.framework.modules.file.utils.FileResourceModelUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.haoyu.framework.modules.file.service.FileResourceService;
import com.haoyu.framework.modules.file.entity.FileResource;
import com.haoyu.framework.modules.file.entity.FileVO;

/**
 * <p>
 * 文件资源表 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@RestController("fileResourceController")
@RequestMapping("/api/v1/file/fileResource")
public class FileResourceController extends BaseController {

    @Autowired
    private FileResourceService fileResourceService;

    /**
     * 分页查询列表
     * 传参方式详见PageController注释
     * @return
     */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
        IPage iPage = fileResourceService.pageByMap(super.getPage(true), super.getParam());
        PageModel pageModel = FileResourceModelUtil.getPageModel(iPage, FileResourceModel.class);
        return R.success().setData(pageModel);
    }

    /**
     * 根据条件查询列表
     * 传参方式详见PageController注释
     * @return
     */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
        return R.success().setData(fileResourceService.listByMap(super.getPage(false), super.getParam()));
    }

    /**
     * 根据ID查询对象
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
        return R.success().setData(fileResourceService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody FileResource entity) {
        return fileResourceService.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody FileResource entity) {
        return fileResourceService.update(entity);
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
    public R updateBatch(FileVO vo) {
        return fileResourceService.update(vo.getFileResource(), super.getParam());
    }

    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return fileResourceService.deleteLogic(id);
    }
}
