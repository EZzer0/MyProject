package com.zz.book.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.base.MPJBaseService;
import com.zz.book.pojo.ZzBook;
import com.zz.common.dto.ZzBookDTO;


public interface IZzBook extends MPJBaseService<ZzBook> {

    String zzCreate(ZzBookDTO zzBookDTO);

    String zzUpdate(ZzBookDTO zzBookDTO);

    String zzDelete(Long zzBookId);

    String zzToggleDel(Long id);

    ZzBookDTO zzDetails(Long id);

    IPage<ZzBookDTO> zzFind(int pageNum, int pageSize, String search);
}