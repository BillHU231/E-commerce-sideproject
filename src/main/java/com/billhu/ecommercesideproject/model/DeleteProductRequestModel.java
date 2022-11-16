package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeleteProductRequestModel {
    @NotBlank(message = "storeName can not null")
    private String storeName ;
    @NotNull(message = "productItems can not null")
    private List<Integer> productItems;
}
