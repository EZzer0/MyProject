package com.zz.book.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.book.service.IZzCustomer;
import com.zz.common.dto.ZzCustomerDTO;
import com.zz.common.pojo.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class ZzCustomerController {
    private final IZzCustomer zzCustomerService;


    @PostMapping
    public R<String> zzCreate(@RequestBody ZzCustomerDTO zzCustomerDTO) {
        String result = zzCustomerService.zzCreate(zzCustomerDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDelete(@PathVariable Long id) {
        String result = zzCustomerService.zzDelete(id);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdate(@RequestBody ZzCustomerDTO zzCustomerDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzCustomerDTO.getZzCustomerId())) {
            return R.error("系统错误，客户ID不匹配");
        }
        String result = zzCustomerService.zzUpdate(zzCustomerDTO);
        return R.success(result);
    }

    @DeleteMapping("/del/{id}")
    public R<String> zzToggleDel(@PathVariable Long id) {
        String result = zzCustomerService.zzToggleDel(id);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzCustomerDTO>> zzFind(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) String search) {
        IPage<ZzCustomerDTO> customerPage = zzCustomerService.zzFind(page, limit, search);
        return R.success("查询成功", customerPage.getRecords(), customerPage.getTotal(), customerPage.getPages());
    }

    @GetMapping("/{id}")
    public R<ZzCustomerDTO> zzDetails(@PathVariable Long id) {
        ZzCustomerDTO customer = zzCustomerService.zzDetails(id);
        return R.success("查询成功", customer);
    }

    @GetMapping("/current")
    public R<ZzCustomerDTO> zzCurrent() {
        Long id = StpUtil.getLoginIdAsLong();
        ZzCustomerDTO customer = zzCustomerService.zzDetails(id);
        return R.success("查询成功", customer);
    }
}