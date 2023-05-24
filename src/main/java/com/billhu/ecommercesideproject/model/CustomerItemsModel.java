package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class CustomerItemsModel {

    @Schema(name = "customerId",example = "30001")
    private String customerId;
    @Schema(name = "customerName",example = "Bill HU")
    private String customerName;

}
