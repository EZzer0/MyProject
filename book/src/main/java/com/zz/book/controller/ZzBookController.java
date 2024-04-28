package com.zz.book.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zz.book.service.IZzBook;
import com.zz.common.dto.ZzBookDTO;
import com.zz.common.pojo.R;
import com.zz.common.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class ZzBookController {
    private final IZzBook zzBookService;
    private final ModelMapper modelMapper;

    @PostMapping
    public R<String> zzCreate(@ModelAttribute ZzBookDTO zzBookDTO, @RequestParam("imageFile") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String filePath = FileStorageUtil.saveFile(file);
                zzBookDTO.setZzUrl(filePath);
            } catch (IOException e) {
                return R.error("文件上传失败：" + e.getMessage());
            }
        }
        String result = zzBookService.zzCreate(zzBookDTO);
        return R.success(result);
    }


    @DeleteMapping("/{id}")
    public R<String> zzDelete(@PathVariable Long id) {
        String result = zzBookService.zzDelete(id);
        return R.success(result);
    }

    @PutMapping("/{id}")
    public R<String> zzUpdate(@ModelAttribute ZzBookDTO zzBookDTO, @PathVariable Long id, @RequestParam("imageFile") MultipartFile file) {

        zzBookDTO.setZzBookId(id);
        if (!file.isEmpty()) {
            try {
                String filePath = FileStorageUtil.saveFile(file);
                zzBookDTO.setZzUrl(filePath);
            } catch (IOException e) {
                return R.error("文件上传失败: " + e.getMessage());
            }
        }
        String result = zzBookService.zzUpdate(zzBookDTO);
        return R.success(result);
    }


    @DeleteMapping("/del/{id}")
    public R<String> zzToggleDel(@PathVariable Long id) {
        String result = zzBookService.zzToggleDel(id);
        return R.success(result);
    }

    @GetMapping("/list")
    public R<List<ZzBookDTO>> zzFind(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int limit,
            @RequestParam(required = false) String search) {
        IPage<ZzBookDTO> bookPage = zzBookService.zzFind(page, limit, search);
        return R.success("查询成功", bookPage.getRecords(), bookPage.getTotal(), bookPage.getPages());
    }

    @GetMapping("/{id}")
    public R<ZzBookDTO> zzDetails(@PathVariable Long id) {
        ZzBookDTO book = zzBookService.zzDetails(id);
        return R.success("查询成功", book);
    }
}