package com.haoyu.framework.modules.auth.mapper;

import com.haoyu.framework.modules.auth.entity.LoginLogStat;
import com.haoyu.framework.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 权限-登陆情况统计 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Repository("loginLogStatMapper")
public interface LoginLogStatMapper extends BaseMapper<LoginLogStat> {

    int updateStat(LoginLogStat loginLogStat);

}
