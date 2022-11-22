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
    private Integer storeTotalCount;
    private List<StoreItemsModel> storeItems;


}
