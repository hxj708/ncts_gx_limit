package com.haoyu.framework.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.ContentType;
import com.haoyu.framework.core.base.BaseException;
import com.haoyu.framework.core.base.R;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * Response 通用工具类
 * </p>
 *
 * @package: com.xkcoding.rbac.security.util
 * @description: Response 通用工具类
 * @author: yangkai.shen
 * @date: Created in 2018-12-07 17:37
 * @copyright: Copyright (c) 2018
 * @version: V1.0
 * @modified: yangkai.shen
 */
@Log4j2
public class ResponseUtils {

    /**
     * 往 response 写出 json
     *
     * @param response 响应
     * @param data     返回数据
     */
    public static void renderJson(HttpServletResponse response, R data) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType(ContentType.JSON.getValue());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setStatus(200);
        ServletUtil.write(response, JsonMapper.defaultMapper().toJson(data), ContentType.JSON.getValue());
    }

    /**
     * 往 response 写出 json
     *
     * @param response  响应
     * @param exception 异常
     */
    public static void renderJson(HttpServletResponse response, BaseException exception) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setContentType(ContentType.JSON.getValue());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setStatus(200);
        R data = R.data(exception.getCode(), exception.getData(), exception.getMessage());
        ServletUtil.write(response, JsonMapper.defaultMapper().toJson(data), ContentType.JSON.getValue());
    }
}
