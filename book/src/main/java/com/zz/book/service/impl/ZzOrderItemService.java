package com.zz.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.zz.book.mapper.ZzOrderItemMapper;
import com.zz.book.pojo.ZzOrderItem;
import com.zz.book.service.IZzOrderItem;
import com.zz.common.dto.ZzOrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service("zzOrderItemService")
public class ZzOrderItemService extends MPJBaseServiceImpl<ZzOrderItemMapper, ZzOrderItem> implements IZzOrderItem {

    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzOrderItemDTO zzOrderItemDTO) {
        ZzOrderItem zzOrderItem = modelMapper.map(zzOrderItemDTO, ZzOrderItem.class);
        if (save(zzOrderItem)) {
            return "订单项创建成功";
        } else {
            throw new IllegalStateException("创建订单项失败。");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("订单项未找到或无法删除。");
        }
        return "订单项删除成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzOrderItemDTO zzOrderItemDTO) {
        ZzOrderItem existingOrderItem = getById(zzOrderItemDTO.getZzItemId());
        if (existingOrderItem == null) {
            throw new IllegalStateException("指定的订单项未找到。");
        }
        modelMapper.map(zzOrderItemDTO, existingOrderItem);
        if (updateById(existingOrderItem)) {
            return "订单项更新成功";
        } else {
            throw new IllegalStateException("更新订单项失败。");
        }
    }

    @Override
    public List<ZzOrderItemDTO> findByOrderId(Long orderId) {
        QueryWrapper<ZzOrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("zz_order_id", orderId);
        List<ZzOrderItem> zzOrderItems = list(queryWrapper);
        return zzOrderItems.stream()
                .map(item -> modelMapper.map(item, ZzOrderItemDTO.class))
                .collect(Collectors.toList());
    }

}