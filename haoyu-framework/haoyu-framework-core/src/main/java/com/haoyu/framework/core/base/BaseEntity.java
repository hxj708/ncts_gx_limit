package com.haoyu.framework.core.base;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * @author shibo
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(value = "ID", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "主键ID")
    private String id;

    /** 创建人 */
    @TableField(value = "CREATE_USER", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createUser;

    /** 创建时间 */
    @TableField(value = "CREATE_TIME", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /** 修改人 */
    @TableField(value = "UPDATE_USER", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人")
    private String updateUser;

    /** 修改时间 */
    @TableField(value = "UPDATE_TIME", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /** 是否删除 */
    @TableField(value = "IS_DELETED", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "是否删除：Y为已删除,N为未删除")
    private String isDeleted;

    /**
     * 版本号
     * 乐观锁：创建时默认版本号为1， 初始化为0
     * 单条update需传入正确的版本号才能成功，批量update默认方法会绕过乐观锁，如业务有需求自行在业务service中增加带乐观锁的批量update方法
     */
    @Version
    @TableField(value = "VERSION", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "版本号")
    private Integer version;

    public static String IS_DELETED_YES = "Y";
    public static String IS_DELETED_NO = "N";

//    public Date getCreateTimeDate(){
//        if(this.getCreateTime()!=null) {
//            ZoneId zoneId = ZoneId.systemDefault();
//            ZonedDateTime zonedDateTime = this.getCreateTime().atZone(zoneId);
//            return Date.from(zonedDateTime.toInstant());
//        }
//        return null;
//    }
//
//    public Date getUpdateTimeDate(){
//        if(this.getUpdateTime()!=null) {
//            ZoneId zoneId = ZoneId.systemDefault();
//            ZonedDateTime zonedDateTime = this.getUpdateTime().atZone(zoneId);
//            return Date.from(zonedDateTime.toInstant());
//        }
//        return null;
//    }
}
