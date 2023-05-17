package com.trangle.storage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trangle.storage.dto.OrderBasicDTO;
import com.trangle.storage.entity.StorageBasic;

public interface StorageBasicMapper extends BaseMapper<StorageBasic> {

    int updateGoodsNumByGoodsId(OrderBasicDTO basicDTO);
}
