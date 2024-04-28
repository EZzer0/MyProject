package com.zz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.zz.common.dto.ZzRoleDTO;
import com.zz.common.pojo.EventType;
import com.zz.system.domain.event.ZzRoleChangeEvent;
import com.zz.system.mapper.ZzRoleMapper;
import com.zz.system.pojo.ZzRole;
import com.zz.system.service.IZzRole;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@RequiredArgsConstructor
@Service("zzRoleService")
public class ZzRoleService extends MPJBaseServiceImpl<ZzRoleMapper, ZzRole> implements IZzRole {
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzRoleDTO dto) {
        ZzRole role = modelMapper.map(dto, ZzRole.class);
        if (!save(role)) {
            throw new DataIntegrityViolationException("创建角色失败");
        }
        dto.setZzRoleId(role.getZzRoleId());
        eventPublisher.publishEvent(new ZzRoleChangeEvent(this, EventType.ROLE_CREATED, dto));

        return "角色创建成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzRoleDTO dto) {
        ZzRole existing = getById(dto.getZzRoleId());
        if (existing == null) {
            throw new EmptyResultDataAccessException("角色未找到", 1);
        }
        modelMapper.map(dto, existing);
        if (!updateById(existing)) {
            throw new DataIntegrityViolationException("更新角色失败");
        }
        eventPublisher.publishEvent(new ZzRoleChangeEvent(this, EventType.ROLE_UPDATED, dto));
        return "角色更新成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("角色删除失败");
        }
        return "角色删除成功";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzRole role = getById(id);
        if (role == null) {
            throw new EmptyResultDataAccessException("未找到要操作的角色", 1);
        }
        role.setZzDeleted(role.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        boolean updated = updateById(role);
        if (!updated) {
            throw new DataIntegrityViolationException("更新角色状态失败");
        }
        return role.getZzDeleted() == 1 ? "角色已逻辑删除" : "角色已重新激活";
    }

    @Transactional(readOnly = true)
    @Override
    public IPage<ZzRoleDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzRole> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ZzRole> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.hasText(search)) {
            queryWrapper.like(ZzRole::getZzRoleName, search)
                    .or().like(ZzRole::getZzRoleCode, search);
        }

        Page<ZzRole> resultPage = this.baseMapper.selectPage(page, queryWrapper);
        return resultPage.convert(role -> modelMapper.map(role, ZzRoleDTO.class));
    }


    @Transactional(readOnly = true)
    @Override
    public ZzRoleDTO zzDetails(Long id) {
        ZzRole role = getById(id);
        if (role == null) {
            throw new EmptyResultDataAccessException("角色不存在: " + id, 1);
        }
        ZzRoleDTO roleDTO = modelMapper.map(role, ZzRoleDTO.class);
        eventPublisher.publishEvent(new ZzRoleChangeEvent(this, EventType.ROLE_DETAILS, roleDTO));
        return roleDTO;
    }


}