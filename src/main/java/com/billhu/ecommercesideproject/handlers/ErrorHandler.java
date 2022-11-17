package com.billhu.ecommercesideproject.handlers;

import com.billhu.ecommercesideproject.util.CheckoutException;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    private  static final Logger log = LoggerFactory.getLogger(ErrorHandler.class);
    @ExceptionHandler(ExpiredJwtException.class)
    private ResponseEntity<String> expiredJwtHandler(){
        log.error("The token is expired");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Access token is Expired");

    }
    @ExceptionHandler(CheckoutException.class)
    private ResponseEntity<String> checkoutHandler(CheckoutException e){
        log.error("check out error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
