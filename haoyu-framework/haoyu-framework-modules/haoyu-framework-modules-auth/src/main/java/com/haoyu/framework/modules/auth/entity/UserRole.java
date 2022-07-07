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
 * 权限-用户角色关联
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_user_role")
@ApiModel(value="UserRole对象", description="权限-用户角色关联")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;

    @ApiModelProperty(value = "角色ID")
    @TableField("ROLE_ID")
    private String roleId;

    public UserRole(){

    }

    public UserRole(String userId,String roleId){
        this.userId=userId;
        this.roleId=roleId;
    }
}
