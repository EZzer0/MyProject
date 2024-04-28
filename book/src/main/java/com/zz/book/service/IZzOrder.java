package com.zz.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.book.pojo.ZzOrder;
import com.zz.common.dto.ZzOrderDTO;


public interface IZzOrder extends MPJBaseService<ZzOrder> {

    String zzCreate(ZzOrderDTO zzOrderDTO);


    String zzDelete(ZzOrderDTO orderDTO);


    String zzUpdate(ZzOrderDTO zzOrderDTO);

    ZzOrderDTO zzDetails(Long id);

    IPage<ZzOrderDTO> zzFindAll(int current, int size);
}