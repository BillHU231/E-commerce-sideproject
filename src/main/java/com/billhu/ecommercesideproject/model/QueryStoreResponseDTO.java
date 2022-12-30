package com.billhu.ecommercesideproject.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class QueryStoreResponseDTO {
    @Schema(description = "平台所有店家數量",example = "5")
    private Integer storeTotalCount;
    private List<StoreItemsModel> storeItems;


}
