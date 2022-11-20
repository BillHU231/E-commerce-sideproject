package com.billhu.ecommercesideproject.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class PaymentReturnFilter extends OncePerRequestFilter {

    private  static final Logger log = LoggerFactory.getLogger(PaymentReturnFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("inside payment filter");
        log.info("request body {}",request.getReader().lines().collect(Collectors.joining()));
    }
}
