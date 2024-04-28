package com.zz.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.common.util.ZzTreeBuilder;
import com.zz.system.mapper.ZzRoleResourceMapper;
import com.zz.system.pojo.ZzResource;
import com.zz.system.pojo.ZzRole;
import com.zz.system.pojo.ZzRoleResource;
import com.zz.system.service.IZzRoleResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("zzRoleResourceService")
public class ZzRoleResourceService extends MPJBaseServiceImpl<ZzRoleResourceMapper, ZzRoleResource> implements IZzRoleResource {


    private final ZzTreeBuilder<ZzResource> zzTreeBuilder = new ZzTreeBuilder<>(
            ZzResource::getZzResourceId,
            ZzResource::getZzParentId,
            ZzResource::setZzChildren
    );


    @Transactional
    @Override
    public void zzAssignResourcesToRole(Long roleId, Set<Long> resourceIds) {
        remove(new MPJLambdaWrapper<ZzRoleResource>().eq(ZzRoleResource::getZzRoleId, roleId));
        List<ZzRoleResource> roleResources = resourceIds.stream()
                .map(resourceId -> new ZzRoleResource(roleId, resourceId))
                .collect(Collectors.toList());
        saveBatch(roleResources);
    }

    @Transactional
    @Override
    public void zzUpdateRoleResources(Long roleId, Set<Long> resourceIds) {
        remove(new MPJLambdaWrapper<ZzRoleResource>().eq(ZzRoleResource::getZzRoleId, roleId));
        if (resourceIds != null && !resourceIds.isEmpty()) {
            List<ZzRoleResource> roleResources = resourceIds.stream()
                    .map(resourceId -> new ZzRoleResource(roleId, resourceId))
                    .collect(Collectors.toList());
            saveBatch(roleResources);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Long> zzFindResourceIdsByRoleId(Long roleId) {
        MPJLambdaWrapper<ZzRoleResource> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(ZzRoleResource::getZzResourceId)
                .eq(ZzRoleResource::getZzRoleId, roleId);
        return selectJoinList(ZzRoleResource.class, wrapper).stream()
                .map(ZzRoleResource::getZzResourceId)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ZzResource> zzGetCurrentUserResources() {
        // 使用Sa-Token获取当前用户的角色代码集合
        List<String> roleCodes = StpUtil.getRoleList();
        // 构建连表查询
        MPJLambdaWrapper<ZzRoleResource> wrapper = new MPJLambdaWrapper<>();
        wrapper.selectAll(ZzResource.class)
                .in(ZzRole::getZzRoleCode, roleCodes)
                .leftJoin(ZzRole.class, ZzRole::getZzRoleId, ZzRoleResource::getZzRoleId)
                .leftJoin(ZzResource.class, ZzResource::getZzResourceId, ZzRoleResource::getZzResourceId);

        List<ZzResource> resources = selectJoinList(ZzResource.class, wrapper);

        // 使用ZzTreeBuilder构建树结构
        return zzTreeBuilder.zzBuildTree(resources, null);
    }


}
