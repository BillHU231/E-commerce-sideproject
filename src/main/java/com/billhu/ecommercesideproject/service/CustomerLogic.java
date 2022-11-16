package com.billhu.ecommercesideproject.service;

import com.billhu.ecommercesideproject.model.QueryProductResponseDTO;
import com.billhu.ecommercesideproject.model.QueryStoreResponseDTO;
import org.springframework.http.ResponseEntity;


public interface CustomerLogic {
    public ResponseEntity<QueryStoreResponseDTO> queryStore();

    public  ResponseEntity<QueryProductResponseDTO> queryProduct(Integer storeId);
}
