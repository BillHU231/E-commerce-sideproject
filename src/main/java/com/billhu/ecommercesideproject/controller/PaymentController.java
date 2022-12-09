package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.service.PaymentLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    PaymentLogic paymentLogic;


    @GetMapping(value = "/payment/{MerchantTradeNo}")
    public String checkOut(HttpServletRequest request, @PathVariable(value = "MerchantTradeNo") String merchantTradeNo,Model model){
        String serverPath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();

        paymentLogic.payment(merchantTradeNo,serverPath,model);
        return "payment";
    }

    @PostMapping("/payment/result")
    public String paymentSuccess(HttpServletRequest request,  Model model){

        StringBuilder bodyString =new StringBuilder();
        Map<String,String> body =new HashMap<>();

        try {
            bodyString.append(request.getReader().lines().collect(Collectors.joining()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        log.info("body {}",bodyString.toString());
        String[] array1 = bodyString.toString().split("&");
        for(int i=0;i<array1.length;i++){
            String[] array2=array1[i].split("=");
            if(array2.length!=2){
                continue;
            }
            body.put(array2[0],array2[1]);

        }

        log.info("body {}",body.toString());

        if(body.containsKey("RtnMsg")&& "Succeeded".contains(body.get("RtnMsg").trim())){
            //payment succeed
            String orderId=body.get("MerchantTradeNo");
            paymentLogic.paymentSucceed(orderId);
            model.addAttribute("MerchantTradeNo",body.get("MerchantTradeNo"));


            return "paymentsucceed";

        }

        model.addAttribute("MerchantTradeNo",body.get("MerchantTradeNo"));


        return "paymentfail";

    }

    @RequestMapping("/payment/return")
    @ResponseBody
    public String paymentAsynReturn(HttpServletRequest request){
        log.info("inside to payment return");

        StringBuilder bodyString =new StringBuilder();
        Map<String,String> body =new HashMap<>();

        try {
            bodyString.append(request.getReader().lines().collect(Collectors.joining()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        log.info("body {}",bodyString.toString());
        String[] array1 = bodyString.toString().split("&");
        for(int i=0;i<array1.length;i++){
            String[] array2=array1[i].split("=");
            if(array2.length!=2){
                continue;
            }
            body.put(array2[0],array2[1]);

        }

        log.info("body {}",body.toString());

        return "ok";

    }
}
