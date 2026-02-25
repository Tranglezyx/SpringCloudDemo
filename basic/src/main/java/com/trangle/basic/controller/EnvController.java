package com.trangle.basic.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/env")
@RestController
public class EnvController {

    @Value("${spring.application.name}")
    private String applicationName;

    @PostMapping("/getName")
    public String getName(){
        return applicationName;
    }
}
