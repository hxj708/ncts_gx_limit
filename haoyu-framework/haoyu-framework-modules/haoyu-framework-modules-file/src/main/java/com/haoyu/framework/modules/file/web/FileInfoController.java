package com.haoyu.framework.modules.file.web;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileInfo;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.entity.FileVO;
import com.haoyu.framework.modules.file.event.CompleteResourceDownLoadEvent;
import com.haoyu.framework.modules.file.service.FileInfoService;
import com.haoyu.framework.modules.file.service.FileRelationService;
import com.haoyu.framework.modules.file.utils.FileUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@RestController("fileInfoController")
@RequestMapping(value = {"/api/v1/file/fileInfo","/cms/api/v1/file/fileInfo"})
public class FileInfoController extends BaseController {

    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private FileRelationService fileRelationService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Value("${file.remote-dir}")
    private String fileRemoteDir;
    @Value("${file.temp-dir}")
    private String fileTempDir;
    @Value("${file.remote-http-domain}")
    private String remoteHttpDomain;

    /**
     * 分页查询列表
     * 传参方式详见PageController注释
     * @return
     */
    @GetMapping("page")
    @ApiOperation(value = "分页查询列表", notes = "分页查询列表", produces = "application/json")
    public R page() {
        return R.success().setData(fileInfoService.pageByMap(super.getPage(true), super.getParam()));
    }

    /**
     * 根据条件查询列表
     * 传参方式详见PageController注释
     * @return
     */
    @GetMapping
    @ApiOperation(value = "根据条件查询列表", notes = "根据条件查询列表", produces = "application/json")
    public R list() {
        return R.success().setData(fileInfoService.listByMap(super.getPage(false), super.getParam()));
    }

    /**
     * 根据ID查询对象
     * @param id
     * @return
     */
    @GetMapping("{id}")
    @ApiOperation(value = "根据ID查询对象", notes = "根据ID查询对象", produces = "application/json")
    public R get(@PathVariable String id) {
        return R.success().setData(fileInfoService.getById(id));
    }

    @PostMapping
    @ApiOperation(value = "创建对象记录", notes = "创建对象记录", produces = "application/json")
        public R create(@RequestBody FileInfo entity) {
        return fileInfoService.create(entity);
    }

    @PutMapping(value = "{id}")
    @ApiOperation(value = "更新对象记录", notes = "更新对象记录", produces = "application/json")
        public R update(@RequestBody FileInfo entity) {
        return fileInfoService.update(entity);
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
        return fileInfoService.update(vo.getFileInfo(), super.getParam());
    }

    /**
    * 多个ID用，分隔
    * @param id
    * @return
    */
    @DeleteMapping(value = "{id}")
    @ApiOperation(value = "根据主键删除对象", notes = "根据主键删除对象", produces = "application/json")
    public R deleteLogic(@PathVariable String id) {
        return fileInfoService.deleteLogic(id);
    }

    @GetMapping(value = "preivew")
    @ApiOperation(value = "获取预览文件url", notes = "获取预览文件url", produces = "application/json")
    public R preview(String url) {
        return R.success().setData(FileUtils.getPreviewUrl(url));
    }

    @GetMapping(value = "download/{id}")
    @ApiOperation(value = "下载文件", notes = "下载文件", produces = "application/json")
    public void download(@PathVariable String id, HttpServletResponse response) {
        try {
            response.setContentType("multipart/form-data");
            if (StrUtil.isNotEmpty(id)) {
                FileInfo fileInfo = fileInfoService.getById(id);
                response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(fileInfo.getFileName().getBytes("UTF-8"), "iso8859-1"));
                OutputStream os = response.getOutputStream();
                File file = new File(fileRemoteDir + fileInfo.getUrl());
                byte[] b = FileUtil.readBytes(file);
                if (ArrayUtil.isNotEmpty(b)) {
                    os.write(b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "download/byUrl")
    @ApiOperation(value = "根据url下载文件", notes = "根据url下载文件", produces = "application/json")
    public void download(@RequestBody FileInfo entity, HttpServletResponse response) {
        try {
            String url = entity.getUrl();
            if (StrUtil.isNotEmpty(url)) {
                if (!url.contains("http") && !url.contains("https")){
                    url = remoteHttpDomain + url;
                }
                response.setContentType("multipart/form-data");
                if(StrUtil.isNotEmpty(entity.getFileName())){
                    response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(entity.getFileName().getBytes("UTF-8"), "iso8859-1"));
                }
                OutputStream os = response.getOutputStream();
                byte[] b = HttpUtil.downloadBytes(url);
                if (ArrayUtil.isNotEmpty(b)) {
                    os.write(b);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "downloadResource/byUrl")
    @ApiOperation(value = "根据url下载文件", notes = "根据url下载文件", produces = "application/json")
    public void downloadResource(@RequestBody FileInfo entity, HttpServletResponse response) {
        Boolean isComplete = true;
        try {
            String url = entity.getUrl();
            if (StrUtil.isNotEmpty(url)) {
                if (!url.contains("http") && !url.contains("https")){
                    url = remoteHttpDomain + url;
                }
                response.setContentType("multipart/form-data");
                if(StrUtil.isNotEmpty(entity.getFileName())){
                    response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String(entity.getFileName().getBytes("UTF-8"), "iso8859-1"));
                }
                OutputStream os = response.getOutputStream();
                byte[] b = HttpUtil.downloadBytes(url);
                if (ArrayUtil.isNotEmpty(b)) {
                    os.write(b);
                }
            }
        } catch (Exception e) {
            isComplete = false;
            e.printStackTrace();
        } finally {
            if (isComplete){
                applicationEventPublisher.publishEvent(new CompleteResourceDownLoadEvent(entity.getResourceId()));
                //下载数+1
                FileRelation fileRelation = fileRelationService.getOne(new LambdaQueryWrapper<FileRelation>()
                        .eq(FileRelation::getRelationId, entity.getResourceId())
                        .eq(FileRelation::getIsDeleted, "N")
                );
                if(ObjectUtil.isNotNull(fileRelation)){
                    fileRelation.setDownloadNum(ObjectUtil.isNotNull(fileRelation.getDownloadNum())?fileRelation.getDownloadNum().add(new BigDecimal(1)):new BigDecimal(1));
                    fileRelationService.update(fileRelation);
                }

            }
        }
    }
}
