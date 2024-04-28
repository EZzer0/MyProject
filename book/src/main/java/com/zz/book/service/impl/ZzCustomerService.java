package com.zz.book.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.book.mapper.ZzCustomerMapper;
import com.zz.book.pojo.ZzCustomer;
import com.zz.book.service.IZzCustomer;
import com.zz.common.dto.ZzCustomerDTO;
import com.zz.common.util.CryptoUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service("zzCustomerService")
public class ZzCustomerService extends MPJBaseServiceImpl<ZzCustomerMapper, ZzCustomer> implements IZzCustomer {

    private final ModelMapper modelMapper;

    @Override
    public void zzDoLogin(String email, String password) {
        ZzCustomer customer = lambdaQuery().eq(ZzCustomer::getZzEmail, email).one();
        if (customer == null) {
            throw new IllegalArgumentException("未找到该邮箱对应的账户。");
        }
        if (CryptoUtil.verifyPassword(password, customer.getZzPassword())) {

            StpUtil.login(customer.getZzCustomerId());
        } else {
            throw new IllegalArgumentException("密码错误。"); // 登录失败，抛出异常
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void zzRegister(String email, String password) {
        boolean emailExists = lambdaQuery().eq(ZzCustomer::getZzEmail, email).exists();
        if (emailExists) {
            throw new IllegalArgumentException("邮箱已被注册。");
        }
        ZzCustomer newCustomer = new ZzCustomer();
        newCustomer.setZzEmail(email);
        String salt = CryptoUtil.generateSalt();
        newCustomer.setZzPassword(CryptoUtil.hashPassword(password, salt));

        if (!save(newCustomer)) {
            throw new IllegalStateException("注册客户失败。");
        }

        StpUtil.login(newCustomer.getZzCustomerId());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzCustomerDTO zzCustomerDTO) {
        ZzCustomer zzCustomer = modelMapper.map(zzCustomerDTO, ZzCustomer.class);
        String salt = CryptoUtil.generateSalt();
        zzCustomer.setZzPassword(CryptoUtil.hashPassword(zzCustomer.getZzPassword(), salt)); // 设置加密密码
        if (!save(zzCustomer)) {
            throw new IllegalStateException("创建客户失败。");
        }
        return "客户创建成功。";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzCustomerDTO zzCustomerDTO) {
        ZzCustomer existingCustomer = getById(zzCustomerDTO.getZzCustomerId());
        if (existingCustomer == null) {
            throw new IllegalArgumentException("指定的客户未找到。");
        }
        if (StringUtils.hasText(zzCustomerDTO.getZzPassword())) {
            String salt = CryptoUtil.generateSalt();
            zzCustomerDTO.setZzPassword(CryptoUtil.hashPassword(zzCustomerDTO.getZzPassword(), salt));
        }
        modelMapper.map(zzCustomerDTO, existingCustomer);
        if (!updateById(existingCustomer)) {
            throw new IllegalStateException("更新客户失败。");
        }
        return "客户更新成功。";
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalArgumentException("客户未找到或无法删除。");
        }
        return "客户删除成功。";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzCustomer customer = getById(id);
        if (customer == null) {
            throw new IllegalArgumentException("未找到要操作的客户。");
        }
        customer.setZzDeleted(customer.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        if (!updateById(customer)) {
            throw new IllegalStateException("更新客户状态失败。");
        }
        return customer.getZzDeleted() == 1 ? "客户已逻辑删除。" : "客户已重新激活。";
    }

    @Override
    public ZzCustomerDTO zzDetails(Long id) {
        ZzCustomer customer = getById(id);
        if (customer == null) {
            throw new IllegalStateException("指定的客户ID未找到。");
        }
        return modelMapper.map(customer, ZzCustomerDTO.class);
    }


    @Transactional(readOnly = true)
    @Override
    public IPage<ZzCustomerDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzCustomer> pagination = new Page<>(pageNum, pageSize);
        MPJLambdaWrapper<ZzCustomer> wrapper = new MPJLambdaWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like(ZzCustomer::getZzEmail, search);
        }
        return this.page(pagination, wrapper).convert(entity -> modelMapper.map(entity, ZzCustomerDTO.class));
    }
}
