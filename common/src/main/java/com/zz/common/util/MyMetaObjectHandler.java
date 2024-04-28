package com.zz.common.util;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "zzCreateTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "zzCreateAccountId", this::getCurrentAccountId, Long.class);
        this.strictInsertFill(metaObject, "zzDeleted", () -> (byte) 0, Byte.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "zzModifiedTime", Date::new, Date.class);
        this.strictUpdateFill(metaObject, "zzModifiedAccountId", this::getCurrentAccountId, Long.class);
    }

    private Long getCurrentAccountId() {
        // 如果有当前登录的用户，返回当前登录的用户ID
        if (StpUtil.isLogin()) {
            return StpUtil.getLoginIdAsLong();
        }
        return 0L;
    }
}