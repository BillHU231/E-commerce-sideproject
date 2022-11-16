package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class StoreUserEntity {
    private Long storeUserId;
    private  Long userId;
    private  String storeName;

}
