package com.trangle.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trangle.order.entity.UpdateOnUniqueKeyDemo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UpdateOnUniqueKeyDemoMapper extends BaseMapper<UpdateOnUniqueKeyDemo> {

    int insertListOnDuplicateKeyUpdateUniqueKey(UpdateOnUniqueKeyDemo demo);

    int batchInsertListOnDuplicateKeyUpdateUniqueKey(@Param("list") List<UpdateOnUniqueKeyDemo> list);

    int insertListOnDuplicateKey(UpdateOnUniqueKeyDemo demo);

    int batchInsertListOnDuplicateKey(@Param("list") List<UpdateOnUniqueKeyDemo> list);
}
