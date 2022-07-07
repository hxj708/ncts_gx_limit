package com.haoyu.framework.modules.file.entity;

import com.haoyu.framework.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 需要修改编码的文件表
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2021-01-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FileChangeDecoder对象", description="需要修改编码的文件表")
public class FileChangeDecoder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    @TableField("FILE_ID")
    private String fileId;


}
