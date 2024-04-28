package com.zz.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.common.dto.ZzAccountDTO;
import com.zz.common.pojo.R;
import com.zz.system.service.IZzAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class ZzAccountController {
    private final IZzAccount zzAccountService;

    @PostMapping
    public R<String> zzCreateAccount(@RequestBody ZzAccountDTO zzAccountDTO) {
        String result = zzAccountService.zzCreate(zzAccountDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDeleteAccount(@PathVariable Long id) {
        System.out.println(id);
        String result = zzAccountService.zzDelete(id);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdateAccount(@RequestBody ZzAccountDTO zzAccountDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzAccountDTO.getZzAccountId())) {
            return R.error("系统错误，账户ID不匹配");
        }
        System.out.println(zzAccountDTO);
        String result = zzAccountService.zzUpdate(zzAccountDTO);
        return R.success(result);
    }

    @DeleteMapping("/del/{id}")
    public R<String> zzFakeDeleteAccount(@PathVariable Long id) {
        String result = zzAccountService.zzToggleDel(id);
        return R.success(result);
    }


    @GetMapping("/list")
    public R<List<ZzAccountDTO>> zzListAccounts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) String search) {
        // 调用服务层方法进行分页查询
        IPage<ZzAccountDTO> accountPage = zzAccountService.zzFind(page, limit, search);

        // 返回成功响应，包括数据列表和分页详情
        return R.success("查询成功", accountPage.getRecords(), accountPage.getTotal(), accountPage.getPages());
    }


    @GetMapping("/{id}")
    public R<ZzAccountDTO> zzGetAccount(@PathVariable Long id) {
        ZzAccountDTO account = zzAccountService.zzDetails(id);
        return R.success("查询成功", account);
    }
}
