package com.haoyu.framework.modules.video.entity;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.haoyu.framework.core.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="FileInfo对象", description="文件表")
public class FileInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    @TableField("FILE_NAME")
    private String fileName;

    @ApiModelProperty(value = "文件地址")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "状态")
    @TableField("STATE")
    private String state;

    @ApiModelProperty(value = "大小")
    @TableField("FILE_SIZE")
    private BigDecimal fileSize;

    @ApiModelProperty(value = "文件分组名")
    @TableField("GROUP_NAME")
    private String groupName;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

    @ApiModelProperty(value = "文件所属包ID")
    @TableField("FILE_RESOURCE_ID")
    private String fileResourceId;

    //数据信息
//    @TableField(exist = false)
//    private MultimediaInfo multimediaInfo;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileInfo) {
            obj = (FileInfo)obj;
            if(StrUtil.isEmpty(((FileInfo) obj).getId())){
                return false;
            }
            if (((FileInfo) obj).getId().equals(this.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}
