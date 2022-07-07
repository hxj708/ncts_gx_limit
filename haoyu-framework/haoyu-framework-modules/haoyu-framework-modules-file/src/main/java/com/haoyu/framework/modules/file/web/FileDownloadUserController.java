package com.haoyu.framework.modules.file.web;

import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileDownloadUser;
import com.haoyu.framework.modules.file.entity.FileVO;
import com.haoyu.framework.modules.file.service.FileDownloadUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 文件下载表 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@RestController("fileDownloadUserController")
@RequestMapping("/api/v1/file/fileDownloadUser")
public class FileDownloadUserController extends BaseController {

    @Autowired
    private FileDownloadUserService fileDownloadUserService;

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody FileDownloadUser entity) {
        return fileDownloadUserService.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody FileDownloadUser entity) {
        return fileDownloadUserService.update(entity);
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
        return fileDownloadUserService.update(vo.getFileDownloadUser(), super.getParam());
    }

    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return fileDownloadUserService.deleteLogic(id);
    }
}
