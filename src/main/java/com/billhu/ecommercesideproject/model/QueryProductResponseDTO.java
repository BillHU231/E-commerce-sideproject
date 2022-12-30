package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class QueryProductResponseDTO {
    @Schema(description = "商店名稱",example = "PChome")
    private String storeName;
    @Schema(description = "商品總數量",example = "5")
    private Integer productTotalCount;

    private List<ProductItemModel> productItems;
    @Schema(description = "請求狀態碼")
    private String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
}
