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
 * 权限-角色资源关联
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_role_resource")
@ApiModel(value="RoleResource对象", description="权限-角色资源关联")
public class RoleResource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private String roleId;

    @ApiModelProperty(value = "权限ID")
    @TableField("RESOURCE_ID")
    private String resourceId;


}
