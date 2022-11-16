package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ProductItemModel {
    private  Integer productId;

    private String product;

    private BigDecimal price;

    private Integer quantity;

}
