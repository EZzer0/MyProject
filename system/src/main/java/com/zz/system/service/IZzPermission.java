package com.zz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.common.dto.ZzPermissionDTO;
import com.zz.system.pojo.ZzPermission;

/**
 * 权限表(ZzPermission)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzPermission extends MPJBaseService<ZzPermission> {

    String zzCreate(ZzPermissionDTO dto);

    String zzUpdate(ZzPermissionDTO dto);

    String zzDelete(Long id);

    String zzToggleDel(Long id);

    ZzPermissionDTO zzDetails(Long id);

    IPage<ZzPermissionDTO> zzFind(int pageNum, int pageSize, String search);
}

