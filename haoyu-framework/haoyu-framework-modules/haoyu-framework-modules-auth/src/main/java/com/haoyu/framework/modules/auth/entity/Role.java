package com.haoyu.framework.modules.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.haoyu.framework.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 权限-角色
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_role")
@ApiModel(value="Role对象", description="权限-角色")
public class Role extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "编码")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "备注")
    @TableField("NOTE")
    private String note;

    /**
     * 已授权人数
     */
    @TableField(exist=false)
    private Integer userCount;


	@Override
	public String getAuthority() {
        return code;
	}
}
