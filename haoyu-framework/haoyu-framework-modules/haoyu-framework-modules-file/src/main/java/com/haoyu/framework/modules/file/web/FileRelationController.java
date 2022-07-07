package com.haoyu.framework.modules.file.web;

import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.entity.FileVO;
import com.haoyu.framework.modules.file.service.FileRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文件关系表 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@RestController("fileRelationController")
@RequestMapping("/api/v1/file/fileRelation")
public class FileRelationController extends BaseController {

    @Autowired
    private FileRelationService fileRelationService;

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody FileRelation entity) {
        return fileRelationService.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody FileRelation entity) {
        return fileRelationService.update(entity);
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
        return fileRelationService.update(vo.getFileRelation(), super.getParam());
    }

    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return fileRelationService.deleteLogic(id);
    }
}
