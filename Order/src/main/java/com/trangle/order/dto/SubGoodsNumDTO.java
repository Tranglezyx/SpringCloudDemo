package com.trangle.order.dto;

import lombok.Data;

@Data
public class SubGoodsNumDTO {

    private Long goodsId;
    private Integer num;

    public SubGoodsNumDTO() {
    }

    public SubGoodsNumDTO(Long goodsId, Integer num) {
        this.goodsId = goodsId;
        this.num = num;
    }
}
