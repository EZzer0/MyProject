package com.zz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.common.dto.ZzRoleDTO;
import com.zz.system.pojo.ZzRole;

/**
 * 角色表(ZzRole)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzRole extends MPJBaseService<ZzRole> {

    String zzCreate(ZzRoleDTO dto);

    String zzUpdate(ZzRoleDTO dto);

    String zzDelete(Long id);

    String zzToggleDel(Long id);

    ZzRoleDTO zzDetails(Long id);

    IPage<ZzRoleDTO> zzFind(int pageNum, int pageSize, String search);
}
