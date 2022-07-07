package com.haoyu.framework.modules.auth.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haoyu.framework.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限-登陆情况统计
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_login_log_stat")
@ApiModel(value="LoginLogStat对象", description="权限-登陆情况统计")
public class LoginLogStat extends BaseEntity {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "USER_ID")
    @TableField("USER_ID")
    private String userId;


    @ApiModelProperty(value = "IP_ADDRESS")
    @TableField("IP_ADDRESS")
    private String ipAddress;


    @ApiModelProperty(value = "OS")
    @TableField("OS")
    private String os;

    @ApiModelProperty(value = "COUNTS")
    @TableField("COUNTS")
    private BigDecimal counts;


}
