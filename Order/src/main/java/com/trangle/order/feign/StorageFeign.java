package com.trangle.order.feign;

import com.trangle.order.dto.SubGoodsNumDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author trangle
 */
@FeignClient("storage")
public interface StorageFeign {

    @PostMapping("/storage/subStorage")
    Boolean subGoodsNum(@RequestBody SubGoodsNumDTO subGoodsNumDTO);
}
