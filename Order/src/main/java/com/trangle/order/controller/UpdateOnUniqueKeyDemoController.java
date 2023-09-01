package com.trangle.order.controller;

import com.trangle.dto.ResultDTO;
import com.trangle.order.entity.UpdateOnUniqueKeyDemo;
import com.trangle.order.mapper.UpdateOnUniqueKeyDemoMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/updateOnUniqueKeyDemo")
public class UpdateOnUniqueKeyDemoController {

    @Resource
    private UpdateOnUniqueKeyDemoMapper updateOnUniqueKeyDemoMapper;

    private static final int size = 50;
    private static final int num = 10;

    private static final Executor executor = Executors.newFixedThreadPool(size);

    @GetMapping("/test")
    public ResultDTO<Boolean> test() throws InterruptedException {
        updateOnUniqueKeyDemoMapper.insertListOnDuplicateKeyUpdateUniqueKey(new UpdateOnUniqueKeyDemo());
        return ResultDTO.success(true);
    }

    @GetMapping("/insertListOnDuplicateKeyUpdateUniqueKey")
    public ResultDTO<Boolean> insertListOnDuplicateKeyUpdateUniqueKey() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            executor.execute(() -> insertListOnDuplicateKeyUpdateUniqueKeyRun());
        }
        return ResultDTO.success(true);
    }

    @GetMapping("/batchInsertListOnDuplicateKeyUpdateUniqueKey")
    public ResultDTO<Boolean> batchInsertListOnDuplicateKeyUpdateUniqueKey() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            executor.execute(() -> batchInsertListOnDuplicateKeyUpdateUniqueKeyRun());
        }
        return ResultDTO.success(true);
    }

    @GetMapping("/insertListOnDuplicateKey")
    public ResultDTO<Boolean> insertListOnDuplicateKey() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            executor.execute(() -> insertListOnDuplicateKeyRun());
        }
        return ResultDTO.success(true);
    }

    @GetMapping("/batchInsertListOnDuplicateKey")
    public ResultDTO<Boolean> batchInsertListOnDuplicateKey() throws InterruptedException {
        for (int i = 0; i < size; i++) {
            executor.execute(() -> batchInsertListOnDuplicateKeyRun());
        }
        return ResultDTO.success(true);
    }

    private void insertListOnDuplicateKeyUpdateUniqueKeyRun() {
        for (int i = 0; i < size; i++) {
            List<UpdateOnUniqueKeyDemo> list = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                updateOnUniqueKeyDemoMapper.insertListOnDuplicateKeyUpdateUniqueKey(new UpdateOnUniqueKeyDemo());
            }
        }
    }

    private void batchInsertListOnDuplicateKeyUpdateUniqueKeyRun() {
        for (int i = 0; i < size; i++) {
            List<UpdateOnUniqueKeyDemo> list = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                list.add(new UpdateOnUniqueKeyDemo());
            }
            updateOnUniqueKeyDemoMapper.batchInsertListOnDuplicateKeyUpdateUniqueKey(list);
        }
    }

    private void insertListOnDuplicateKeyRun() {
        for (int i = 0; i < size; i++) {
            List<UpdateOnUniqueKeyDemo> list = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                updateOnUniqueKeyDemoMapper.insertListOnDuplicateKey(new UpdateOnUniqueKeyDemo());
            }
        }
    }

    private void batchInsertListOnDuplicateKeyRun() {
        for (int i = 0; i < size; i++) {
            List<UpdateOnUniqueKeyDemo> list = new ArrayList<>();
            for (int j = 0; j < num; j++) {
                list.add(new UpdateOnUniqueKeyDemo());
            }
            updateOnUniqueKeyDemoMapper.batchInsertListOnDuplicateKey(list);
        }
    }
}
