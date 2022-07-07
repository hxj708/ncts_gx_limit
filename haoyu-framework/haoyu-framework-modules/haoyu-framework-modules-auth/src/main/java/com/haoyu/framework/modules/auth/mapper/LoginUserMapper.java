package com.haoyu.framework.modules.auth.mapper;

import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.core.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Repository("loginUserMapper")
public interface LoginUserMapper extends BaseMapper<LoginUser> {

    LoginUser selectUserViewByWechatId(String wechatId);

    LoginUser selectUserViewByAccount(String account);

    /**
     * 查询转校记录
     */
    Map<String, Object> selectLoginUserByTransfer(String userName);

    int updateLockStatus(LoginUser loginUser);

}
