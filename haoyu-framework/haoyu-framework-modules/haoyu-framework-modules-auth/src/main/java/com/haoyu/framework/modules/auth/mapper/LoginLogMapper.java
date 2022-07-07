package com.haoyu.framework.modules.auth.mapper;

import com.haoyu.framework.modules.auth.entity.LoginLog;
import com.haoyu.framework.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限-登陆情况 Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Repository("loginLogMapper")
public interface LoginLogMapper extends BaseMapper<LoginLog> {

	int deleteByRequestParam(Map<String,Object> requestParam);

	int deleteByLogout(LoginLog loginLog);

	List<LoginLog> selectByLoginLog(LoginLog loginLog);

}
