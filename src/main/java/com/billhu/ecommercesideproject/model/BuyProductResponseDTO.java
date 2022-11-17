package com.billhu.ecommercesideproject.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BuyProductResponseDTO {
    private Integer storeId;
    private List<Integer> shoppingCartItems;
    private BigDecimal total;
    private String paymentURL;
    private String status;
    private String message;
}
