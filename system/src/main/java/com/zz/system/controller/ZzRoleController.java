package com.zz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.common.dto.ZzRoleDTO;
import com.zz.common.pojo.R;
import com.zz.system.service.IZzRole;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class ZzRoleController {
    private final IZzRole zzRoleService;

    @PostMapping
    public R<String> zzCreate(@RequestBody ZzRoleDTO zzRoleDTO) {
        String result = zzRoleService.zzCreate(zzRoleDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDelete(@PathVariable Long id) {
        String result = zzRoleService.zzDelete(id);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdate(@RequestBody ZzRoleDTO zzRoleDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzRoleDTO.getZzRoleId())) {
            return R.error("系统错误，角色ID不匹配");
        }
        String result = zzRoleService.zzUpdate(zzRoleDTO);
        return R.success(result);
    }

    @DeleteMapping("/del/{id}")
    public R<String> zzToggleDel(@PathVariable Long id) {
        String result = zzRoleService.zzToggleDel(id);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzRoleDTO>> zzListRoles(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        // 调用服务层方法进行分页查询
        IPage<ZzRoleDTO> rolesPage = zzRoleService.zzFind(page, limit, search);

        // 返回成功响应，包括数据列表和分页详情
        return R.success("查询成功", rolesPage.getRecords(), rolesPage.getTotal(), rolesPage.getPages());
    }


    @GetMapping("/{id}")
    public R<ZzRoleDTO> zzDetails(@PathVariable Long id) {
        ZzRoleDTO role = zzRoleService.zzDetails(id);
        return R.success("查询成功", role);
    }
}