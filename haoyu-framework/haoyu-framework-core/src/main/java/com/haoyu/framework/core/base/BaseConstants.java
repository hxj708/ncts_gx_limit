package com.haoyu.framework.core.base;

import cn.hutool.setting.dialect.PropsUtil;

/**
 * 系统常量
 *
 * @author shibo
 * @date 2020-08-08
 */
public interface BaseConstants {

    /** 配置文件名称 */
    String CONFIG_APP = "app";
    /** 配置文件名称 */
    String CONFIG_APP_KEY = "app.key";
    String CONFIG_APP_KEY_VALUE= PropsUtil.get(BaseConstants.CONFIG_APP).getStr(BaseConstants.CONFIG_APP_KEY);

    /** 配置文件名称 */
    String CONFIG_APP_NAME = "app.name";
    String CONFIG_APP_NAME_VALUE= PropsUtil.get(BaseConstants.CONFIG_APP).getStr(BaseConstants.CONFIG_APP_KEY);

    /** 删除状态[N:正常,Y:删除] */
    String IS_DELETED_NO = BaseEntity.IS_DELETED_NO;
    String IS_DELETED_YES = BaseEntity.IS_DELETED_YES;

    /** 约定的空对象ID */
    String ID_EMPTY_OBJECT="EMPTY_OBJECT";
    /** 约定的NULL对象ID */
    String ID_NULL_OBJECT="NULL_OBJECT";

    /** 默认为空消息 */
    String DEFAULT_NULL_MESSAGE = "暂无数据";
    /** 默认成功消息 */
    String DEFAULT_SUCCESS_MESSAGE = "操作成功";
    /** 默认失败消息 */
    String DEFAULT_FAILURE_MESSAGE = "操作失败";
    /** 默认未授权消息 */
    String DEFAULT_UNAUTHORIZED_MESSAGE = "签名认证失败";

    /** 通用审核状态 */
    String COMMON_AUDIT_STATUS_PASS = "passed";

    String COMMON_AUDIT_STATUS_REJECT = "reject";

    String COMMON_AUDIT_STATUS_AUDITING = "auditing";

}
