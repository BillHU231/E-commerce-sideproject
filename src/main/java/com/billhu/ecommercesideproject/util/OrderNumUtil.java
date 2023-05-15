package com.billhu.ecommercesideproject.util;

import com.billhu.ecommercesideproject.dao.mapper.SequenceMapper;
import com.billhu.ecommercesideproject.service.impl.CustomerLogicImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Component
public class OrderNumUtil {

    private final static Logger log = LoggerFactory.getLogger(OrderNumUtil.class);


    @Autowired
    SequenceMapper sequenceMapper;

    public   String  generateOrderNum(){
        StringBuilder orderNum =new StringBuilder("EC");

        DateFormat df =new SimpleDateFormat("yyyyMMdd");

        String nowDate= df.format(new Date());

        Integer orderId =sequenceMapper.generateID("orderId");

        log.info("generateID {}",orderId);

        orderNum.append(nowDate).append(orderId);

        return orderNum.toString();

    }


}
