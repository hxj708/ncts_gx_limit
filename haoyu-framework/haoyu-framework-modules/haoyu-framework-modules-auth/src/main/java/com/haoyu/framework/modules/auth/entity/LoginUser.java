package com.haoyu.framework.modules.auth.entity;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.PhoneUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haoyu.framework.core.base.BaseUser;
import com.haoyu.framework.modules.auth.utils.AuthServiceUtils;
import com.haoyu.framework.modules.dict.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("userview")
@ApiModel(value="LoginUser对象", description="VIEW")
public class LoginUser extends BaseUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("USER_NAME")
    private String userName;

    @ApiModelProperty(value = "密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "二进制位运算")
    @TableField("ROLE_CODE")
    private BigDecimal roleCode;

    @ApiModelProperty(value = "角色代码集合，json格式")
    @TableField("USER_ROLE")
    private String userRole;

    @ApiModelProperty(value = "证件号")
    @TableField("PAPERWORK_NO")
    private String paperworkNo;

    @ApiModelProperty(value = "姓名")
    @TableField("REAL_NAME")
    private String realName;

    @ApiModelProperty(value = "性别")
    @TableField("SEX")
    private String sex;

    @ApiModelProperty(value = "出生日期")
    @TableField("BORN_DATE")
    private String bornDate;

    @ApiModelProperty(value = "学科")
    @TableField("SUBJECT")
    private String subject;

    @ApiModelProperty(value = "学段")
    @TableField("stage")
    private String stage;

    @ApiModelProperty(value = "部门ID")
    @TableField("DEPT_ID")
    private String deptId;

    @ApiModelProperty(value = "机构/部门名称")
    @TableField("DEPT_NAME")
    private String deptName;

    @ApiModelProperty(value = "级别")
    @TableField("DEPT_LEVEL")
    private Integer deptLevel;

    @ApiModelProperty(value = "类型")
    @TableField("DEPT_TYPE")
    private String deptType;

    @ApiModelProperty(value = "父ID")
    @TableField("PARENT_ID")
    private String parentId;

    @ApiModelProperty(value = "省")
    @TableField("PROVINCE")
    private String province;

    @ApiModelProperty(value = "市")
    @TableField("CITY")
    private String city;

    @ApiModelProperty(value = "区县")
    @TableField("COUNTIES")
    private String counties;

    @ApiModelProperty(value = "个人头像")
    @TableField("AVATAR")
    private String avatar;

    @ApiModelProperty(value = "无格式密码")
    @TableField("PASSWORD_PLAIN")
    private String passwordPlain;

    @ApiModelProperty(value = "培训范围")
    @TableField("TRAIN_RANGE")
    private String trainRange;

    @ApiModelProperty(value = "手机")
    @TableField("MOBILE_PHONE")
    private String mobilePhone;

    @ApiModelProperty(value = "邮箱")
    @TableField("EMAIL")
    private String email;

    @ApiModelProperty(value = "父机构ID")
    @TableField("PARENT_DEPT_ID")
    private String parentDeptId;

    @ApiModelProperty(value = "主机构ID")
    @TableField("MAIN_DEPT_ID")
    private String mainDeptId;

    @ApiModelProperty(value = "微信ID")
    @TableField("WECHAT_ID")
    private String wechatId;

    @ApiModelProperty(value = "微信昵称")
    @TableField("WECHAT_NICKNAME")
    private String wechatNickname;

    @ApiModelProperty(value = "专技网注册身份证")
    @TableField("GDHR_PAPERWORK_NO")
    private String gdhrPaperworkNo;

    @ApiModelProperty(value = "专技网行业序列和高校学时申报")
    @TableField("DEPT_TYPE_SEQ")
    private String deptTypeSeq;

    @ApiModelProperty(value = "是否锁定")
    @TableField("is_lock")
    private String isLock;

    @ApiModelProperty(value = "在岗状态")
    @TableField("post_type")
    private String postType;

    @ApiModelProperty(value = "是否做过问卷")
    @TableField("do_survey")
    private String doSurvey;

    @TableField(exist = false)
    private String subjectEntryName;

    @TableField(exist = false)
    private String stageEntryName;

    //职称
    @ApiModelProperty(value = "职称")
    @TableField("TEACHER_TITLE")
    private String teacherTitle;

    @TableField(exist = false)
    private String teacherTitleEntryName;

    /** 权限和其它用途 */
    @ApiModelProperty(value = "登陆信息-IP地址")
    @TableField(exist=false)
    private String ipAddress;
    @ApiModelProperty(value = "登陆信息-用户标示")
    @TableField(exist=false)
    private String userAgent;
    @ApiModelProperty(value = "单点登陆用-票据")
    @TableField(exist=false)
    private String ticket;
    @ApiModelProperty(value = "权限模块用-用户角色列表")
    @TableField(exist=false)
    private List<Role> roles;
    @ApiModelProperty(value = "权限模块用-用户权限列表")
    @TableField(exist=false)
    private List<Resource> resources;
    @ApiModelProperty(value = "权限模块用-角色菜单")
    @TableField(exist=false)
    private Map<String,String> menus;
    @ApiModelProperty(value = "图形验证码")
    @TableField(exist=false)
    private String captcha;
    @ApiModelProperty(value = "图形验证码key")
    @TableField(exist=false)
    private String captchaKey;

    public LoginUser() {
    }

    public LoginUser(String id, String userName, String paperworkNo, String realName, BigDecimal roleCode,
                     String deptId, String deptName, String parentDeptId,
                     Integer deptLevel, String deptType, String province, String city,
                     String counties, String avatar, String trainRange, String userRole) {
        super();
        this.setId(id);
        this.userName = userName;
        this.paperworkNo = paperworkNo;
        this.realName = realName;
        this.roleCode = roleCode;
        this.deptId = deptId;
        this.deptName = deptName;
        this.parentDeptId = parentDeptId;
        this.deptLevel = deptLevel;
        this.deptType = deptType;
        this.province = province;
        this.city = city;
        this.counties = counties;
        this.avatar = avatar;
        this.trainRange = trainRange;
        this.userRole = userRole;
    }

    @Override
    @JsonIgnore
    public List<Role> getAuthorities() {
        return roles;
    }

    @JsonIgnore
    public List<String> getRoleCodeList(){
        List<String> userRoleCodeList= CollUtil.getFieldValues(this.getRoles(),"code",String.class);
        return userRoleCodeList;
    }

    @JsonIgnore
    public List<String> getResourceCodeList(){
        List<String> userResourceCodeList= CollUtil.getFieldValues(this.getResources(),"code",String.class);
        return userResourceCodeList;
    }

    @JsonIgnore
    public List<Map<String,Object>> getResourceList(){
        List<Map<String,Object>> userResourceList= CollUtil.newArrayList();
        if(CollUtil.isNotEmpty(this.getResources())){
            this.getResources().forEach(resource -> {
                userResourceList.add(AuthServiceUtils.toAuthResourceMap(resource));
            });
        }
        return userResourceList;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public Map<String,Object> getSimpleLoginUser(){
        Map<String,Object> simpleLoginUser = MapUtil.newHashMap();
        simpleLoginUser.put("userName",this.userName);
        simpleLoginUser.put("roleCode",this.roleCode);
        simpleLoginUser.put("userRole",this.userRole);
        simpleLoginUser.put("paperworkNo", IdcardUtil.hide(this.paperworkNo,6,14));
        simpleLoginUser.put("realName",this.realName);
        simpleLoginUser.put("sex",this.sex);
        simpleLoginUser.put("sexName", DictUtils.getEntryName("SEX_TYPE",this.sex));
        simpleLoginUser.put("deptId",this.deptId);
        simpleLoginUser.put("deptName",this.deptName);
        simpleLoginUser.put("deptLeve",this.deptLevel);
        simpleLoginUser.put("deptType",this.deptType);
        simpleLoginUser.put("parentId",this.parentId);
        simpleLoginUser.put("province",this.province);
        simpleLoginUser.put("city",this.city);
        simpleLoginUser.put("counties",this.counties);
        simpleLoginUser.put("avatar",this.avatar);
        simpleLoginUser.put("trainRange","");
        simpleLoginUser.put("mobilePhone", PhoneUtil.hideBetween(this.mobilePhone));
        simpleLoginUser.put("email",this.email);
        simpleLoginUser.put("parentDeptId",this.parentDeptId);
        simpleLoginUser.put("mainDeptId",this.mainDeptId);
        simpleLoginUser.put("wechatId",this.wechatId);
        simpleLoginUser.put("wechatNickname",this.wechatNickname);
        simpleLoginUser.put("deptTypeSeq",this.deptTypeSeq);
        simpleLoginUser.put("isLock",this.isLock);
        simpleLoginUser.put("ipAddress",this.ipAddress);
        simpleLoginUser.put("userAgent",this.userAgent);
        simpleLoginUser.put("doSurvey",this.getDoSurvey());
        simpleLoginUser.put("id",this.getId());
        return simpleLoginUser;
    }

    public void setSubject(String subject){
        this.subject = subject;
        if(StrUtil.isEmpty(this.subjectEntryName)){
            this.subjectEntryName = DictUtils.getEntryName("TRAIN_SUBJECT", subject);
        }
    }
    public void setStage(String stage){
        this.stage = stage;
        if(StrUtil.isEmpty(this.stageEntryName)){
            this.stageEntryName = DictUtils.getEntryName("TRAIN_STAGE", stage);
        }
    }

    public void setTeacherTitle(String teacherTitle){
        this.teacherTitle = teacherTitle;
        if(StrUtil.isEmpty(this.teacherTitleEntryName)){
            this.teacherTitleEntryName = DictUtils.getEntryName("TEACHER_TITLE", teacherTitle);
        }
    }
}
