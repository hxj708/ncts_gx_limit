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
 * 基础-字典项
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_dict_entry")
@ApiModel(value="DictEntry对象", description="基础-字典项")
public class DictEntry extends BaseEntity implements Comparable<DictEntry> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "字典")
    @TableField("DICT_TYPE_CODE")
    private String dictTypeCode;

    @ApiModelProperty(value = "代码")
    @TableField("DICT_VALUE")
    private String dictValue;

    @ApiModelProperty(value = "名称")
    @TableField("DICT_NAME")
    private String dictName;

    @ApiModelProperty(value = "父代码")
    @TableField("PARENT_VALUE")
    private String parentValue;

    @ApiModelProperty(value = "父名称")
    @TableField("PARENT_NAME")
    private String parentName;

    @ApiModelProperty(value = "排序")
    @TableField("SORT_NO")
    private BigDecimal sortNo;

    @ApiModelProperty(value = "隐藏")
    @TableField("IS_HIDDEN")
    private String isHidden;

    @Override
    public int compareTo(DictEntry o) {
        return this.sortNo.intValue() - o.getSortNo().intValue();
    }
}
