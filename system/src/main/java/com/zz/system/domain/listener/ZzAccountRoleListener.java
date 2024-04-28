package com.zz.system.domain.listener;

import com.zz.common.dto.ZzAccountDTO;
import com.zz.common.dto.ZzRoleDTO;
import com.zz.system.domain.event.ZzAccountRoleEvent;
import com.zz.system.pojo.ZzRole;
import com.zz.system.service.IZzAccountRole;
import com.zz.system.service.IZzRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ZzAccountRoleListener {

    private final IZzAccountRole iZzAccountRole;
    private final IZzRole iZzRole;
    private final ModelMapper modelMapper;

    @EventListener
    public void onAccountRoleEvent(ZzAccountRoleEvent event) {
        ZzAccountDTO accountDTO = event.getAccountDTO();
        Set<Long> roleIds = null;
        if (accountDTO.getZzRoles() != null) {
            roleIds = accountDTO.getZzRoles().stream()
                    .map(ZzRoleDTO::getZzRoleId)
                    .collect(Collectors.toSet());
        }

        // 根据事件类型决定处理逻辑
        switch (event.getEventType()) {
            case ROLE_ASSIGNED:
                // 处理角色分配逻辑
                iZzAccountRole.zzAssignRolesToAccount(accountDTO.getZzAccountId(), roleIds);
                break;
            case ACCOUNT_UPDATED:
                // 调用服务层方法更新账户的角色信息
                iZzAccountRole.zzUpdateAccountRoles(accountDTO.getZzAccountId(), roleIds);
                break;
            case ACCOUNT_DETAILS:
                // 为获取详细信息的账户更新角色信息
                List<ZzRole> roles = iZzRole.list();
                Set<Long> ownedRoleIds = iZzAccountRole.zzFindRoleIdsByAccountId(accountDTO.getZzAccountId());
                List<ZzRoleDTO> roleDTOs = roles.stream().map(role -> {
                    ZzRoleDTO roleDTO = modelMapper.map(role, ZzRoleDTO.class);
                    roleDTO.setZzIsOwned(ownedRoleIds.contains(role.getZzRoleId()));
                    return roleDTO;
                }).collect(Collectors.toList());
                accountDTO.setZzRoles(roleDTOs);
                break;
        }
    }
}
