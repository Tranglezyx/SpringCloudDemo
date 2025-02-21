package com.trangle.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: zhouyunxiang
 * @Date: 2025/2/8 15:12
 * @Description:
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @PostMapping("/upload")
    public String upload(
            @RequestPart("metadata") String metadata,
            @RequestPart("sid") String sid,
            @RequestPart("fileA") MultipartFile fileA,
            @RequestPart("fileB") MultipartFile fileB,
            @RequestPart(value = "fileC", required = false) MultipartFile fileC,
            @RequestPart("fileD") MultipartFile fileD) {
        log.info("--- {}", JSONUtil.toJsonStr(metadata));
        return "success";
    }

    @Data
    public static class FileDTO {
        private String name;
        private int age;
    }
}
