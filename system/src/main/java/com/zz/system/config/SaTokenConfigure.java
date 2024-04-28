package com.zz.system.config;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaSessionCustomUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.thymeleaf.dialect.SaTokenDialect;
import com.zz.system.service.IZzAccountRole;
import com.zz.system.service.IZzRolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class SaTokenConfigure {

    private final IZzAccountRole zzAccountRole;
    private final IZzRolePermission zzRolePermission;

    @Bean
    public SaTokenDialect getSaTokenDialect() {
        return new SaTokenDialect();
    }

    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.addStaticVariable("stp", StpUtil.stpLogic);
        return viewResolver;
    }

    @Bean
    public StpInterface getStpInterface() {
        return new StpInterface() {

            @Override
            @SuppressWarnings("unchecked")
            public List<String> getPermissionList(Object loginId, String loginType) {
                SaSession session = StpUtil.getSessionByLoginId(loginId);
                return getRoleList(loginId, loginType).stream()
                        .flatMap(roleCode -> {
                            SaSession roleSession = SaSessionCustomUtil.getSessionById("role-" + roleCode);
                            return ((List<String>) roleSession.get("Permission_List", () -> zzRolePermission.zzGetPermissionListByRoleCode(roleCode))).stream();
                        })
                        .distinct()
                        .collect(Collectors.toList());
            }

            @Override
            public List<String> getRoleList(Object loginId, String loginType) {
                SaSession session = StpUtil.getSessionByLoginId(loginId);
                return session.get("Role_List", () -> zzAccountRole.zzGetRoleCodeListByAccountId(Long.valueOf(String.valueOf(loginId))));


            }
        };
    }

}
