package com.zz.book.service;

import com.github.yulichang.base.MPJBaseService;
import com.zz.book.pojo.ZzOrderItem;
import com.zz.common.dto.ZzOrderItemDTO;

import java.util.List;

public interface IZzOrderItem extends MPJBaseService<ZzOrderItem> {

    String zzCreate(ZzOrderItemDTO zzOrderItemDTO);

    String zzDelete(Long id);

    String zzUpdate(ZzOrderItemDTO zzOrderItemDTO);

    List<ZzOrderItemDTO> findByOrderId(Long orderId);
}