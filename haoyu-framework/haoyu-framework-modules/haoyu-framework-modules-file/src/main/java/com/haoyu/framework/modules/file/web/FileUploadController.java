package com.haoyu.framework.modules.file.web;

import com.haoyu.framework.core.base.BaseController;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileInfo;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 文件表 前端控制器
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@RestController("fileController")
@RequestMapping(value = {"/api/v1/file/upload","/cms/api/v1/file/upload"})
public class FileUploadController extends BaseController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传文件到远程服务器
     * @param file
     * @return
     */
    @PostMapping("remote")
    public R uploadFileInfoRemote(MultipartFile file) {
        String relationId = request.getParameter("relationId");
        String type = request.getParameter("type");
        FileInfo fileInfo = new FileInfo();
        FileRelation fileRelation = new FileRelation();
        fileRelation.setType(type);
        fileRelation.setRelationId(relationId);
        fileInfo.getFileRelations().add(fileRelation);
        try {
            return fileUploadService.uploadFile(file.getBytes(), file.getSize(), fileInfo, file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.fail();
    }

    /**
     * 上传文件到临时目录
     * @param file
     * @return
     */
    @PostMapping("temp")
    public R uploadTemp(MultipartFile file) {
        try {
            return fileUploadService.uploadTemp(file.getBytes(),file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.fail();
    }

}
