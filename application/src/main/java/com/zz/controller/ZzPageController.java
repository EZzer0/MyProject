package com.zz.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.zz.book.service.IZzBook;
import com.zz.book.service.IZzCustomer;
import com.zz.book.service.IZzOrder;
import com.zz.common.dto.*;
import com.zz.system.pojo.ZzPermission;
import com.zz.system.pojo.ZzResource;
import com.zz.system.pojo.ZzRole;
import com.zz.system.service.IZzAccount;
import com.zz.system.service.IZzPermission;
import com.zz.system.service.IZzResource;
import com.zz.system.service.IZzRole;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
public class ZzPageController {
    private final IZzAccount zzAccount;
    private final IZzRole zzRole;
    private final IZzPermission zzPermission;
    private final IZzResource zzResource;
    private final IZzBook zzBook;
    private final IZzCustomer zzCustomer;
    private final IZzOrder zzOrder;
    private final ModelMapper modelMapper;


    @GetMapping("/")
    public String defaultPage() {
        return "bookWeb/login";
    }

    @GetMapping("/system/login")
    public String ALoginPage() {
        return "system/login";
    }


    @GetMapping("/BookWeb/login")
    public String loginPage() {
        return "bookWeb/login";
    }

    @ModelAttribute
    public void noCacheResponse(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @SaCheckLogin
    @GetMapping("/BookWeb/index")
    public String indexPage(Model model) {
        return "bookWeb/index";
    }

    @SaCheckLogin
    @GetMapping("/BookWeb/profile")

    public String showProfile() {
        return "bookWeb/layout/profile";
    }

    @SaCheckLogin
    @GetMapping("/BookWeb/cart")
    public String showCart() {
        return "bookWeb/layout/cart";
    }

    @SaCheckLogin
    @GetMapping("/BookWeb/book")
    public String showBooks() {
        return "bookWeb/layout/book";
    }


    @GetMapping("/system/main")
    @SaCheckLogin
    public String mainPage() {
        return "system/main";
    }

    @SaCheckLogin
    @GetMapping("/system/accounts")
    public String accounts() {
        return "system/account/account";
    }

    @SaCheckLogin
    @GetMapping("/system/roles")
    public String roles() {
        return "system/role/role";
    }

    @SaCheckLogin
    @GetMapping("/system/permissions")
    public String permissions() {
        return "system/permission/permission";
    }

    @SaCheckLogin
    @GetMapping("/system/resources")
    public String resources() {
        return "system/resource/resource";
    }

    @SaCheckLogin
    @GetMapping("/system/books")
    public String books() {
        return "system/book/book";
    }

    @SaCheckLogin
    @GetMapping("/system/customers")
    public String customers() {
        return "system/customer/customer";
    }

    @SaCheckLogin
    @GetMapping("/system/orders")
    public String orders() {
        return "system/order/order";
    }


    @SaCheckLogin
    @GetMapping("/accounts/cru")
    public String accounts(@RequestParam(value = "action", required = false) String action,
                           @RequestParam(value = "id", required = false) Long id,
                           Model model) {
        ZzAccountDTO accountDto = new ZzAccountDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                accountDto = zzAccount.zzDetails(id);
                if (accountDto == null) {
                    accountDto = new ZzAccountDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的账户");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        } else if ("add".equals(action)) {
            List<ZzRole> roles = zzRole.list();
            List<ZzRoleDTO> roleDTOs = roles.stream().map(role -> {
                ZzRoleDTO roleDTO = modelMapper.map(role, ZzRoleDTO.class);
                roleDTO.setZzIsOwned(false);
                return roleDTO;
            }).collect(Collectors.toList());
            accountDto.setZzRoles(roleDTOs);
        }
        model.addAttribute("account", accountDto);
        model.addAttribute("action", action);
        return "system/account/CRUaccount";
    }

    @SaCheckLogin
    @GetMapping("/roles/cru")
    public String roles(@RequestParam(value = "action", required = false) String action,
                        @RequestParam(value = "id", required = false) Long id,
                        Model model) {
        ZzRoleDTO roleDto = new ZzRoleDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                roleDto = zzRole.zzDetails(id);
                if (roleDto == null) {
                    roleDto = new ZzRoleDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的角色");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        } else if ("add".equals(action)) {
            List<ZzPermission> permissions = zzPermission.list();
            List<ZzPermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
                ZzPermissionDTO permissionDTO = modelMapper.map(permission, ZzPermissionDTO.class);
                permissionDTO.setZzIsOwned(false);
                return permissionDTO;
            }).collect(Collectors.toList());
            roleDto.setZzPermissions(permissionDTOs);

            List<ZzResource> resources = zzResource.list();
            List<ZzResourceDTO> resourceDTOs = resources.stream().map(resource -> {
                ZzResourceDTO resourceDTO = modelMapper.map(resource, ZzResourceDTO.class);
                resourceDTO.setZzIsOwned(false);
                return resourceDTO;
            }).collect(Collectors.toList());
            roleDto.setZzResources(resourceDTOs);
        }
        model.addAttribute("role", roleDto);
        model.addAttribute("action", action);
        return "system/role/CRUrole";
    }

    @SaCheckLogin
    @GetMapping("/resources/cru")
    public String resources(@RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "id", required = false) Long id,
                            Model model) {
        ZzResourceDTO resourceDto = new ZzResourceDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                resourceDto = zzResource.zzDetails(id);
                if (resourceDto == null) {
                    resourceDto = new ZzResourceDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的资源");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        }
        model.addAttribute("resource", resourceDto);
        model.addAttribute("action", action);
        return "system/resource/CRUresource";
    }

    @SaCheckLogin
    @GetMapping("/permissions/cru")
    public String permissions(@RequestParam(value = "action", required = false) String action,
                              @RequestParam(value = "id", required = false) Long id,
                              Model model) {
        ZzPermissionDTO permissionDto = new ZzPermissionDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                permissionDto = zzPermission.zzDetails(id);
                if (permissionDto == null) {
                    permissionDto = new ZzPermissionDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的权限");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        }
        model.addAttribute("permission", permissionDto);
        model.addAttribute("action", action);
        return "system/permission/CRUpermission";
    }

    @SaCheckLogin
    @GetMapping("/books/cru")
    public String books(@RequestParam(value = "action", required = false) String action,
                        @RequestParam(value = "id", required = false) Long id,
                        Model model) {
        ZzBookDTO bookDto = new ZzBookDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                bookDto = zzBook.zzDetails(id);
                if (bookDto == null) {
                    bookDto = new ZzBookDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的书籍");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        }
        model.addAttribute("book", bookDto);
        model.addAttribute("action", action);
        return "system/book/CRUbook";
    }

    @SaCheckLogin
    @GetMapping("/customers/cru")
    public String customers(@RequestParam(value = "action", required = false) String action,
                            @RequestParam(value = "id", required = false) Long id,
                            Model model) {
        ZzCustomerDTO customerDto = new ZzCustomerDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                customerDto = zzCustomer.zzDetails(id);
                if (customerDto == null) {
                    customerDto = new ZzCustomerDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的客户");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        }
        model.addAttribute("customer", customerDto);
        model.addAttribute("action", action);
        return "system/customer/CRUcustomer";
    }

    @SaCheckLogin
    @GetMapping("/orders/cru")
    public String orders(@RequestParam(value = "action", required = false) String action,
                         @RequestParam(value = "id", required = false) Long id,
                         Model model) {
        ZzOrderDTO orderDto = new ZzOrderDTO();
        if ("edit".equals(action) || "view".equals(action)) {
            if (id != null) {
                orderDto = zzOrder.zzDetails(id);
                if (orderDto == null) {
                    orderDto = new ZzOrderDTO();
                    model.addAttribute("error", "未找到 ID 为 " + id + " 的订单");
                }
            } else {
                model.addAttribute("error", "编辑或查看操作需要 ID");
            }
        }
        model.addAttribute("order", orderDto);
        model.addAttribute("action", action);
        return "system/order/CRUorder";
    }

}
