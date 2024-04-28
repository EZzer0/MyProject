package com.zz.system.service;

import com.github.yulichang.base.MPJBaseService;
import com.zz.system.pojo.ZzResource;
import com.zz.system.pojo.ZzRoleResource;

import java.util.List;
import java.util.Set;

/**
 * 角色资源关联表(ZzRoleResource)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:01
 */
public interface IZzRoleResource extends MPJBaseService<ZzRoleResource> {

    void zzAssignResourcesToRole(Long roleId, Set<Long> resourceIds);

    void zzUpdateRoleResources(Long roleId, Set<Long> resourceIds);

    Set<Long> zzFindResourceIdsByRoleId(Long roleId);

    List<ZzResource> zzGetCurrentUserResources();
}

