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
 * 基础-行政区域
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("ipanther_dict_region")
@ApiModel(value="DictRegion对象", description="基础-行政区域")
public class DictRegion extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("CODE")
    private String code;

    @TableField("NAME")
    private String name;

    @TableField("PARENT_CODE")
    private String parentCode;

    @TableField("LEVEL")
    private BigDecimal level;

    @TableField("SORT_NO")
    private String sortNo;


}
