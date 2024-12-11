package com.trangle.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: zhouyunxiang
 * @Date: 2024/12/10 20:30
 * @Description:
 */
public class JSONUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
    }

    public static String toJSONString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {

        }
        return null;
    }
}
