package com.zz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.common.dto.ZzResourceDTO;
import com.zz.common.pojo.R;
import com.zz.system.service.IZzResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ZzResourceController {
    private final IZzResource zzResourceService;

    @PostMapping
    public R<String> zzCreateResource(@RequestBody ZzResourceDTO zzResourceDTO) {
        String result = zzResourceService.zzCreate(zzResourceDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDeleteResource(@PathVariable Long id) {
        String result = zzResourceService.zzDelete(id);
        return R.success(result);
    }


    @PutMapping("/{id}")
    public R<String> zzUpdateResource(@RequestBody ZzResourceDTO zzResourceDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzResourceDTO.getZzResourceId())) {
            return R.error("系统错误，资源ID不匹配");
        }
        String result = zzResourceService.zzUpdate(zzResourceDTO);
        return R.success(result);
    }

    @DeleteMapping("/del/{id}")
    public R<String> zzToggleDel(@PathVariable Long id) {
        String result = zzResourceService.zzToggleDel(id);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzResourceDTO>> zzListResources(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String search) {
        // 调用服务层方法进行分页查询
        IPage<ZzResourceDTO> resourcePage = zzResourceService.zzFind(page, limit, search);

        // 返回成功响应，包括数据列表和分页详情
        return R.success("查询成功", resourcePage.getRecords(), resourcePage.getTotal(), resourcePage.getPages());
    }

    @GetMapping("/{id}")
    public R<ZzResourceDTO> zzGetResource(@PathVariable Long id) {
        ZzResourceDTO resource = zzResourceService.zzDetails(id);
        return R.success("查询成功", resource);
    }
}