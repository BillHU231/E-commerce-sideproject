package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import com.billhu.ecommercesideproject.dao.mapper.OrdersMapper;
import com.billhu.ecommercesideproject.service.PaymentLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class PaymentImpl implements PaymentLogic {
    @Autowired
    OrdersMapper ordersMapper;
    @Override
    public Integer paymentSucceed(String orderId) {
        OrdersEntity entity=new OrdersEntity();
        entity.setOrderId(orderId);
        entity.setPaymentFlag("Y");
        entity.setPaymentTime(new Date(System.currentTimeMillis()));

        return ordersMapper.updateByOrderId(entity);

    }
}
