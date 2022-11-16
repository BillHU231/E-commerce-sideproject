package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class CreateProductResponseDTO {
    private String storeName;
    private List<ProductItemModel> productItems;
    private  String status;
    private String message;
}
