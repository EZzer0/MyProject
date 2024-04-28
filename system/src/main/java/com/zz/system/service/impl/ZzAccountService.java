package com.zz.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.zz.common.dto.ZzAccountDTO;
import com.zz.common.pojo.EventType;
import com.zz.system.domain.event.ZzAccountRoleEvent;
import com.zz.system.mapper.ZzAccountMapper;
import com.zz.system.pojo.ZzAccount;
import com.zz.system.service.IZzAccount;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("zzAccountService")
@RequiredArgsConstructor
public class ZzAccountService extends MPJBaseServiceImpl<ZzAccountMapper, ZzAccount> implements IZzAccount {

    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void zzAuth(String username, String password) {
        ZzAccount account = lambdaQuery().eq(ZzAccount::getZzUsername, username).one();
        if (account == null) {
            throw new IllegalArgumentException("未找到该用户名对应的账户。");
        }
        if (!DigestUtil.md5Hex(password + account.getZzSalt()).equals(account.getZzPassword())) {
            throw new IllegalArgumentException("密码错误。");
        }
        StpUtil.login(account.getZzAccountId()); // 验证通过后登录
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzAccountDTO dto) {
        ZzAccount account = modelMapper.map(dto, ZzAccount.class);
        String[] saltAndPass = zzSaltPass(dto.getZzPassword());
        account.setZzPassword(saltAndPass[1]);
        account.setZzSalt(saltAndPass[0]);
        if (!save(account)) {
            throw new DataIntegrityViolationException("创建账户失败");
        }
        dto.setZzAccountId(account.getZzAccountId());
        eventPublisher.publishEvent(new ZzAccountRoleEvent(this, EventType.ROLE_ASSIGNED, dto));

        return "账户创建成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzAccountDTO dto) {
        ZzAccount existing = getById(dto.getZzAccountId());
        if (existing == null) {
            throw new EmptyResultDataAccessException("账户未找到", 1);
        }
        if (StringUtils.hasText(dto.getZzPassword())) {
            String[] saltAndPass = zzSaltPass(dto.getZzPassword());
            existing.setZzPassword(saltAndPass[1]);
            existing.setZzSalt(saltAndPass[0]);
        }
        modelMapper.map(dto, existing);
        if (!updateById(existing)) {
            throw new DataIntegrityViolationException("更新账户失败");
        }
        eventPublisher.publishEvent(new ZzAccountRoleEvent(this, EventType.ACCOUNT_UPDATED, dto));
        return "账户更新成功";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("账户删除失败");
        }
        return "账户删除成功";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzAccount account = getById(id);
        if (account == null) {
            throw new EmptyResultDataAccessException("未找到要操作的账户", 1);
        }
        account.setZzDeleted(account.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        boolean updated = updateById(account);
        if (!updated) {
            throw new DataIntegrityViolationException("更新账户状态失败");
        }
        return account.getZzDeleted() == 1 ? "账户已逻辑删除" : "账户已重新激活";
    }


    @Transactional(readOnly = true)
    @Override
    public IPage<ZzAccountDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzAccount> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ZzAccount> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.hasText(search)) {
            queryWrapper.like(ZzAccount::getZzUsername, search)
                    .or().like(ZzAccount::getZzRealName, search)
                    .or().like(ZzAccount::getZzEmail, search)
                    .or().like(ZzAccount::getZzPhone, search);
        }

        Page<ZzAccount> resultPage = this.baseMapper.selectPage(page, queryWrapper);
        return resultPage.convert(account -> modelMapper.map(account, ZzAccountDTO.class));
    }


    @Transactional(readOnly = true)
    @Override
    public ZzAccountDTO zzDetails(Long id) {
        ZzAccount account = getById(id);
        if (account == null) {
            throw new EmptyResultDataAccessException("账户不存在: " + id, 1);
        }
        ZzAccountDTO accountDTO = modelMapper.map(account, ZzAccountDTO.class);
        eventPublisher.publishEvent(new ZzAccountRoleEvent(this, EventType.ACCOUNT_DETAILS, accountDTO));
        return accountDTO;
    }


    private String[] zzSaltPass(String pass) {
        String salt = RandomUtil.randomString(16);
        String encoded = DigestUtil.md5Hex(pass + salt);
        return new String[]{salt, encoded};
    }
}
