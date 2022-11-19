package com.billhu.ecommercesideproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PaymentController {

    @RequestMapping(value = "/payment/{MerchantTradeNo}",method = RequestMethod.GET)
    public String checkOut(@PathVariable(value = "MerchantTradeNo") String merchantTradeNo){
        return merchantTradeNo;
    }

//    public String paymentSuccess(){
//
//    }
}
