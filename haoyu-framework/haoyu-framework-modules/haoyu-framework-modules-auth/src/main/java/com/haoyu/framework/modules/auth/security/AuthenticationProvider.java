package com.haoyu.framework.modules.auth.security;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.setting.dialect.PropsUtil;
import com.haoyu.framework.core.base.BaseConstants;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.entity.Module;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.haoyu.framework.modules.auth.entity.Role;
import com.haoyu.framework.modules.auth.service.*;
import com.haoyu.framework.modules.auth.utils.AuthConstants;
import com.haoyu.framework.modules.auth.utils.AuthUtils;
import com.haoyu.framework.modules.dict.utils.DictUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登陆验证
 *
 * @author LiuChao
 * @date 2020-06-01
 */
@Component("authenticationProvider")
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private LoginLogStatService loginLogStatService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private LoginLogService loginLogService;
    @Qualifier("md5PasswordEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthUtils authUtils;


    /**
     * 验证码验证结束后，继续验证
     * 4.验证账号密码是否正确
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //上一步传过来的非加密的账号密码
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = authentication.getName();
        String passwordPlain = authentication.getCredentials().toString();
        String passwordEncrypt = null;
        if (StrUtil.startWith(passwordPlain, AuthConstants.PASSWORD_MD5_PREFIX)) {
            passwordEncrypt = StrUtil.removePrefix(passwordPlain, AuthConstants.PASSWORD_MD5_PREFIX);
        } else {
            passwordEncrypt = SecureUtil.md5().digestHex(passwordPlain);
        }
        String errorMessage = "";

        //如果已经超过3次，再次使用此账号会继续锁定
//        if (!authUtils.checkLoginFailed(username)) {
//            authUtils.addLoginFailed(username);
//            errorMessage = "用户输入的账号密码错误" + AuthConstants.LOGIN_FAILED_LIMIT + "次被锁定" + AuthConstants.LOGIN_FAILED_BAN_TIME + "分钟！在此期间请勿重复输入账号密码，时间未到将重新锁定" + AuthConstants.LOGIN_FAILED_BAN_TIME + "分钟。";
//            throw new AuthenticationServiceException(errorMessage);
//        }

        LoginUser loginUser = loginUserService.loadUserByUsername(username);

        if (loginUser != null) {

            //部分角色不允许登陆
            String[] userRoleLimits = PropsUtil.get(BaseConstants.CONFIG_APP).getStr(AuthConstants.USER_ROLE_LIMIT).split(";");
            if (loginUser != null && !ArrayUtils.contains(userRoleLimits, loginUser.getRoleCode().toString())) {
                errorMessage = "用户不存在！";
                throw new UsernameNotFoundException(errorMessage);
            }

            //判断是否锁定
//            if (StrUtil.isNotBlank(loginUser.getIsLock()) && StrUtil.equals(loginUser.getIsLock(), BaseConstants.IS_DELETED_YES)) {
//                errorMessage = "账号被锁定,请联系学校管理员重置密码即可解除锁定！";
//                throw new AuthenticationServiceException(errorMessage);
//            }

            //信息系统对三个月内未登录过系统的账号进行禁用
//            if (AuthConstants.USER_ROLECODE_TEACHER == loginUser.getRoleCode().intValue() && !loginLogStatService.checkLoginRecently(loginUser.getId())) {
//                loginUserService.updateLockStatus(loginUser.getId(), true);
//                errorMessage = "用户超过" + AuthConstants.LOGIN_LOCK_NOLOGIN_MONTH + "个月未登录被锁定,请联系学校管理员重置密码即可解除锁定！";
//                throw new AuthenticationServiceException(errorMessage);
//            }

            // 登陆黑名单判断
            if (StringUtils.isNotBlank(DictUtils.getEntryName(AuthConstants.DICT_KEY_LOGIN_ITLMS_BLACKLIST, loginUser.getDeptId()))) {
                errorMessage = "用户不存在！";
                throw new UsernameNotFoundException(errorMessage);
            }

            //验证密码
            if (!StrUtil.equals(loginUser.getPassword(), passwordEncrypt)) {
                authUtils.addLoginFailed(username);
                errorMessage = "用户名或密码错误！";
                throw new BadCredentialsException(errorMessage);
            }

            //对密码强度不够的教师用户进行禁用
//            if (AuthConstants.USER_ROLECODE_TEACHER == loginUser.getRoleCode().intValue() && !authUtils.checkPasswordStrong(passwordPlain)) {
//                errorMessage = "账号密码强度弱被锁定，请联系学校管理员重置密码即可解除锁定！";
//                throw new AuthenticationServiceException(errorMessage);
//            }

            //确认可以登陆后，读取此用户的权限信息

            // 保存用户明文密码
            /*if (StringUtils.isEmpty(loginUser.getPasswordPlain()) && !StrUtil.startWith(passwordPlain, AuthConstants.PASSWORD_MD5_PREFIX)) {
                User iuser = new User();
                iuser.setId(loginUser.getId());
                iuser.setPasswordPlain(passwordPlain);
                userService.updatePassword(iuser);
            }*/

            //获得用户所有角色的权限的资源.
            List<Resource> resourceList = resourceService.getResourceByUser(loginUser.getId());
            loginUser.setResources(resourceList);
            //获得用户所有角色
            List<Role> roleList = roleService.getRoleByUser(loginUser.getId());
            loginUser.setRoles(roleList);

            //整理菜单
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("show", "1");
            List<Module> moduleList = moduleService.listByMap(map);
            loginUser.setMenus(resourceService.createTreeMenuJsonMap(moduleList, resourceList));

            return new UsernamePasswordAuthenticationToken(loginUser, loginUser.getPassword(), loginUser.getAuthorities());

        } else {
//            Map<String, Object> map = loginUserService.getLoginUserByTransfer(username);
            Map<String, Object> map = null;
            if (map != null && (!map.isEmpty())) {
                errorMessage = "此用户正在从 &nbsp;&nbsp;" + MapUtils.getString(map, "outDept") + "&nbsp;&nbsp;调往&nbsp;&nbsp;" + MapUtils.getString(map, "inDept") + "&nbsp;&nbsp;需学校管理员接收后才可登录!";
                throw new AuthenticationServiceException(errorMessage);
            } else {
//                authUtils.addLoginFailed(username);
//                errorMessage = "用户名或密码错误！连续输入错误" + AuthConstants.LOGIN_FAILED_LIMIT + "次后将被锁定" + AuthConstants.LOGIN_FAILED_BAN_TIME + "分钟！";
//                throw new AuthenticationServiceException(errorMessage);
                errorMessage = "用户不存在！";
                throw new UsernameNotFoundException(errorMessage);
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication) || WeixinAuthenticationToken.class.equals(authentication);
    }
}
