package com.haoyu.framework.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.haoyu.framework.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限-模块
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_module")
@ApiModel(value="Module对象", description="权限-模块")
public class Module extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("NOTE")
    private String note;


}
