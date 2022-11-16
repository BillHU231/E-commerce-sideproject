package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeleteProductResponseDTO {
    private String storeName;
    private List<Integer> productItems;
    private  String status;
    private String message;
}
