package com.trangle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> {

    private int status;
    private T data;
    private String msg;

    public static ResultDTO success() {
        return new ResultDTO(200, null, null);
    }

    public static ResultDTO success(Object data) {
        return new ResultDTO(200, data, null);
    }

    public static ResultDTO error(String msg) {
        return new ResultDTO(500, null, msg);
    }

    public static ResultDTO error() {
        return new ResultDTO(500, null, null);
    }
}
