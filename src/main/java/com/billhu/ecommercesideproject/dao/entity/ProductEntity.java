package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.math.BigDecimal;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ProductEntity {
  private Integer productId;
  private Integer storeUserId;
  private String productName;
  private BigDecimal productPrice;
  private Integer quantity;
  private Integer isEnble;
}
