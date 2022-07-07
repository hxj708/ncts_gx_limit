package com.haoyu.framework.core.base;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author shibo
 */
@Data
public class BaseUser extends BaseEntity {

    private String realName;
    private String userName;
    private String paperworkNo;
    private String password;
    private String avatar;

    public BaseUser() {
    }

    public BaseUser(String id) {
        this.setId(id);
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", realName=" + realName + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof BaseUser) {
            BaseUser baseUser = (BaseUser) obj;
            if (StrUtil.equals(baseUser.getId(), this.getId())) {
                return true;
            }
        }
        return false;
    }

}
