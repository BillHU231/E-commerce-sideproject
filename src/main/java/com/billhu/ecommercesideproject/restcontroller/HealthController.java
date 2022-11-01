package com.billhu.ecommercesideproject.restcontroller;

import com.billhu.ecommercesideproject.ECommerceSideprojectApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    @GetMapping("/ping")
    public String applicationHealth(){
        log.error("Hello");
        return "pong";
    }
}
