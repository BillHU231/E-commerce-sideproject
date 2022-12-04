package com.billhu.ecommercesideproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AzureController {
    @RequestMapping(value = "/robots933456.txt")
    public String azurePing(){
        return "pong";
    }
}
