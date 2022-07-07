package com.haoyu.framework.modules.cache.utils;

import cn.hutool.extra.spring.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtils {

    public static RedisTemplate getRedisTemplate() {
        return (RedisTemplate) SpringUtil.getBean("redisTemplate");
    }

    public static void addOrUpdate(String key, Object value, Integer expire, TimeUnit timeUnit) {
        getRedisTemplate().opsForValue().set(key, value);
        if (expire != null) {
            if(timeUnit == null){
                timeUnit = TimeUnit.HOURS;
            }
            getRedisTemplate().expire(key, expire, timeUnit);
        }
    }

    public static void addOrUpdate(String key, String hashKey, Object value, Integer expire, TimeUnit timeUnit) {
        getRedisTemplate().opsForHash().put(key, hashKey, value);
        if (expire != null) {
            if(timeUnit == null){
                timeUnit = TimeUnit.HOURS;
            }
            getRedisTemplate().expire(key, expire, timeUnit);
        }
    }

    public static void addOrUpdate(String key, Object value) {
        addOrUpdate(key, value, null, null);
    }

    public static void addOrUpdate(String key, String hashKey, Object value) {
        addOrUpdate(key, hashKey, value, null, null);
    }

    public static void delete(String key) {
        getRedisTemplate().delete(key);
    }

    public static void deleteKeys(String key) {
        getRedisTemplate().delete(getRedisTemplate().keys(key));
    }

    public static void delete(String key, String hashKey) {
        getRedisTemplate().opsForHash().delete(key, hashKey);
    }

    public static String getString(String key) {
        if (getRedisTemplate().opsForValue().get(key) != null) {
            return (String) getRedisTemplate().opsForValue().get(key);
        }
        return null;
    }

    public static Object get(String key) {
        if (getRedisTemplate().opsForValue().get(key) != null) {
            return getRedisTemplate().opsForValue().get(key);
        }
        return null;
    }

    public static Object get(String key, String hashKey) {
        if (getRedisTemplate().hasKey(key)) {
            return getRedisTemplate().opsForHash().get(key, hashKey);
        }
        return null;
    }
}
