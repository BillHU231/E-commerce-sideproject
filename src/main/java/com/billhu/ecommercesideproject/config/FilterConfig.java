package com.billhu.ecommercesideproject.config;

import com.billhu.ecommercesideproject.filters.PaymentReturnFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean paymentFilter(){
        FilterRegistrationBean bean =new FilterRegistrationBean<>();

        bean.setFilter(new PaymentReturnFilter());
        bean.setName("paymentFilter");
        bean.addUrlPatterns("/payment/return");


        return bean;
    }
}
