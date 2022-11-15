package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HealthController {
    private static final Logger log = LoggerFactory.getLogger(HealthController.class);
    @Autowired
    JwtTokenUtil tokenUtil;

    @GetMapping("/ping")
    public String applicationHealth(){
        log.error("Hello");
        return "pong";
    }
    @GetMapping("/login/ping")
    public ResponseEntity<String> authorizationValidate(@RequestHeader(value = "Authorization",required = true) String auth){



        if(auth != null && !auth.isBlank() && auth.startsWith("Bearer")) {
            String token = auth.replace("Bearer ", "");
            log.info("token {} ", token);

            String  mail = tokenUtil.getMail(token);


            if (mail.isBlank()) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Access token has not mail");
            }
            return ResponseEntity.status(HttpStatus.OK).body("the token is efficient");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please input Authorization");

    }
}
