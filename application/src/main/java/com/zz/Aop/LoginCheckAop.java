package com.zz.Aop;


import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginCheckAop {

    @Pointcut("execution(* com.zz.system.controller.*.*(..))  && " +
            "execution(* com.zz.book.controller.*.*(..)) && !" +
            "execution(* com.zz.controller.AuthController.zzAdminLogin()) && !" +
            "execution(* com.zz.controller.AuthController.zzCustomerLogin()) && !" +
            "execution(* com.zz.controller.ZzPageController.defaultPage()) && !" +
            "execution(* com.zz.controller.ZzPageController.ALoginPage()) && !" +
            "execution(* com.zz.controller.ZzPageController.loginPage())")
    public void controllerMethods() {
    }


    @Before("controllerMethods()")
    public void checkLogin() {
        if (!StpUtil.isLogin()) {
            throw new NotLoginException(StpUtil.getLoginType(), NotLoginException.NOT_TOKEN, "用户未登录");
        }
    }


}
