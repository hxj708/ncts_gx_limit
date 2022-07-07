package com.haoyu.framework.modules.auth.entity;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.haoyu.framework.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 权限-资源
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-08-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_resource")
@ApiModel(value="Resource对象", description="权限-资源")
public class Resource extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模块代码")
    @TableField("MODULE_CODE")
    private String moduleCode;

    @ApiModelProperty(value = "上级ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "唯一标示，用于界面权限控制")
    @TableField("CODE")
    private String code;

    @ApiModelProperty(value = "类型")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "链接")
    @TableField("VALUE")
    private String value;

    @ApiModelProperty(value = "图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "是否显示")
    @TableField("IS_SHOW")
    private String isShow;

    @ApiModelProperty(value = "排序")
    @TableField("TRAIN")
    private Double train;

    @ApiModelProperty(value = "权限规则")
    @TableField("RULE")
    private String rule;

    @ApiModelProperty(value = "说明")
    @TableField("NOTE")
    private String note;

    @ApiModelProperty(value = "打开方式")
    @TableField("TARGET")
    private String target;

    @ApiModelProperty(value = "组件")
    @TableField("COMPONENT")
    private String component;

    @ApiModelProperty(value = "扩展JSON")
    @TableField("META")
    private String meta;

    /** 对应角色ID列表 */
    /** 可访问该资源的授权ID字符串, 多个授权用','分隔. */
    @TableField(exist = false)
    private String roleIds;
    /** 可访问该资源的授权名称字符串, 多个授权用','分隔. */
    @TableField(exist = false)
    private String roleNames;
    /** 可访问该资源的授权代码字符串, 多个授权用','分隔. */
    @TableField(exist = false)
    private String roleCodes;
    /** 子权限 */
    @TableField(exist = false)
    private List<Resource> resources = CollectionUtil.newArrayList();


}
