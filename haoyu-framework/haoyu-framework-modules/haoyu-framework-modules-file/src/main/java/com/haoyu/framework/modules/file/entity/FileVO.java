package com.haoyu.framework.modules.file.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * com.haoyu.framework.modules.file.entity
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Data
public class FileVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private FileDownloadUser fileDownloadUser = new FileDownloadUser();
    private FileInfo fileInfo = new FileInfo();
    private FileRelation fileRelation = new FileRelation();
    private FileResource fileResource = new FileResource();
}
