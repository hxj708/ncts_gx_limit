package com.haoyu.framework.modules.auth.web;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.Header;
import cn.hutool.http.useragent.UserAgentInfo;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.setting.dialect.PropsUtil;
import com.haoyu.framework.core.base.BaseConstants;
import com.haoyu.framework.core.base.R;
import com.haoyu.framework.modules.auth.entity.JwtResponse;
import com.haoyu.framework.modules.auth.entity.LoginLog;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.modules.auth.service.LoginLogService;
import com.haoyu.framework.modules.auth.service.LoginUserService;
import com.haoyu.framework.modules.auth.utils.AuthConstants;
import com.haoyu.framework.modules.auth.utils.AuthResultCode;
import com.haoyu.framework.modules.auth.utils.AuthUtils;
import com.haoyu.framework.modules.cache.utils.RedisUtils;
import com.haoyu.framework.utils.JsonMapper;
import com.haoyu.framework.utils.ThreadContext;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * ?????? Controller??????????????????????????????????????????
 */
@Log4j2
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private LoginLogService loginLogService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthUtils authUtils;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * ??????
     * ??????ticke?????? ??????????????????????????????????????????????????????????????????????????????
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginUser loginRequest,HttpServletRequest request) {

        String username = null;
        String password = null;
        //??????????????????
        String ticket = loginRequest.getTicket();
        if (StrUtil.isNotBlank(ticket)) {
            String[] ticketArray = StrUtil.split(ticket, "-");
            if (ticketArray.length != 2) {
                throw new AuthenticationServiceException("??????????????????");
            }
            String key = PropsUtil.get(BaseConstants.CONFIG_APP).getStr(AuthConstants.SSO_SYSTEM_KEY_PREFIX + ticketArray[0]);
            if (StrUtil.isBlank(key)) {
                throw new AuthenticationServiceException("??????????????????");
            }
            String userId = null;
            String loginTime = null;
            try {
                String json = SecureUtil.aes(key.getBytes()).decryptStr(ticketArray[1]);
                Map<String, String> map = JsonMapper.defaultMapper().fromJson(json, Map.class);
                userId = MapUtil.getStr(map, "userId");
                loginTime = MapUtil.getStr(map, "loginTime");
            } catch (Exception e) {
                throw new AuthenticationServiceException("?????????????????????");
            }
            if (!StrUtil.isAllNotBlank(userId, loginTime)) {
                throw new AuthenticationServiceException("?????????????????????");
            }
            if (DateUtil.between(DateUtil.parse(loginTime, DatePattern.NORM_DATETIME_FORMAT).toJdkDate(), DateUtil.date().toJdkDate(), DateUnit.MINUTE) > AuthConstants.SSO_CONFIG_TIMEOUT) {
                throw new AuthenticationServiceException("??????????????????");
            }
            LoginUser loginUser = loginUserService.getByEntityId(userId);
            username = loginUser.getPaperworkNo();
            password = "MD5-" + loginUser.getPassword();

        } else {
            //???????????????
            String captchaKey = AuthConstants.REDIS_LOGIN_CAPTCHA_KEY_PREFIX+StrUtil.blankToDefault(loginRequest.getCaptchaKey(), "");
            String code = StrUtil.blankToDefault(loginRequest.getCaptcha(), "");
            if (!redisTemplate.hasKey(captchaKey)) {
                throw new AuthenticationServiceException("??????????????????");
            }
            String redisCode = (String) redisTemplate.opsForValue().get(captchaKey);
            if (!StringUtils.equalsIgnoreCase(redisCode, code)) {
                throw new AuthenticationServiceException("??????????????????");
            }
            redisTemplate.delete(captchaKey);
            //?????????????????? ????????????
            username = StrUtil.blankToDefault(loginRequest.getUsername(), "");
            password = StrUtil.blankToDefault(loginRequest.getPassword(), "");
            if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
                throw new AuthenticationServiceException("?????????????????????");
            }
            try {
                //?????????????????? ????????????
                username = Base64.decodeStr(username);
                password = Base64.decodeStr(password);
            } catch (Exception e) {
                throw new AuthenticationServiceException("????????????????????????");
            }
        }

        // ??????????????? SpringSecurity AuthenticationProvider ??????
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        //??????ThreadLocal?????????????????????????????????
        ThreadContext.bind(loginUser);
        // SpringSecurity????????????????????????????????????
        loginLogService.createLoginLog(loginUser,request);

        // ?????? authentication ?????? jwtToken
        // String jwt = jwtUtils.createJWT(authentication,loginRequest.getRememberMe());
        String jwt = authUtils.createJWT(authentication, false);
        JwtResponse jwtResponse = new JwtResponse(jwt,loginUser);
        return R.success().setData(jwtResponse);
    }

    /**
     * ???????????????JWT
     *
     * @param request
     * @return
     */
    @PostMapping("/refresh")
    public R refresh(HttpServletRequest request) {
        String jwt = authUtils.refreshJWT(request,false);
        return R.success().setData(new JwtResponse(jwt));
    }

    /**
     * ???????????????JWT??????
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R logout(HttpServletRequest request) {
        try {
            authUtils.invalidateJWT(request);
            ThreadContext.unbindUser();
        } catch (Exception e) {
            throw new AuthenticationServiceException(AuthResultCode.TOKEN_EXPIRED.getMessage());
        }
        return R.success(AuthResultCode.LOGOUT);
    }

    /**
     * ??????????????????????????????
     * @param request
     * @return
     */
    @GetMapping("/getCurrentLoginUser")
    public R getCurrentLoginUser(HttpServletRequest request) {
        String jwt = authUtils.getJwtFromRequest(request);
        LoginUser loginUser = AuthUtils.getCurrentLoginUser();
//        LoginUser loginUser = AuthUtils.getCurrentLoginUser(request);
        //??????JWT????????????
        return R.success().setData(loginUser);
    }

    /**
     * ??????????????????????????????Code??????
     * @param request
     * @return
     */
    @GetMapping("/getCurrentRoleCodeList")
    public R getCurrentRoleCodeList(HttpServletRequest request) {
        String jwt = authUtils.getJwtFromRequest(request);
        //????????????
//        String id = authUtils.getUserIdFromJWT(jwt);
//        LoginUser loginUser = authUtils.getLoginUserCache(id);
//        return R.success().setData(loginUser.getRoleCodeList());

        //??????JWT????????????
        List<String> roleCodeList = authUtils.getUserRoldCodeListFromJWT(jwt);
        return R.success().setData(roleCodeList);
    }

    /**
     * ??????????????????????????????Code??????
     * @param request
     * @return
     */
    @GetMapping("/getCurrentResourceCodeList")
    public R getCurrentResourceCodeList(HttpServletRequest request) {
        String jwt = authUtils.getJwtFromRequest(request);
        //????????????
//        String id = authUtils.getUserIdFromJWT(jwt);
//        LoginUser loginUser = authUtils.getLoginUserCache(id);
//        return R.success().setData(loginUser.getResourceCodeList());

        //??????JWT????????????
        List<String> resourceCodeList = authUtils.getUserResourceCodeListFromJWT(jwt);
        return R.success().setData(resourceCodeList);
    }

    /**
     * ??????????????????????????????Resource??????
     * @param request
     * @return
     */
    @GetMapping("/getCurrentResourceList")
    public R getCurrentResourceList(HttpServletRequest request) {
        LoginUser loginUser = AuthUtils.getCurrentLoginUser();
        if(loginUser!=null){
            return R.success().setData(Base64.encode(JsonMapper.defaultMapper().toJson(loginUser.getResourceList())));
        }
        return R.fail();
    }

    @GetMapping("/captcha")
    public R captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SpecCaptcha specCaptcha = new SpecCaptcha(AuthConstants.LOGIN_USER_CAPTCHA_IMG_WIDTH,
                AuthConstants.LOGIN_USER_CAPTCHA_IMG_HEIGTH,
                AuthConstants.LOGIN_USER_CAPTCHA_IMG_LENGTH);
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        String verCode = specCaptcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // ??????redis????????????????????????3??????
        redisTemplate.opsForValue().set(AuthConstants.REDIS_LOGIN_CAPTCHA_KEY_PREFIX+key, verCode, AuthConstants.LOGIN_USER_CAPTCHA_IMG_EXPIRE,TimeUnit.MINUTES);
        // ???key???base64???????????????
        Map<String,Object> map=MapUtil.newHashMap();
        map.put("key", key);
        map.put("image", specCaptcha.toBase64());
        return R.success().setData(map);
    }
}
