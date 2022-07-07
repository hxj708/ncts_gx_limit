package com.haoyu.framework.modules.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.haoyu.framework.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文件下载表
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FileDownloadUser对象", description="文件下载表")
public class FileDownloadUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联文件ID")
    @TableField("FILE_ID")
    private String fileId;


}
