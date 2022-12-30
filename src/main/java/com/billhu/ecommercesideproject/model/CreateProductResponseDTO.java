package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductResponseDTO {
    @Schema(description = "店家名稱",example = "PChome")
    private String storeName;
    private List<ProductItemModel> productItems;
    @Schema(description = "請求狀態碼")
    private  String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
}
