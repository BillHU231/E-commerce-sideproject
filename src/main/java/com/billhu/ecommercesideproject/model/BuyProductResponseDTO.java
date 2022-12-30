package com.billhu.ecommercesideproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "店家ID",example = "20003")
    private Integer storeId;
    @Schema(description = "在購物車中的商品",example = "40012,40015")
    private List<Integer> shoppingCartItems;
    @Schema(description = "消費金額",example = "3000")
    private BigDecimal total;
    @Schema(description = "結帳link")
    private String paymentURL;
    @Schema(description = "請求狀態碼")
    private String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
}
