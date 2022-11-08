package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerUserEntity {
    private Long customerUserId;
    private Long userId;
    private String customerName;
}
