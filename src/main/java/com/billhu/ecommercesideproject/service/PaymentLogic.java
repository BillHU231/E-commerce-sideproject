package com.billhu.ecommercesideproject.service;

import org.springframework.ui.Model;

public interface PaymentLogic {
    public Integer paymentSucceed(String orderId);

    public void payment(String orderId, Model model);
}
