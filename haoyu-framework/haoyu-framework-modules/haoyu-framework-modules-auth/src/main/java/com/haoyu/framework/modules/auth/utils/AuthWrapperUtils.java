package com.haoyu.framework.modules.auth.utils;

import com.haoyu.framework.modules.auth.entity.*;
import com.haoyu.framework.modules.auth.entity.Resource;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import cn.hutool.core.map.MapUtil;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 工具类：用于Map转为MyBatisPlus的Wrapper
 * </p>
 *
 * @author haoyu-framework-generator
 * @since 2020-07-20
 */
public class AuthWrapperUtils {

	/**
     * LoginLog
     */
	public static LambdaQueryWrapper<LoginLog> getLoginLogQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<LoginLog> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginLog::getId, ids);
		}
		return wrapper;
	}
	/**
	 * LoginLog
	 */
	public static LambdaUpdateWrapper<LoginLog> getLoginLogUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<LoginLog> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginLog::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * LoginLogStat
     */
	public static LambdaQueryWrapper<LoginLogStat> getLoginLogStatQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<LoginLogStat> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginLogStat::getId, ids);
		}
		return wrapper;
	}
	/**
	 * LoginLogStat
	 */
	public static LambdaUpdateWrapper<LoginLogStat> getLoginLogStatUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<LoginLogStat> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginLogStat::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * Module
     */
	public static LambdaQueryWrapper<Module> getModuleQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<Module> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Module::getId, ids);
		}
		return wrapper;
	}
	/**
	 * Module
	 */
	public static LambdaUpdateWrapper<Module> getModuleUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<Module> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Module::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * Resource
     */
	public static LambdaQueryWrapper<Resource> getResourceQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<Resource> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Resource::getId, ids);
		}
		return wrapper;
	}
	/**
	 * Resource
	 */
	public static LambdaUpdateWrapper<Resource> getResourceUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<Resource> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Resource::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * Role
     */
	public static LambdaQueryWrapper<Role> getRoleQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<Role> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Role::getId, ids);
		}
		return wrapper;
	}
	/**
	 * Role
	 */
	public static LambdaUpdateWrapper<Role> getRoleUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<Role> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(Role::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * Resource
     */
	public static LambdaQueryWrapper<RoleResource> getRoleResourceQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<RoleResource> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(RoleResource::getId, ids);
		}
		return wrapper;
	}
	/**
	 * Resource
	 */
	public static LambdaUpdateWrapper<RoleResource> getRoleResourceUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<RoleResource> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(RoleResource::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * UserRole
     */
	public static LambdaQueryWrapper<UserRole> getUserRoleQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<UserRole> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(UserRole::getId, ids);
		}
		return wrapper;
	}
	/**
	 * UserRole
	 */
	public static LambdaUpdateWrapper<UserRole> getUserRoleUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<UserRole> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(UserRole::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}

	/**
     * LoginUser
     */
	public static LambdaQueryWrapper<LoginUser> getLoginUserQueryWrapper(Map<String, Object> map){
		LambdaQueryWrapper<LoginUser> wrapper = Wrappers.lambdaQuery();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginUser::getId, ids);
		}
		return wrapper;
	}
	/**
	 * LoginUser
	 */
	public static LambdaUpdateWrapper<LoginUser> getLoginUserUpdateWrapper(Map<String, Object> map){
		LambdaUpdateWrapper<LoginUser> wrapper = Wrappers.lambdaUpdate();
		if (map.containsKey("ids")) {
		List<String> ids = (List<String>) map.get("ids");
			wrapper.in(LoginUser::getId, ids);
		}else{
			throw new IllegalArgumentException("key is wrong");
		}
		return wrapper;
	}


}