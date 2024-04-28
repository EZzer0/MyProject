package com.zz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.common.dto.ZzPermissionDTO;
import com.zz.system.mapper.ZzPermissionMapper;
import com.zz.system.pojo.ZzPermission;
import com.zz.system.service.IZzPermission;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service("zzPermissionService")
public class ZzPermissionService extends MPJBaseServiceImpl<ZzPermissionMapper, ZzPermission> implements IZzPermission {

    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzPermissionDTO zzPermissionDTO) {
        ZzPermission permission = modelMapper.map(zzPermissionDTO, ZzPermission.class);
        if (save(permission)) {
            return "权限创建成功";
        } else {
            throw new IllegalStateException("创建权限失败。");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("权限未找到或无法删除。");
        }
        return "权限删除成功";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzPermission permission = getById(id);
        if (permission == null) {
            throw new IllegalStateException("未找到要操作的权限");
        }
        permission.setZzDeleted(permission.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        boolean updated = updateById(permission);
        if (!updated) {
            throw new IllegalStateException("更新权限状态失败");
        }
        return permission.getZzDeleted() == 1 ? "权限已逻辑删除" : "权限已重新激活";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzPermissionDTO zzPermissionDTO) {
        ZzPermission existingPermission = getById(zzPermissionDTO.getZzPermissionId());
        if (existingPermission == null) {
            throw new IllegalStateException("指定的权限未找到。");
        }
        modelMapper.map(zzPermissionDTO, existingPermission);
        if (updateById(existingPermission)) {
            return "权限更新成功";
        } else {
            throw new IllegalStateException("更新权限失败。");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public IPage<ZzPermissionDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzPermission> pagination = new Page<>(pageNum, pageSize);
        MPJLambdaWrapper<ZzPermission> wrapper = new MPJLambdaWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like(ZzPermission::getZzPermissionName, search);
        }
        return this.page(pagination, wrapper).convert(entity -> modelMapper.map(entity, ZzPermissionDTO.class));
    }

    @Override
    public ZzPermissionDTO zzDetails(Long id) {
        ZzPermission permission = getById(id);
        if (permission == null) {
            throw new IllegalStateException("指定的权限ID未找到。");
        }
        return modelMapper.map(permission, ZzPermissionDTO.class);
    }
}