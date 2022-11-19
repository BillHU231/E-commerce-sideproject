package com.billhu.ecommercesideproject.service.impl;


import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:ECPay.properties")
public class ECPayService {

    private static final Logger log= LoggerFactory.getLogger(ECPayService.class);

    @Value("${MerchantID}")
    private String merchantId;
    @Value("${PaymentType}")
    private String paymentType;
    @Value("${ReturnURL}")
    private String returnURL;
    @Value("${ChoosePayment}")
    private String choosePayment;
    @Value("${EncryptType}")
    private String encryptType;
    @Value("${CreateOrderURL}")
    private String createOrderURL;

    @Value("${OrderResultURL}")
    private String orderResultURL;

    public Map<String,Object> creatingOrder(Map<String,Object> body)  {

        body.put("MerchantID",merchantId);
        body.put("PaymentType",paymentType);
        body.put("ChoosePayment",choosePayment);
        body.put("ReturnURL",returnURL);
        body.put("EncryptType",encryptType);
        body.put("OrderResultURL",orderResultURL);

        log.info("{} ",body.entrySet());

        //建立CheckMacValue
        StringBuilder values1 =new StringBuilder();

        for(Map.Entry<String, Object> set: body.entrySet()){
            values1.append(set.getKey())
                    .append("=")
                    .append(set.getValue())
                    .append("&");
        }

        log.info("value1 {}",values1.toString());

        StringBuilder values2=new StringBuilder(values1);
        values2.insert(0,"HashKey=5294y06JbISpM5x9&");
        values2.insert(values2.length(),"HashIV=v77hoKGq4kWxNNIS");

        log.info("values2 {}",values2.toString());

        String values3= URLEncoder.encode(values2.toString(), StandardCharsets.UTF_8);

        log.info("values3 {}",values3);

        String values4=values3.toLowerCase().replaceAll("%21", "\\!").replaceAll("%28", "\\(").replaceAll("%29", "\\)");

        log.info("values4 {}",values4);

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            log.error("create order CheckMacValue convent to SHA-256 error");
            throw new RuntimeException(e);
        }
        byte[] encodedhash = digest.digest(
                values4.getBytes(StandardCharsets.UTF_8));

        String values5 =bytesToHex(encodedhash);

        log.info("values5 {} ",values5);

        String values6=values5.toUpperCase();

        log.info("values6 {}",values6);

        body.put("CheckMacValue",values6);
        body.put("CreateOrderURL",createOrderURL);

        return body;

    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }


}
