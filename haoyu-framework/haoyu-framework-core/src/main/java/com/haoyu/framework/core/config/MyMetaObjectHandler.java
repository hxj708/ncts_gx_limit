package com.haoyu.framework.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.haoyu.framework.core.base.BaseEntity;
import com.haoyu.framework.core.base.BaseUser;
import com.haoyu.framework.utils.ThreadContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author shibo
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		//取当前登录人信息，待优化
		BaseUser baseUser = ThreadContext.getUser();
		if (baseUser != null) {
			if (metaObject.hasSetter("createUser")) {
				this.strictInsertFill(metaObject, "createUser", String.class, baseUser.getId());
			}
			if (metaObject.hasSetter("updateUser")) {
				this.strictInsertFill(metaObject, "updateUser", String.class, baseUser.getId());
			}
		}
		if(metaObject.getValue("createTime") == null){
			if (metaObject.hasSetter("createTime")) {
				this.strictInsertFill(metaObject, "createTime", Date.class, new Date());
			}
		}
		if(metaObject.getValue("updateTime") == null) {
			if (metaObject.hasSetter("updateTime")) {
				this.strictInsertFill(metaObject, "updateTime", Date.class, new Date());
			}
		}
		if (metaObject.hasSetter("isDeleted")) {
			this.strictInsertFill(metaObject, "isDeleted", String.class, BaseEntity.IS_DELETED_NO);
		}
//		if (metaObject.hasSetter("version")) {
//			this.strictInsertFill(metaObject, "version", Integer.class, 1);
//		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		//取当前登录人信息，待优化
		BaseUser baseUser = ThreadContext.getUser();
		if (baseUser != null) {
			if (metaObject.hasSetter("updateUser")) {
				this.strictUpdateFill(metaObject, "updateUser", String.class, baseUser.getId());
			}
		}
		if (metaObject.hasSetter("updateTime")) {
			this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
		}
	}
}
