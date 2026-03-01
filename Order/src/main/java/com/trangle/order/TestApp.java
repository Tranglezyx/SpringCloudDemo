package com.trangle.order;

import com.trangle.order.dto.SubGoodsNumDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestApp {

    public static void main(String[] args) throws InterruptedException {
        int num = 100_0000_000;
        List<SubGoodsNumDTO> list = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            SubGoodsNumDTO dto = new SubGoodsNumDTO();
            Random random = new Random();

            dto.setNum(random.nextInt());
            dto.setGoodsId(random.nextLong());
            list.add(dto);
        }
        System.out.println(list.size());
        Thread.sleep(10000);
    }
}
