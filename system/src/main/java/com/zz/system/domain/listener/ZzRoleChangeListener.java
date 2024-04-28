package com.zz.system.domain.listener;

import com.zz.common.dto.ZzPermissionDTO;
import com.zz.common.dto.ZzResourceDTO;
import com.zz.common.dto.ZzRoleDTO;
import com.zz.system.domain.event.ZzRoleChangeEvent;
import com.zz.system.pojo.ZzPermission;
import com.zz.system.pojo.ZzResource;
import com.zz.system.service.IZzPermission;
import com.zz.system.service.IZzResource;
import com.zz.system.service.IZzRolePermission;
import com.zz.system.service.IZzRoleResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ZzRoleChangeListener implements ApplicationListener<ZzRoleChangeEvent> {

    private final IZzRolePermission zzRolePermissionService;
    private final IZzRoleResource zzRoleResourceService;
    private final IZzPermission iZzPermission;
    private final IZzResource iZzResource;
    private final ModelMapper modelMapper;

    @Override
    public void onApplicationEvent(ZzRoleChangeEvent event) {
        ZzRoleDTO roleDTO = event.getRoleDTO();
        Set<Long> permissionIds = roleDTO.getZzPermissions() != null ? roleDTO.getZzPermissions().stream()
                .map(ZzPermissionDTO::getZzPermissionId)
                .collect(Collectors.toSet()) : Collections.emptySet();
        Set<Long> resourceIds = roleDTO.getZzResources() != null ? roleDTO.getZzResources().stream()
                .map(ZzResourceDTO::getZzResourceId)
                .collect(Collectors.toSet()) : Collections.emptySet();

        // 根据事件类型处理逻辑
        switch (event.getEventType()) {
            case ROLE_CREATED:
                // 处理角色创建时的权限和资源分配
                zzRolePermissionService.zzAssignPermissionsToRole(roleDTO.getZzRoleId(), permissionIds);
                zzRoleResourceService.zzAssignResourcesToRole(roleDTO.getZzRoleId(), resourceIds);
                break;
            case ROLE_UPDATED:
                // 更新角色的权限和资源
                zzRolePermissionService.zzUpdateRolePermissions(roleDTO.getZzRoleId(), permissionIds);
                zzRoleResourceService.zzUpdateRoleResources(roleDTO.getZzRoleId(), resourceIds);
                break;
            case ROLE_DETAILS:
                // 获取所有权限和资源并标记拥有状态
                List<ZzPermission> permissions = iZzPermission.list();
                List<ZzResource> resources = iZzResource.list();
                Set<Long> ownedPermissionIds = zzRolePermissionService.zzFindPermissionIdsByRoleId(roleDTO.getZzRoleId());
                Set<Long> ownedResourceIds = zzRoleResourceService.zzFindResourceIdsByRoleId(roleDTO.getZzRoleId());
                // 转换权限和资源到DTO并标记拥有状态
                List<ZzPermissionDTO> permissionDTOs = permissions.stream().map(permission -> {
                    ZzPermissionDTO permissionDTO = modelMapper.map(permission, ZzPermissionDTO.class);
                    permissionDTO.setZzIsOwned(ownedPermissionIds.contains(permission.getZzPermissionId()));
                    return permissionDTO;
                }).collect(Collectors.toList());
                List<ZzResourceDTO> resourceDTOs = resources.stream().map(resource -> {
                    ZzResourceDTO resourceDTO = modelMapper.map(resource, ZzResourceDTO.class);
                    resourceDTO.setZzIsOwned(ownedResourceIds.contains(resource.getZzResourceId()));
                    return resourceDTO;
                }).collect(Collectors.toList());

                roleDTO.setZzPermissions(permissionDTOs);
                roleDTO.setZzResources(resourceDTOs);
                break;
        }
    }
}