package com.zz.system.service;

import com.github.yulichang.base.MPJBaseService;
import com.zz.system.pojo.ZzRolePermission;

import java.util.List;
import java.util.Set;

/**
 * 角色权限关联表(ZzRolePermission)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzRolePermission extends MPJBaseService<ZzRolePermission> {

    void zzAssignPermissionsToRole(Long roleId, Set<Long> permissionIds);

    void zzUpdateRolePermissions(Long roleId, Set<Long> permissionIds);

    Set<Long> zzFindPermissionIdsByRoleId(Long roleId);

    List<String> zzGetPermissionListByRoleCode(String roleCode);

}

