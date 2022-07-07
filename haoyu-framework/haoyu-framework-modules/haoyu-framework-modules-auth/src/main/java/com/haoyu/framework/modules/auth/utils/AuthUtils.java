package com.haoyu.framework.modules.auth.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.extra.spring.SpringUtil;
import com.haoyu.framework.core.base.BaseException;
import com.haoyu.framework.modules.auth.config.JwtConfig;
import com.haoyu.framework.modules.auth.entity.LoginUser;
import com.haoyu.framework.utils.ThreadContext;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * JWT 工具类
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Log4j2
public class AuthUtils {
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 签名私钥
     */
    private SecretKey createSecretKey() {
        //本地的密码解码
        byte[] encodedKey = Base64.decodeBase64(jwtConfig.getKey());
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        // Algorithm 见 io.jsonwebtoken.SignatureAlgorithm
        SecretKey secretKey = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HmacSHA512");
        return secretKey;
    }

    /**
     * 创建JWT
     *
     * @param rememberMe       记住我
     * @param id               用户id
     * @param subject          用户名
     * @param roleCodeList     用户角色代码
     * @param resourceCodeList 用户权限代码
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, String id, String subject, List<String> roleCodeList, List<String> resourceCodeList) {
        DateTime now = DateUtil.date();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(this.createSecretKey(), SignatureAlgorithm.HS512);
//                .claim(AuthConstants.JWT_CLAIMS_KEY_ROLE_CODE_LIST, roleCodeList)
//                .claim(AuthConstants.JWT_CLAIMS_KEY_RESOURCE_CODE_LIST, resourceCodeList);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getExpire();
        if (ttl > 0) {
            Date expireDate = DateUtil.offsetSecond(now, ttl.intValue());
            builder.setExpiration(expireDate);
        }

        String jwt = builder.serializeToJsonWith(new JacksonSerializer()).compact();
        // 将生成的JWT保存至Redis
        redisTemplate.opsForValue().set(AuthConstants.REDIS_JWT_KEY_PREFIX + id, jwt, ttl, TimeUnit.SECONDS);
        return jwt;
    }

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJWT(Authentication authentication, Boolean rememberMe) {
        LoginUser userPrincipal = (LoginUser) authentication.getPrincipal();
        this.setLoginUserCache(userPrincipal, rememberMe);
        return createJWT(rememberMe, userPrincipal.getId(), userPrincipal.getUsername(), userPrincipal.getRoleCodeList(), userPrincipal.getResourceCodeList());
    }

    /**
     * 解析JWT
     * 仅做解析
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    private Claims parseJWTOnly(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .deserializeJsonWith(new JacksonDeserializer<>())
                .setSigningKey(this.createSecretKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    /**
     * 解析JWT
     * 解析和校验
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            Claims claims = parseJWTOnly(jwt);

            String username = claims.getSubject();
            String id = claims.getId();
            String redisKey = AuthConstants.REDIS_JWT_KEY_PREFIX + id;

            // 校验redis中的JWT是否存在
            Long expire = redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new BaseException(AuthResultCode.TOKEN_EXPIRED);
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = (String) redisTemplate.opsForValue().get(redisKey);
            if (jwtConfig.getConcurrencyCount() > 0) {
                if (!StrUtil.equals(jwt, redisToken)) {
                    throw new BaseException(AuthResultCode.TOKEN_OUT_OF_CTRL);
                }
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new BaseException(AuthResultCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new BaseException(AuthResultCode.TOKEN_PARSE_ERROR);
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new BaseException(AuthResultCode.TOKEN_PARSE_ERROR);
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new BaseException(AuthResultCode.TOKEN_PARSE_ERROR);
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new BaseException(AuthResultCode.TOKEN_PARSE_ERROR);
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String id = getUserIdFromJWT(jwt);
        // 从redis中清除JWT
        redisTemplate.delete(AuthConstants.REDIS_JWT_KEY_PREFIX + id);
        this.removeLoginUserCache(id);
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public String refreshJWT(HttpServletRequest request, Boolean rememberMe) {
        String jwt = getJwtFromRequest(request);
        Claims claims = this.parseJWT(jwt);
//        Date expirationDate = claims.getExpiration();
        Date issueDate = claims.getIssuedAt();
        //每过10秒钟刷新一次
        if (DateUtil.between(DateUtil.date(), issueDate, DateUnit.SECOND) >= jwtConfig.getRefresh()) {
            String id = claims.getId();
            String subject = claims.getSubject();
            List<String> roleCodeList = claims.get(AuthConstants.JWT_CLAIMS_KEY_ROLE_CODE_LIST, List.class);
            List<String> resourceCodeList = claims.get(AuthConstants.JWT_CLAIMS_KEY_RESOURCE_CODE_LIST, List.class);
            jwt = this.createJWT(rememberMe, id, subject, roleCodeList, resourceCodeList);
            // 重新设置过期时间
            String key = AuthConstants.REDIS_LOGIN_USER_KEY_PREFIX + id;
            Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getExpire();
            redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
        }
        return jwt;
    }

    /**
     * 从 request 的 header 中获取 JWT
     * 需要用 “Bearer_”前缀
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtConfig.getTokenHeaderName());
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(jwtConfig.getTokenPrefix())) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 根据 jwt 获取用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseJWTOnly(jwt);
        return claims.getSubject();
    }

    /**
     * 根据 jwt 获取Id
     */
    public String getUserIdFromJWT(String jwt) {
        Claims claims = parseJWTOnly(jwt);
        return claims.getId();
    }

    /**
     * 根据 jwt 获取roleCodeList
     */
    public List<String> getUserRoldCodeListFromJWT(String jwt) {
        Claims claims = parseJWTOnly(jwt);
        List<String> roleCodeList = claims.get(AuthConstants.JWT_CLAIMS_KEY_ROLE_CODE_LIST, List.class);
        return roleCodeList;
    }

    /**
     * 根据 jwt 获取resourceCodeList
     */
    public List<String> getUserResourceCodeListFromJWT(String jwt) {
        Claims claims = parseJWTOnly(jwt);
        List<String> resourceCodeList = claims.get(AuthConstants.JWT_CLAIMS_KEY_RESOURCE_CODE_LIST, List.class);
        return resourceCodeList;
    }

    /**
     * cache中读取LoginUser
     */
    public LoginUser getLoginUserCache(String id) {
        String key = AuthConstants.REDIS_LOGIN_USER_KEY_PREFIX + id;
        if (redisTemplate.hasKey(key)) {
            return (LoginUser) redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    /**
     * cache中读取LoginUser
     */
    public void setLoginUserCache(LoginUser loginUser, Boolean rememberMe) {
        String key = AuthConstants.REDIS_LOGIN_USER_KEY_PREFIX + loginUser.getId();
        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getExpire();
        redisTemplate.opsForValue().set(key, loginUser, ttl, TimeUnit.SECONDS);
    }

    /**
     * cache中移除LoginUser
     */
    public void removeLoginUserCache(String id) {
        String key = AuthConstants.REDIS_LOGIN_USER_KEY_PREFIX + id;
        redisTemplate.delete(key);
    }

    /**
     * 从 request 的 header 中获取 ID
     * 从 redis 中 获取 LoginUser
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        String jwt = this.getJwtFromRequest(request);
        String id = this.getUserIdFromJWT(jwt);
        return this.getLoginUserCache(id);
    }

    /**
     * 从 request 的 header 中获取 ID
     * 从 redis 中 获取 LoginUser
     */
    public final static LoginUser getCurrentLoginUser(HttpServletRequest request) {
        AuthUtils authUtils = SpringUtil.getBean(AuthUtils.class);
        String jwt = authUtils.getJwtFromRequest(request);
        String id = authUtils.getUserIdFromJWT(jwt);
        return authUtils.getLoginUserCache(id);
    }

    /**
     * 从 ThreadContext 中获取 LoginUser
     */
    public final static LoginUser getCurrentLoginUser() {
        LoginUser loginUser = ThreadContext.getUser();
        return loginUser;
    }

    public final static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip) || ip.contains("unknown")) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (StringUtils.isEmpty(ip) || ip.contains("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || ip.contains("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || ip.contains("unknown")) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public Integer getOnlineUserCount() {
        return redisTemplate.opsForHash().entries(AuthConstants.KEY_ONLINE_USER_MAP).size();
    }

    public Integer getOnlineSessionCount() {
        Integer count = null;
        Object obj = redisTemplate.opsForValue().get(AuthConstants.KEY_ONLINE_SESSION_COUNT);
        if (obj == null) {
            count = redisTemplate.keys(AuthConstants.KEY_ONLINE_SESSION_PREFIX + "*").size();
            redisTemplate.opsForValue().set(AuthConstants.KEY_ONLINE_SESSION_COUNT, count, Duration.ofMinutes(1));
        } else {
            count = (Integer) obj;
        }
        return count;
    }

    /**
     * 此用户名登陆失败后，将此用户名加入登陆失败缓存，登陆失败则+1，计算登陆时间，15分钟后解除
     */
    public boolean addLoginFailed(String loginName) {
        String key = AuthConstants.LOGIN_FAILED_KEY_PREFIX + loginName;
        redisTemplate.opsForValue().increment(key, 1);
        return redisTemplate.expire(key, AuthConstants.LOGIN_FAILED_BAN_TIME, TimeUnit.MINUTES);
    }

    /**
     * 此用户名登陆成功后，清除缓存
     */
    public boolean delLoginFailed(String loginName) {
        String key = AuthConstants.LOGIN_FAILED_KEY_PREFIX + loginName;
        return redisTemplate.delete(key);
    }

    /**
     * 检查是否此用户名登陆失败次数超过限制
     */
    public boolean checkLoginFailed(String loginName) {
        boolean canLogin = false;
        String key = AuthConstants.LOGIN_FAILED_KEY_PREFIX + loginName;
        Object loginTimeObj = redisTemplate.boundValueOps(key).get(0, -1);
        if (loginTimeObj != null && StrUtil.isNotBlank(loginTimeObj.toString())) {
            int count = 0;
            try {
                count = Integer.parseInt(loginTimeObj.toString());
                canLogin = count < AuthConstants.LOGIN_FAILED_LIMIT;
            } catch (Exception ex) {
                ex.printStackTrace();
                canLogin = true;
            }
        } else {
            canLogin = true;
        }
        return canLogin;
    }

    /**
     * 检查密码是否为强密码
     * 禁止弱口令账号登录系统以及强制修改口令相关代码情况
     * 口令长度至少8位，由大写字母、小写字母、数字、特殊字符的三种或以上组成
     *
     * @param password
     * @return
     */
    public boolean checkPasswordStrong(String password) {
        boolean isStrong = false;
        Pattern regUpper = compile("[A-Z]");
        Pattern regLower = compile("[a-z]");
        Pattern regNum = compile("[0-9]");
        Pattern regSymbol = compile("[`~!@#$^&*()_=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？+-]");
        int complex = 0;
        if (regUpper.matcher(password).find()) {
            ++complex;
        }
        if (regLower.matcher(password).find()) {
            ++complex;
        }
        if (regNum.matcher(password).find()) {
            ++complex;
        }
        if (regSymbol.matcher(password).find()) {
            ++complex;
        }
        if (complex >= 3 && password.length() >= 8) {
            isStrong = true;
        }
        return isStrong;
    }

}
