package com.zz.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class R<T> {
    private int code;
    private String message;
    private T data;
    private Long count;
    private Long pages;

    // For simple success messages without any data
    public static <T> R<T> success(String message) {
        return R.of(0, message, null, null, null);
    }

    public static <T> R<T> success(String message, T data) {
        return R.of(0, message, data, null, null);
    }

    public static <T> R<T> success(String message, T data, Long count, Long pages) {
        return R.of(0, message, data, count, pages);
    }

    public static <T> R<T> error(String message) {
        return R.of(1, message, null, null, null);
    }
}
