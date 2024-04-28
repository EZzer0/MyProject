package com.zz.system.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.system.mapper.ZzPermissionMapper;
import com.zz.system.mapper.ZzRolePermissionMapper;
import com.zz.system.pojo.ZzPermission;
import com.zz.system.pojo.ZzRole;
import com.zz.system.pojo.ZzRolePermission;
import com.zz.system.service.IZzRolePermission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("zzRolePermissionService")
public class ZzRolePermissionService extends MPJBaseServiceImpl<ZzRolePermissionMapper, ZzRolePermission> implements IZzRolePermission {

    private final ZzPermissionMapper zzPermissionMapper;

    @Transactional
    @Override
    public void zzAssignPermissionsToRole(Long roleId, Set<Long> permissionIds) {
        // Remove all existing permissions assigned to the role before assigning new ones
        remove(new MPJLambdaWrapper<ZzRolePermission>().eq(ZzRolePermission::getZzRoleId, roleId));
        List<ZzRolePermission> rolePermissions = permissionIds.stream()
                .map(permissionId -> new ZzRolePermission(roleId, permissionId))
                .collect(Collectors.toList());
        // Save the new list of permissions
        saveBatch(rolePermissions);
    }

    @Transactional
    @Override
    public void zzUpdateRolePermissions(Long roleId, Set<Long> permissionIds) {
        // First, remove the existing permissions linked to the role
        remove(new MPJLambdaWrapper<ZzRolePermission>().eq(ZzRolePermission::getZzRoleId, roleId));
        // Proceed only if there are new permissions to add
        if (permissionIds != null && !permissionIds.isEmpty()) {
            List<ZzRolePermission> rolePermissions = permissionIds.stream()
                    .map(permissionId -> new ZzRolePermission(roleId, permissionId))
                    .collect(Collectors.toList());
            // Save the updated permissions
            saveBatch(rolePermissions);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Long> zzFindPermissionIdsByRoleId(Long roleId) {
        MPJLambdaWrapper<ZzRolePermission> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(ZzRolePermission::getZzPermissionId)
                .eq(ZzRolePermission::getZzRoleId, roleId);

        // Execute the query and collect the results
        return selectJoinList(ZzRolePermission.class, wrapper).stream()
                .map(ZzRolePermission::getZzPermissionId)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    @Override
    public List<String> zzGetPermissionListByRoleCode(String roleCode) {
        return selectJoinList(String.class,
                new MPJLambdaWrapper<ZzRolePermission>()
                        .select(ZzPermission::getZzPermissionCode)
                        .leftJoin(ZzRole.class, ZzRole::getZzRoleId, ZzRolePermission::getZzRoleId)
                        .leftJoin(ZzPermission.class, ZzPermission::getZzPermissionId, ZzRolePermission::getZzPermissionId)
                        .eq(ZzRole::getZzRoleCode, roleCode)
        );
    }

}
