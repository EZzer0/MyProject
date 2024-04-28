package com.zz.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.zz.common.pojo.R;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public R<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("数据完整性冲突：{}", ex.getMessage());
        return R.error("数据完整性冲突：" + ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public R<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("参数错误：{}", ex.getMessage());
        return R.error("参数错误：" + ex.getMessage());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public R<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        log.error("未找到相关资源：{}", ex.getMessage());
        return R.error("未找到相关资源：" + ex.getMessage());
    }


    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleNotLoginException(NotLoginException ex, HttpServletResponse response) throws IOException {
        log.error("用户未登录：{}", ex.getMessage());
        response.sendRedirect("/BookWeb/login");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R<String> handleGeneralException(Exception ex) {
        log.error("Unhandled exception caught: ", ex);
        return R.error("服务器内部错误：" + ex.getClass().getSimpleName() + " - " + Optional.ofNullable(ex.getMessage()).orElse("No detailed message"));
    }


}
