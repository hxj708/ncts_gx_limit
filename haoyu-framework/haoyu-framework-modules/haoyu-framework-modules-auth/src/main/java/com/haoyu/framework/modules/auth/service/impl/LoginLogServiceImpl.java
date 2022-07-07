package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import com.haoyu.framework.modules.auth.entity.LoginLog;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.mapper.LoginLogMapper;
import com.haoyu.framework.modules.auth.service.LoginLogService;
import com.haoyu.framework.modules.auth.utils.AuthWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限-登陆情况 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("loginLogService")
public class LoginLogServiceImpl extends BaseServiceImpl<LoginLogMapper,LoginLog> implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;
    
    @Override
    public R<LoginLog> create(LoginLog entity) {
        return super.create(entity);
    }
    
    @Override
    public R<LoginLog> update(LoginLog entity) {
        return super.update(entity);
    }
    
    @Override
    public R<LoginLog> update(LoginLog entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<LoginLog> wrapper = AuthWrapperUtils.getLoginLogUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }
    
    @Override
    public R deleteLogic(@NotNull String id) {
        List<String> ids = Arrays.asList(StrUtil.split(id, StrUtil.COMMA));
        return this.deleteLogic(ids);
    }
    
    @Override
        public R deleteLogic(@NotEmpty List<String> ids) {
        Map<String, Object> map = MapUtil.newHashMap();
        map.put("ids", ids);
        return this.deleteLogic(map);
    }
    
    @Override
    public R deleteLogic(Map<String, Object> map) {
        if (MapUtil.isEmpty(map)) {
            return R.fail("map is empty");
        }
        LoginLog entity = new LoginLog();
        entity.setIsDeleted(LoginLog.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<LoginLog> wrapper = AuthWrapperUtils.getLoginLogUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }
    
    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<LoginLog> wrapper = AuthWrapperUtils.getLoginLogQueryWrapper(map);
        return super.count(wrapper);
    }
	
	@Override
	public int deleteByLogout(LoginLog loginLog){
		loginLog.setUpdateTime(DateUtil.date());
		return this.loginLogMapper.deleteByLogout(loginLog);
	}

    @Override
    public R createLoginLog(LoginUser loginUser, HttpServletRequest request) {
        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(loginUser.getId());
        loginLog.setSessionId(request.getRequestedSessionId());
        loginLog.setIpAddress(ServletUtil.getClientIP(request));
        loginLog.setOs(ServletUtil.getHeader(request, Header.USER_AGENT.getValue(), CharsetUtil.CHARSET_UTF_8));
        return this.create(loginLog);
    }

    @Override
	public int deleteLoginLogByRequestParam(Map<String,Object> requestParam){
		return this.loginLogMapper.deleteByRequestParam(requestParam);
	}

	@Override
	public List<LoginLog> checkLoginLogByUserId(String userId,String sessionId){
		Map<String,Object> requestParam = MapUtil.newHashMap();
		requestParam.put("userId",   userId);
		requestParam.put("sessionId",sessionId);
		requestParam.put("isDeleted","N");
		return this.listByMap(requestParam);
	}
	
	@Override
	public List<LoginLog> checkLoginLog(LoginLog loginLog){
		return this.loginLogMapper.selectByLoginLog(loginLog);
	}

	public LoginLogMapper getLoginLogMapper() {
		return loginLogMapper;
	}

	public void setLoginLogMapper(LoginLogMapper loginLogMapper) {
		this.loginLogMapper = loginLogMapper;
	}

}
