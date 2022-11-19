package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrdersEntity {
    private String orderId;
    private Integer customerUserId;
    private BigDecimal purchasedTotal;
    private Date purchasedTime;
    private String paymentFlag;
    private Date paymentTime;
}
