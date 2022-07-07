package com.haoyu.framework.modules.dict.entity;

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
 * 基础-字典
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_dict_type")
@ApiModel(value="DictType对象", description="基础-字典")
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典代码")
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;

    @ApiModelProperty(value = "字典名称")
    @TableField("DICT_TYPE_NAME")
    private String dictTypeName;

    @ApiModelProperty(value = "权重")
    @TableField("RANK")
    private BigDecimal rank;

    @ApiModelProperty(value = "父代码")
    @TableField("PARENT_CODE")
    private String parentCode;


}
