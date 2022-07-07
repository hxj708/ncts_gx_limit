package com.haoyu.framework.modules.file.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.haoyu.framework.modules.file.entity.FileRelation;
import com.haoyu.framework.modules.file.entity.FileResource;
import com.haoyu.framework.modules.file.mapper.FileResourceMapper;
import com.haoyu.framework.modules.file.service.FileInfoService;
import com.haoyu.framework.modules.file.service.FileRelationService;
import com.haoyu.framework.modules.file.service.FileResourceService;
import com.haoyu.framework.modules.file.service.FileUploadService;
import com.haoyu.framework.modules.file.utils.FileWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 文件资源表 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Service("fileResourceService")
public class FileResourceServiceImpl extends BaseServiceImpl<FileResourceMapper,FileResource> implements FileResourceService {

    @Autowired
    private FileResourceMapper fileResourceMapper;

    @Autowired
    private FileRelationService fileRelationService;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public R<FileResource> create(FileResource entity) {
        R<FileResource> fileResourceR = super.create(entity);
        //保存关系表
        if(fileResourceR.isSuccess() && !StringUtils.isEmpty(entity.getRelationId())){
            fileRelationService.save(new FileRelation(){{
                setFileId(entity.getId());
                setRelationId(entity.getRelationId());
                setType(entity.getType());
                setDownloadNum(BigDecimal.valueOf(0));
            }});
            if( !StringUtils.isEmpty(entity.getParentIds())  && "N".equals(entity.getIsFolder()) ){
                //所有父文件夹文件数+1
                Map<String,Object> map = MapUtil.newHashMap();
                map.put("isIncrease","Y");
                map.put("num",1);
                map.put("ids",entity.getParentIds().split(","));
                fileResourceMapper.updateFileNum(map);
            }
            //添加文件
            if (CollectionUtil.isNotEmpty(entity.getFileInfos())){
                fileUploadService.createFileList(entity.getFileInfos(), entity.getId(), "fileRelation");
            }
            if (entity.getFileInfo() != null){
                fileUploadService.createFile(entity.getFileInfo(), entity.getId(), "fileRelation");
            }
        }
        return fileResourceR;
    }

    @Override
    public R<FileResource> update(FileResource entity) {
        R<FileResource> update = super.update(entity);
        //修改关系表
        if ( update.isSuccess()  && !StringUtils.isEmpty(entity.getRelationId()) ){
            Map<String, Object> map = MapUtil.newHashMap();
            map.put("fileId",entity.getId());
            map.put("relationId",entity.getRelation());
            LambdaQueryWrapper<FileRelation> wrapper = FileWrapperUtils.getFileRelationQueryWrapper(map);
            fileRelationService.update(new FileRelation(){{
                setType(entity.getType());
            }},wrapper);

            if (CollectionUtil.isNotEmpty(entity.getFileInfos())){
                fileUploadService.updateFileList(entity.getFileInfos(), entity.getId(), "fileRelation");
            }
            if (entity.getFileInfo() != null){
                fileUploadService.updateFile(entity.getFileInfo(), entity.getId(), "fileRelation");
            }


        }
        return update;
    }

    @Override
    public R<FileResource> update(FileResource entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<FileResource> wrapper = FileWrapperUtils.getFileResourceUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R deleteLogic(@NotNull String id) {
        //List<String> ids = Arrays.asList(StrUtil.split(id, StrUtil.COMMA));
        Set<FileResource> fileSet = CollectionUtil.newHashSet();
        Set<FileResource> dirSet = CollectionUtil.newHashSet();
        //先查询是不是文件
        FileResource byId = super.getById(id);
        if ("Y".equals(byId.getIsFolder())){
            //获取所有的待删除的文件夹和文件
            Map<String, Set> dirListAndFileList = getDirListAndFileList(new HashMap<>(), id);
            fileSet = dirListAndFileList.get("fileSet");
            dirSet = dirListAndFileList.get("dirSet");
            //别忘了加上自己哦
            dirSet.add(byId);
        }else{
            fileSet.add(byId);
        }


        //文件夹直接删
        R r1 = null,r2 = null;
        if (CollectionUtil.isNotEmpty(dirSet)){
            r1 = this.deleteLogic(dirSet.stream().map( fileResource ->  fileResource.getId() ).collect(Collectors.toList()));
        }
        //文件删除后相关的文件夹数量-1
        if (CollectionUtil.isNotEmpty(fileSet)){
            //获取文件列表
            List<FileResource> fileResourceList = super.listByIds(fileSet.stream().map( fileResource ->fileResource.getId() ).collect(Collectors.toSet()));
            fileResourceList.forEach(fileResource -> {
                //文件删除后相关的文件夹数量-1
                Map<String, Object> map = MapUtil.newHashMap();
                map.put("isIncrease","N");
                map.put("num",1);
                map.put("ids",fileResource.getParentIds().split(","));
                fileResourceMapper.updateFileNum(map);
            });
            r2 = this.deleteLogic(fileSet.stream().map(fileResource -> fileResource.getId()).collect(Collectors.toList()));
        }
        if (( r1 != null && r1.isSuccess()) || (r2 != null && r2.isSuccess())){
            return R.success();
        }else {
            return R.fail("操作异常");
        }
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
        FileResource entity = new FileResource();
        entity.setIsDeleted(FileResource.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<FileResource> wrapper = FileWrapperUtils.getFileResourceUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    /**
     * 获取该文件夹下的文件和文件夹id
     * @param id
     * @return
     */
    private Map<String, Set> getDirListAndFileList(Map<String, Set> map,String id){
        //获取该文件夹下的所有内容
        Map<String, Object> mapQuery = MapUtil.newHashMap();
        mapQuery.put("parentIds", id);
        List<FileResource> list = super.listByMap(mapQuery);
        //文件夹
        Set<FileResource> dirSet = list.stream().filter(fileResource -> fileResource.getIsFolder().equals("Y")).collect(Collectors.toSet());
        //文件
        Set<FileResource> fileSet = list.stream().filter(fileResource -> fileResource.getIsFolder().equals("N")).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(map.get("dirSet"))){
            map.put("dirSet",dirSet);
        }else {
            map.get("dirSet").addAll(dirSet);
        }

        if (CollectionUtil.isEmpty(map.get("fileSet"))){
            map.put("fileSet",fileSet);
        }else {
            map.get("fileSet").addAll(fileSet);
        }


        if (CollectionUtil.isNotEmpty(dirSet)){
            dirSet.forEach( dir -> getDirListAndFileList(map,dir.getId()) );
        }
        return map;

    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<FileResource> wrapper = FileWrapperUtils.getFileResourceQueryWrapper(map);
        return super.count(wrapper);
    }

}
