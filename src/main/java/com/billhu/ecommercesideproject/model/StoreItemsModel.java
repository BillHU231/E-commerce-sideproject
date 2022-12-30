package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreItemsModel {
    @Schema(description = "店家ID",example = "20003")
    private Integer storeId;
    @Schema(description = "店家名稱",example = "Shopee")
    private  String storeName;
}
