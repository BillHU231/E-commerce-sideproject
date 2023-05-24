package com.billhu.ecommercesideproject.model;

import com.billhu.ecommercesideproject.dao.entity.CustomerUserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class QueryCustomerResponseDTO {

    @Schema(name = "customerTotalCount",example = "5")
    private  Integer  customerTotalCount;


    private List<CustomerItemsModel> customerItems;
}
