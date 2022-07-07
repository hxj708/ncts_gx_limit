package com.haoyu.framework.core.base;

/**
 * 状态码接口规范
 */
public interface BaseResultCode {

    /**
     * 状态码
     *
     * @return 状态码
     */
    Integer getCode();

    /**
     * 返回信息
     *
     * @return 返回信息
     */
    String getMessage();

}
