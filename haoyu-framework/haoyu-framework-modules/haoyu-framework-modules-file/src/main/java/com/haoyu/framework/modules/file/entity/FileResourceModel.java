package com.haoyu.framework.modules.file.entity;

import com.haoyu.framework.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FileResourceModel extends BaseModel {

    private String name;
    private String parentId;
    private String isFolder;
    private BigDecimal fileCount;
    private String parentIds;
    private String realName;
    private FileRelationModel relation;
    private String type;
    private String isShow;


}
