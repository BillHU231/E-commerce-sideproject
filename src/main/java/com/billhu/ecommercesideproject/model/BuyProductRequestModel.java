package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BuyProductRequestModel {
    @Schema(description = "商店ID",example = "20003")
    @NotNull(message = "storeId can not be null")
    private Integer storeId;
    @Schema(description = "購買商品ID",example = "[40012,40015]")
    @NotNull(message = "shopping cart can not be null")
    private List<Integer> shoppingCartItems;
}
