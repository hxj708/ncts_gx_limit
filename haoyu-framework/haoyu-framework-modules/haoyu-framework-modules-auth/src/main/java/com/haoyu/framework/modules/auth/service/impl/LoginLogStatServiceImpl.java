package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.date.DateUtil;
import com.haoyu.framework.modules.auth.entity.LoginLogStat;
import com.haoyu.framework.modules.auth.mapper.LoginLogStatMapper;
import com.haoyu.framework.modules.auth.service.LoginLogStatService;
import com.haoyu.framework.modules.auth.utils.AuthConstants;
import com.haoyu.framework.modules.auth.utils.AuthWrapperUtils;
import com.haoyu.framework.core.base.BaseServiceImpl;

import com.haoyu.framework.core.base.R;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 权限-登陆情况统计 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("loginLogStatService")
public class LoginLogStatServiceImpl extends BaseServiceImpl<LoginLogStatMapper,LoginLogStat> implements LoginLogStatService {

    @Autowired
    private LoginLogStatMapper loginLogStatMapper;
    
    @Override
    public R<LoginLogStat> create(LoginLogStat entity) {
        return super.create(entity);
    }
    
    @Override
    public R<LoginLogStat> update(LoginLogStat entity) {
        return super.update(entity);
    }
    
    @Override
    public R<LoginLogStat> update(LoginLogStat entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<LoginLogStat> wrapper = AuthWrapperUtils.getLoginLogStatUpdateWrapper(map);
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
        LoginLogStat entity = new LoginLogStat();
        entity.setIsDeleted(LoginLogStat.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<LoginLogStat> wrapper = AuthWrapperUtils.getLoginLogStatUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }
    
    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<LoginLogStat> wrapper = AuthWrapperUtils.getLoginLogStatQueryWrapper(map);
        return super.count(wrapper);
    }
    
    /**
	 * 保存或更新此用户的最后登陆记录
	 * @param userId
	 * @param ip
	 * @param os
	 * @return
	 */
	@Override
	public R saveLoginLogStat(String userId, String ip, String os){
		LoginLogStat loginLogStat = new LoginLogStat();
		loginLogStat.setId(userId);
		loginLogStat.setUserId(userId);
		loginLogStat.setIpAddress(ip);
		loginLogStat.setOs(os);
		loginLogStat.setUpdateUser(userId);
		loginLogStat.setUpdateTime(DateUtil.date());
		loginLogStat.setIsDeleted(LoginLogStat.IS_DELETED_NO);
		int count = loginLogStatMapper.updateStat(loginLogStat);
		if(count==0){
			loginLogStat.setVersion(1);
			loginLogStat.setCounts(BigDecimal.ONE);
			count = this.create(loginLogStat).isSuccess() ? 1:0;
		}
		return R.status(count>0);
	}

	/**
	 * 检查账号信息，
	 * 信息系统对三个月内未登录过系统的账号进行禁用
	 * @param userId
	 * @return
	 */
	@Override
	public boolean checkLoginRecently(String userId){
		boolean isRecently=false;
        LoginLogStat loginLogStat = this.getById(userId);
        if(loginLogStat == null){
            this.saveLoginLogStat(userId,null,null);
            loginLogStat = new LoginLogStat();
            loginLogStat.setUpdateTime(DateUtil.date());
        }
        long monthBetween = DateUtil.betweenMonth(loginLogStat.getUpdateTime(),Calendar.getInstance().getTime(),false);
        if(monthBetween < AuthConstants.LOGIN_LOCK_NOLOGIN_MONTH){
            isRecently=true;
        }
		return isRecently;
	}

}
