package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OrderItemEntity {
    private String orderItemId;
    private String orderId;
    private Integer storeId;
    private  Integer productId;
}
