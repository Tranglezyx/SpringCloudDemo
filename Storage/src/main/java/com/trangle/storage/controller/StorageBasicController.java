package com.trangle.storage.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.storage.entity.StorageBasic;
import com.trangle.storage.service.StorageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/storage")
public class StorageBasicController {

    @Resource
    private StorageService storageService;

    @PostMapping("/addStorage")
    public void addStorage(@RequestBody StorageBasic storageBasic) {
        storageService.addStorage(storageBasic);
    }

    @PostMapping("/subStorage")
    public void subStorage(@RequestBody StorageBasic storageBasic) {
        storageService.subStorage(storageBasic);
    }
}

