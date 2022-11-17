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
    private long orderItemId;
    private long orderId;
    private long storeId;
    private  long productId;
}
