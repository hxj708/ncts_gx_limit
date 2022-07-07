package com.haoyu.framework.modules.file.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.haoyu.framework.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 文件资源表
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FileResource对象", description="文件资源表")
public class FileResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件包名")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "父包ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "是否文件夹")
    @TableField("IS_FOLDER")
    private String isFolder;

    @ApiModelProperty(value = "文件数")
    @TableField("FILE_COUNT")
    private BigDecimal fileCount;

    @ApiModelProperty(value = "活动状态")
    @TableField("FILE_INFO_ID")
    private String fileInfoId;

    @ApiModelProperty(value = "类型")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "父ID集合")
    @TableField("PARENT_IDS")
    private String parentIds;

    @ApiModelProperty(value = "是否可见")
    @TableField("IS_SHOW")
    private String isShow;

    @TableField(exist = false)
    private String realName;

    @TableField(exist = false)
    private FileRelation relation;

    @TableField(exist = false)
    private String relationId;

    @TableField(exist = false)
    private List<FileInfo> fileInfos = CollectionUtil.newArrayList();

    @TableField(exist = false)
    private FileInfo fileInfo;

}
