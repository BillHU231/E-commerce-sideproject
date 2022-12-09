package com.billhu.ecommercesideproject.util;

import org.springframework.beans.factory.annotation.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderNumUtil {

    private static Integer orderId=1040;

    public static  String  generateOrderNum(){
        StringBuilder orderNum =new StringBuilder("EC");

        DateFormat df =new SimpleDateFormat("yyyyMMdd");

        String nowDate= df.format(new Date());

        orderId++;

        orderNum.append(nowDate).append(orderId);

        return orderNum.toString();

    }


}
