package com.haoyu.framework.modules.auth.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.entity.LoginLog;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.mapper.LoginLogMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限-登陆情况 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface LoginLogService extends BaseService<LoginLog,LoginLogMapper> {

    R createLoginLog(LoginUser loginUser, HttpServletRequest request);

                     /**
     * @param paramMap
     * @return
     */
    int deleteLoginLogByRequestParam(Map<String, Object> paramMap);

    /**
     * @param loginLog
     * @return
     */
    int deleteByLogout(LoginLog loginLog);

    List<LoginLog> checkLoginLogByUserId(String userId, String sessionId);

    List<LoginLog> checkLoginLog(LoginLog loginLog);

}
