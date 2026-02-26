package com.trangle.basic.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(200, "操作成功", null);
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "操作成功", data);
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<>(200, message, data);
    }

    public static <T> BaseResponse<T> error() {
        return new BaseResponse<>(500, "操作失败", null);
    }

    public static <T> BaseResponse<T> error(String message) {
        return new BaseResponse<>(500, message, null);
    }

    public static <T> BaseResponse<T> error(Integer code, String message) {
        return new BaseResponse<>(code, message, null);
    }
}
