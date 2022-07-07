package com.haoyu.framework.modules.auth.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.haoyu.framework.core.base.BaseService;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.mapper.LoginUserMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

/**
 * <p>
 * VIEW 服务类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public interface LoginUserService extends BaseService<LoginUser, LoginUserMapper>, UserDetailsService {

    /**
     * 账号读取
     */
    LoginUser getByAccount(String username);
    /**
     * 微信号读取
     */
    LoginUser getByWechatId(String wechatId);

    @Override
    LoginUser loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 解锁
     * true： 上锁
     * false：解锁
     */
    int updateLockStatus(String userId, boolean isLock);
}
