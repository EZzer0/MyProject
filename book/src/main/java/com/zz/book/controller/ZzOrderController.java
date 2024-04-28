package com.zz.book.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.book.service.IZzOrder;
import com.zz.common.dto.ZzOrderDTO;
import com.zz.common.pojo.R;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class ZzOrderController {
    private final IZzOrder zzOrderService;
    private final ModelMapper modelMapper;

    @PostMapping
    public R<String> zzCreate(@RequestBody ZzOrderDTO zzOrderDTO) {
        String result = zzOrderService.zzCreate(zzOrderDTO);
        return R.success(result);
    }

    @DeleteMapping("/{id}")
    public R<String> zzDelete(@RequestBody ZzOrderDTO zzOrderDTO, @PathVariable String id) {
        String result = zzOrderService.zzDelete(zzOrderDTO);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdate(@RequestBody ZzOrderDTO zzOrderDTO, @PathVariable Long id) {
        if (!Objects.equals(id, zzOrderDTO.getZzOrderId())) {
            return R.error("系统错误，订单ID不匹配");
        }
        String result = zzOrderService.zzUpdate(zzOrderDTO);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzOrderDTO>> zzFindAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit) {
        IPage<ZzOrderDTO> orderPage = zzOrderService.zzFindAll(page, limit);
        return R.success("查询成功", orderPage.getRecords(), orderPage.getTotal(), orderPage.getPages());
    }
}