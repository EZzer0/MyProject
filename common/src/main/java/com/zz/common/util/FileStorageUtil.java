package com.zz.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileStorageUtil {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 支持最大为10MB的文件

    /**
     * 保存文件到指定目录并返回文件的访问URL
     *
     * @param file 要保存的文件
     * @return 文件的完整路径
     */
    public static String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalStateException("上传的文件不能为空。");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalStateException("文件过大，单个文件大小不能超过10MB。");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && !originalFilename.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|pdf|doc|docx|xls|xlsx))$)")) {
            throw new IllegalStateException("文件格式不正确，支持的格式包括JPG, PNG, GIF, BMP, PDF, DOC, DOCX, XLS, XLSX。");
        }

        // 定义文件存储的路径
        String storageDirectory = "D:\\BPP\\Code\\IntelliJ IDEA\\zzPro\\picture";
        String fileName = UUID.randomUUID() + "_" + originalFilename;

        // 确保上传目录存在
        Path directoryPath = Paths.get(storageDirectory).toAbsolutePath();
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }

        // 保存文件
        Path filePath = directoryPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 返回文件的完整路径
        return fileName;
    }
}
