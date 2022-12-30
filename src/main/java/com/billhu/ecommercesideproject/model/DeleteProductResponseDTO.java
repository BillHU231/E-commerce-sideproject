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
public class DeleteProductResponseDTO {
    @Schema(description = "商店名稱",example = "PChome")
    private String storeName;
    @Schema(description = "成功刪除商品ID" ,example = "40001,40005")
    private List<Integer> productItems;
    @Schema(description = "請求狀態碼")
    private  String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
}
