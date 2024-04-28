package com.zz.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.common.dto.ZzResourceDTO;
import com.zz.system.pojo.ZzResource;

/**
 * 资源表(ZzResource)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzResource extends MPJBaseService<ZzResource> {

    String zzCreate(ZzResourceDTO zzResourceDTO);

    String zzDelete(Long id);

    String zzUpdate(ZzResourceDTO zzResourceDTO);

    String zzToggleDel(Long id);

    IPage<ZzResourceDTO> zzFind(int page, int size, String search);

    ZzResourceDTO zzDetails(Long id);
}
