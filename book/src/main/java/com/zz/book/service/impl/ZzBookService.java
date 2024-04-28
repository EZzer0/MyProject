package com.zz.book.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.zz.book.mapper.ZzBookMapper;
import com.zz.book.pojo.ZzBook;
import com.zz.book.service.IZzBook;
import com.zz.common.dto.ZzBookDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service("zzBookService")
public class ZzBookService extends MPJBaseServiceImpl<ZzBookMapper, ZzBook> implements IZzBook {

    private final ModelMapper modelMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzCreate(ZzBookDTO zzBookDTO) {
        System.out.println(zzBookDTO);
        ZzBook book = modelMapper.map(zzBookDTO, ZzBook.class);
        if (!save(book)) {
            throw new IllegalStateException("书籍创建失败");
        }
        return "书籍创建成功";
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzDelete(Long id) {
        if (!removeById(id)) {
            throw new IllegalStateException("书籍未找到或无法删除。");
        }
        return "书籍删除成功";
    }

    @Override
    public String zzToggleDel(Long id) {
        ZzBook book = getById(id);
        if (book == null) {
            throw new IllegalStateException("未找到要操作的书籍");
        }
        book.setZzDeleted(book.getZzDeleted() == 1 ? (byte) 0 : (byte) 1);
        boolean updated = updateById(book);
        if (!updated) {
            throw new IllegalStateException("更新书籍状态失败");
        }
        return book.getZzDeleted() == 1 ? "书籍已逻辑删除" : "书籍已重新激活";
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String zzUpdate(ZzBookDTO zzBookDTO) {
        ZzBook existingBook = getById(zzBookDTO.getZzBookId());
        if (existingBook == null) {
            throw new IllegalStateException("指定的书籍未找到。");
        }
        modelMapper.map(zzBookDTO, existingBook);
        if (!updateById(existingBook)) {
            throw new IllegalStateException("更新书籍失败");
        }
        return "书籍更新成功";
    }


    @Transactional(readOnly = true)
    @Override
    public IPage<ZzBookDTO> zzFind(int pageNum, int pageSize, String search) {
        Page<ZzBook> pagination = new Page<>(pageNum, pageSize);
        MPJLambdaWrapper<ZzBook> wrapper = new MPJLambdaWrapper<>();
        if (StringUtils.hasText(search)) {
            wrapper.like(ZzBook::getZzTitle, search);
        }
        return this.page(pagination, wrapper).convert(entity -> modelMapper.map(entity, ZzBookDTO.class));
    }

    @Override
    public ZzBookDTO zzDetails(Long id) {
        ZzBook book = getById(id);
        if (book == null) {
            throw new IllegalStateException("指定的书籍ID未找到。");
        }
        return modelMapper.map(book, ZzBookDTO.class);
    }


}