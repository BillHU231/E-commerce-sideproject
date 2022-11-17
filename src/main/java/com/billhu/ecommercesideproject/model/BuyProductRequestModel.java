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
public class BuyProductRequestModel {
    @NotBlank(message = "storeId can not be null")
    private Integer storeId;
    @NotNull(message = "shopping cart can not be null")
    private List<Integer> shoppingCartItems;
}
