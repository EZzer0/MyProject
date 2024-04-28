package com.zz.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.zz.book.service.IZzCustomer;
import com.zz.system.pojo.ZzResource;
import com.zz.system.service.IZzAccount;
import com.zz.system.service.IZzRoleResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final IZzAccount zzAccountService;
    private final IZzRoleResource zzRoleResourceService;
    private final IZzCustomer zzCustomerService;

    @PostMapping("/adminLogin")
    public String zzAdminLogin(@RequestParam("zzUsername") String username, @RequestParam("zzPassword") String password, RedirectAttributes redirectAttributes) {
        try {
            zzAccountService.zzAuth(username, password);
            List<ZzResource> menuList = zzRoleResourceService.zzGetCurrentUserResources();
            StpUtil.getSession().set("menuList", menuList);
            StpUtil.getSession().set("zzUsername", username);
            return "redirect:/system/main";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("loginError", e.getMessage());
            return "redirect:/system/login";  // 重定向回登录页面
        }
    }

    @PostMapping("/customerLogin")
    public String zzCustomerLogin(@RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        try {
            zzCustomerService.zzDoLogin(email, password);
            return "redirect:/BookWeb/index";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("loginError", e.getMessage());
            return "redirect:/BookWeb/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("loginError", "登录过程中出现错误: " + e.getMessage());
            return "redirect:BookWeb/login";
        }
    }

    @PostMapping("/register")
    public String zzRegister(@RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("confirm_password") String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("registrationError", "密码和确认密码不匹配");
            return "redirect:/BookWeb/register";
        }
        try {
            zzCustomerService.zzRegister(email, password);
            redirectAttributes.addFlashAttribute("registrationSuccess", "注册成功，请登录");
            return "redirect:/BookWeb/index";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registrationError", "注册失败: " + e.getMessage());
            return "redirect:/BookWeb/register";
        }
    }

    @PostMapping("/logout")
    public String zzLogout() {
        StpUtil.logout();
        return "redirect:/BookWeb/login";
    }
}
