package com.haoyu.framework.modules.auth.service.impl;

import cn.hutool.core.date.DateUtil;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.mapper.LoginUserMapper;
import com.haoyu.framework.modules.auth.service.LoginUserService;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
@Service("loginUserService")
public class LoginUserServiceImpl extends BaseServiceImpl<LoginUserMapper,LoginUser> implements LoginUserService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    @Override
    public R<LoginUser> create(LoginUser entity) {
        return super.create(entity);
    }

    @Override
    public R<LoginUser> update(LoginUser entity) {
        return super.update(entity);
    }

    @Override
    public R<LoginUser> update(LoginUser entity, Map<String, Object> map) {
        try {
            LambdaUpdateWrapper<LoginUser> wrapper = AuthWrapperUtils.getLoginUserUpdateWrapper(map);
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
        LoginUser entity = new LoginUser();
        entity.setIsDeleted(LoginUser.IS_DELETED_YES);
        entity.setVersion(null);
        try {
            LambdaUpdateWrapper<LoginUser> wrapper = AuthWrapperUtils.getLoginUserUpdateWrapper(map);
            return super.update(entity, wrapper) ? R.success() : R.fail();
        } catch (IllegalArgumentException e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public int count(Map<String, Object> map) {
        LambdaQueryWrapper<LoginUser> wrapper = AuthWrapperUtils.getLoginUserQueryWrapper(map);
        return super.count(wrapper);
    }

    @Override
    public LoginUser loadUserByUsername(String account) {
        if (StrUtil.isNotEmpty(account)) {
            return loginUserMapper.selectUserViewByAccount(account);
        }
        return null;
    }

    @Override
    public LoginUser getByAccount(String account) {
        return this.loadUserByUsername(account);
    }

    @Override
    public LoginUser getByWechatId(String wechatId) {
        if (StrUtil.isNotBlank(wechatId)) {
            return loginUserMapper.selectUserViewByWechatId(wechatId);
        }
        return null;
    }

//    @Override
    public Map<String, Object> getLoginUserByTransfer(String userName) {
        Map<String, Object> map = null;

        try {
            map = loginUserMapper.selectLoginUserByTransfer(userName);
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
        return map;
    }

    /**
     * 变更用户锁定状态
     * true： 上锁
     * false：解锁
     */
    @Override
    public int updateLockStatus(String userId, boolean isLock) {
        LoginUser user = new LoginUser();
        user.setId(userId);
        user.setIsLock(isLock ? LoginUser.IS_DELETED_YES : LoginUser.IS_DELETED_NO);
        user.setUpdateUser(userId);
        user.setUpdateTime(DateUtil.date());
        return loginUserMapper.updateLockStatus(user);
    }

}
