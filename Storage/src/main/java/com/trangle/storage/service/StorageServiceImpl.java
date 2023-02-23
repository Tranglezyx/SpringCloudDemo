package com.trangle.storage.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.trangle.storage.entity.StorageBasic;
import com.trangle.storage.mapper.StorageBasicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageBasicMapper storageBasicMapper;

    @Override
    public void addStorage(StorageBasic storageBasic) {
        storageBasicMapper.insert(storageBasic);
    }

    @Override
    public void subStorage(StorageBasic storageBasic) {
        StorageBasic basic = storageBasicMapper.selectOne(Wrappers.<StorageBasic>lambdaQuery()
                .eq(StorageBasic::getGoodsId, storageBasic.getGoodsId())
                .last(" limit 1"));
        if (basic != null && basic.getNum().compareTo(storageBasic.getNum()) >= 0) {
            storageBasicMapper.update(null, Wrappers.<StorageBasic>lambdaUpdate()
                    .set(StorageBasic::getNum, basic.getNum() - storageBasic.getNum())
                    .eq(StorageBasic::getId, basic.getId()));
        } else {
            throw new RuntimeException("商品不存在或者库存不足");
        }
    }
}
