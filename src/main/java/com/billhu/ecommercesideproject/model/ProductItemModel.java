package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

//@Getter
//@Setter
@NoArgsConstructor
@ToString
public class ProductItemModel {


    @Schema(description = "商品ID",example = "40001",accessMode = Schema.AccessMode.READ_ONLY)  //swagger3 只能讀取不能寫入此參數
    private  Integer productId;

    @Schema(description = "商品名稱",example = "【Philips 飛利浦】沙龍級護髮水潤負離子專業吹風機(HP8232)")
    private String product;


    @Schema(description = "販售價格",example = "4000")
    private BigDecimal price;

    @Schema(description = "庫存數量",example = "1000")
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
