package com.zz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.common.dto.ZzAccountDTO;
import com.zz.system.pojo.ZzAccount;

/**
 * 账号表(ZzAccount)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzAccount extends MPJBaseService<ZzAccount> {


    void zzAuth(String username, String password);

    String zzCreate(ZzAccountDTO dto);

    String zzUpdate(ZzAccountDTO dto);

    String zzDelete(Long zzAccountId);

    String zzToggleDel(Long id);

    ZzAccountDTO zzDetails(Long id);

    IPage<ZzAccountDTO> zzFind(int pageNum, int pageSize, String search);
}

