package com.haoyu.framework.modules.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.haoyu.framework.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 文件关系表
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FileRelation对象", description="文件关系表")
public class FileRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联文件ID")
    @TableField("FILE_ID")
    private String fileId;

    @ApiModelProperty(value = "关联实体ID")
    @TableField("RELATION_ID")
    private String relationId;

    @ApiModelProperty(value = "类型")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "下载数")
    @TableField("DOWNLOAD_NUM")
    private BigDecimal downloadNum;


}
