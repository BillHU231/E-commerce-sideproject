package com.billhu.ecommercesideproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

//@Getter
//@Setter
@NoArgsConstructor
@ToString
public class ProductItemModel {


    @Schema(accessMode = Schema.AccessMode.READ_ONLY)  //swagger3 只能讀取不能寫入此參數
    private  Integer productId;

    private String product;

    private BigDecimal price;

    private Integer quantity;

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProduct() {
        return product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
