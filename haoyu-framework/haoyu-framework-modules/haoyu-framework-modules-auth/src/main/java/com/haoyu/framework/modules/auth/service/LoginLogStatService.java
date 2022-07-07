package com.haoyu.framework.modules.auth.service;

import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.entity.LoginLogStat;
import com.haoyu.framework.modules.auth.mapper.LoginLogStatMapper;

/**
 * <p>
 * 权限-登陆情况统计 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface LoginLogStatService extends BaseService<LoginLogStat,LoginLogStatMapper> {

    R saveLoginLogStat(String userId, String ip, String os);

    boolean checkLoginRecently(String userId);
}
