package com.zz.common.Aop;


import cn.dev33.satoken.stp.StpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CheckIsMyAop {

    @Pointcut("@annotation(com.zz.common.pojo.CheckIsMy)")
    public void checkIsMyMethods() {
    }

    @Before("checkIsMyMethods()")
    public void checkIsMy(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof Long accountId) {
            Long currentAccountId = StpUtil.getLoginIdAsLong();
            if (accountId.equals(currentAccountId)) {
                throw new RuntimeException("您不能对自己的账户或角色进行修改或删除操作");
            }
        }
    }
}
