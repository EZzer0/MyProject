package com.zz.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.book.pojo.ZzCustomer;
import com.zz.common.dto.ZzCustomerDTO;

public interface IZzCustomer extends MPJBaseService<ZzCustomer> {

    void zzDoLogin(String email, String password);

    void zzRegister(String email, String password);

    String zzCreate(ZzCustomerDTO zzCustomerDTO);

    String zzUpdate(ZzCustomerDTO zzCustomerDTO);

    String zzDelete(Long zzCustomerId);

    String zzToggleDel(Long id);

    ZzCustomerDTO zzDetails(Long id);

    IPage<ZzCustomerDTO> zzFind(int pageNum, int pageSize, String search);
}