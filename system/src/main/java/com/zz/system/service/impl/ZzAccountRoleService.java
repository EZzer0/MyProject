package com.zz.system.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.system.mapper.ZzAccountRoleMapper;
import com.zz.system.mapper.ZzRoleMapper;
import com.zz.system.pojo.ZzAccountRole;
import com.zz.system.pojo.ZzRole;
import com.zz.system.service.IZzAccountRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 账户角色关联表(ZzAccountRole)表服务实现类
 *
 * @author zz
 * @since 2024-04-17 20:16:03
 */
@RequiredArgsConstructor
@Service("zzAccountRoleService")
public class ZzAccountRoleService extends MPJBaseServiceImpl<ZzAccountRoleMapper, ZzAccountRole> implements IZzAccountRole {

    private final ZzRoleMapper zzRoleMapper;


    @Transactional
    @Override
    public void zzAssignRolesToAccount(Long accountId, Set<Long> roleIds) {
        remove(new MPJLambdaWrapper<ZzAccountRole>().eq(ZzAccountRole::getZzAccountId, accountId));
        List<ZzAccountRole> accountRoles = roleIds.stream()
                .map(roleId -> new ZzAccountRole(accountId, roleId))
                .collect(Collectors.toList());

        saveBatch(accountRoles);
    }

    @Override
    @Transactional
    public void zzUpdateAccountRoles(Long accountId, Set<Long> roleIds) {
        remove(new MPJLambdaWrapper<ZzAccountRole>().eq(ZzAccountRole::getZzAccountId, accountId));
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        List<ZzAccountRole> accountRoles = roleIds.stream()
                .map(roleId -> new ZzAccountRole(accountId, roleId))
                .collect(Collectors.toList());
        saveBatch(accountRoles);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Long> zzFindRoleIdsByAccountId(Long accountId) {
        MPJLambdaWrapper<ZzAccountRole> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(ZzAccountRole::getZzRoleId)
                .eq(ZzAccountRole::getZzAccountId, accountId);

        // 执行查询并收集结果
        return selectJoinList(ZzAccountRole.class, wrapper).stream()
                .map(ZzAccountRole::getZzRoleId)
                .collect(Collectors.toSet());
    }


    @Transactional(readOnly = true)
    @Override
    public List<String> zzGetRoleCodeListByAccountId(Long accountId) {
        return selectJoinList(String.class,
                new MPJLambdaWrapper<ZzAccountRole>()
                        .select(ZzRole::getZzRoleCode)
                        .leftJoin(ZzRole.class, ZzRole::getZzRoleId, ZzAccountRole::getZzRoleId)
                        .eq(ZzAccountRole::getZzAccountId, accountId)
        );
    }


}

