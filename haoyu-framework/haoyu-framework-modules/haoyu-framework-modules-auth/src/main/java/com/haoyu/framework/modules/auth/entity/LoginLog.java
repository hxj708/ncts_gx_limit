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
 * 权限-登陆情况
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_login_log")
@ApiModel(value="LoginLog对象", description="权限-登陆情况")
public class LoginLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;

    @ApiModelProperty(value = "sessionID")
    @TableField("SESSION_ID")
    private String sessionId;

    @ApiModelProperty(value = "IP")
    @TableField("IP_ADDRESS")
    private String ipAddress;

    @ApiModelProperty(value = "OS")
    @TableField("OS")
    private String os;


}
