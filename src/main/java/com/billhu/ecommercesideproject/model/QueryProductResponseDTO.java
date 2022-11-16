package com.billhu.ecommercesideproject.model;

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
    private String storeName;
    private Integer productTotalCount;
    private List<ProductItemModel> productItems;
    private String status;
    private String message;
}
