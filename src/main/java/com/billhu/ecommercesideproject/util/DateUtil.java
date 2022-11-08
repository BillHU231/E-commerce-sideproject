package com.billhu.ecommercesideproject.util;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class DateUtil {
    public static void main(String[] args) {
        GregorianCalendar gc =new GregorianCalendar();
        System.out.println(SimpleDateFormat.getInstance().getCalendar().getTime());
    }
}
