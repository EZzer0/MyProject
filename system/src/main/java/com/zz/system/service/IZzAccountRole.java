package com.zz.system.service;

import com.github.yulichang.base.MPJBaseService;
import com.zz.system.pojo.ZzAccountRole;

import java.util.List;
import java.util.Set;

/**
 * 账户角色关联表(ZzAccountRole)表服务接口
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
public interface IZzAccountRole extends MPJBaseService<ZzAccountRole> {

    void zzAssignRolesToAccount(Long accountId, Set<Long> roleIds);

    void zzUpdateAccountRoles(Long accountId, Set<Long> roleIds);

    Set<Long> zzFindRoleIdsByAccountId(Long accountId);

    List<String> zzGetRoleCodeListByAccountId(Long accountId);
}

