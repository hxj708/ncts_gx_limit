package com.haoyu.framework.modules.file.service;

import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.file.entity.FileInfo;

import java.util.List;

public interface FileUploadService {

  R uploadFile(byte[] bytes, long size, FileInfo fileInfo, String originalFilename);

  R uploadTemp(byte[] bytes, String originalFilename);

  R createFile(FileInfo fileInfo, String relationId, String relationType);

  R updateFile(FileInfo fileInfo, String relationId, String relationType);

  R createFileList(List<FileInfo> fileInfos, String relationId, String relationType);

  R updateFileList(List<FileInfo> fileInfos, String relationId, String relationType);

}
