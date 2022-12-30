package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CreateProductRequestModel {
    @Schema(description = "店家名稱",example = "PChome")
    @NotBlank(message = "storeName can not be null")
    private String storeName;

    @NotNull(message = "productItems can not be null")
    private List<  ProductItemModel> productItems;
}
