package com.zz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.common.dto.ZzPermissionDTO;
import com.zz.common.pojo.R;
import com.zz.system.service.IZzPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
public class ZzPermissionController {
    private final IZzPermission zzPermissionService;

    @PostMapping
    public R<String> zzCreate(@RequestBody ZzPermissionDTO zzPermissionDTO) {
        String result = zzPermissionService.zzCreate(zzPermissionDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDelete(@PathVariable Long id) {
        String result = zzPermissionService.zzDelete(id);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdate(@RequestBody ZzPermissionDTO zzPermissionDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzPermissionDTO.getZzPermissionId())) {
            return R.error("系统错误，权限ID不匹配");
        }
        String result = zzPermissionService.zzUpdate(zzPermissionDTO);
        return R.success(result);
    }

    @DeleteMapping("/del/{id}")
    public R<String> zzToggleDel(@PathVariable Long id) {
        String result = zzPermissionService.zzToggleDel(id);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzPermissionDTO>> zzListPermissions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        // 调用服务层方法进行分页查询
        IPage<ZzPermissionDTO> permissionPage = zzPermissionService.zzFind(page, limit, search);

        // 返回成功响应，包括数据列表和分页详情
        return R.success("查询成功", permissionPage.getRecords(), permissionPage.getTotal(), permissionPage.getPages());
    }

    @GetMapping("/{id}")
    public R<ZzPermissionDTO> zzDetails(@PathVariable Long id) {
        ZzPermissionDTO permission = zzPermissionService.zzDetails(id);
        return R.success("查询成功", permission);
    }
}