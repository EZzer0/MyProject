package com.zz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.common.dto.ZzResourceDTO;
import com.zz.system.mapper.ZzResourceMapper;
import com.zz.system.pojo.ZzResource;
import com.zz.system.service.IZzResource;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service("zzResourceService")
public class ZzResourceService extends MPJBaseServiceImpl<ZzResourceMapper, ZzResource> implements IZzResource {

    private final ModelMapper modelMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzResourceDTO zzResourceDTO) {
        ZzResource resource = modelMapper.map(zzResourceDTO, ZzResource.class);
        if (save(resource)) {
            return "资源创建成功";
        } else {
            throw new IllegalStateException("创建资源失败。");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("资源未找到或无法删除。");
        }
        return "资源删除成功";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzResource resource = getById(id);
        if (resource == null) {
            throw new IllegalStateException("未找到要操作的资源");
        }
        resource.setZzDeleted(resource.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        boolean updated = updateById(resource);
        if (!updated) {
            throw new IllegalStateException("更新资源状态失败");
        }
        return resource.getZzDeleted() == 1 ? "资源已逻辑删除" : "资源已重新激活";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzResourceDTO zzResourceDTO) {
        ZzResource existingResource = getById(zzResourceDTO.getZzResourceId());
        if (existingResource == null) {
            throw new IllegalStateException("指定的资源未找到。");
        }
        modelMapper.map(zzResourceDTO, existingResource);
        if (updateById(existingResource)) {
            return "资源更新成功";
        } else {
            throw new IllegalStateException("更新资源失败。");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public IPage<ZzResourceDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzResource> pagination = new Page<>(pageNum, pageSize);
        MPJLambdaWrapper<ZzResource> wrapper = new MPJLambdaWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like(ZzResource::getZzResourceName, search);
        }
        return this.page(pagination, wrapper).convert(entity -> modelMapper.map(entity, ZzResourceDTO.class));
    }

    @Override
    public ZzResourceDTO zzDetails(Long id) {
        ZzResource resource = getById(id);
        if (resource == null) {
            throw new IllegalStateException("指定的资源ID未找到。");
        }
        return modelMapper.map(resource, ZzResourceDTO.class);
    }
}