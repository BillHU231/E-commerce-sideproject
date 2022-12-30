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
@ToString
@NoArgsConstructor
public class DeleteProductRequestModel {
    @Schema(description = "店家名稱",example = "PChome")
    @NotBlank(message = "storeName can not null")
    private String storeName ;
    @Schema(description = "刪除商品id",example = "40002,40003(可多選）")
    @NotNull(message = "productItems can not null")
    private List<Integer> productItems;
}
