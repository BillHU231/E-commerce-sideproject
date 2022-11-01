package com.billhu.ecommercesideproject.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/ping")
    public String applicationHealth(){
        return "pong";
    }
}
