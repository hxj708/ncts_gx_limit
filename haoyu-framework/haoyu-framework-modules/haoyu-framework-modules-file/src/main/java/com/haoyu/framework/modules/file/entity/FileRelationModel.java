package com.haoyu.framework.modules.file.entity;

import com.haoyu.framework.model.BaseModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FileRelationModel extends BaseModel {

    private String fileId;
    private String relationId;
    private String type;
    private BigDecimal downloadNum;

}
