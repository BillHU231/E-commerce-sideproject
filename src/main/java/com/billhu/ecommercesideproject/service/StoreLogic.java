package com.billhu.ecommercesideproject.service;

import com.billhu.ecommercesideproject.model.CreateProductRequestModel;
import com.billhu.ecommercesideproject.model.CreateProductResponseDTO;
import com.billhu.ecommercesideproject.model.DeleteProductRequestModel;
import com.billhu.ecommercesideproject.model.DeleteProductResponseDTO;
import org.springframework.http.ResponseEntity;

public interface StoreLogic {

    public ResponseEntity<CreateProductResponseDTO> createProduct(Integer storeId, CreateProductRequestModel model);

    public  ResponseEntity<DeleteProductResponseDTO> deleteProduct(Integer storeId, DeleteProductRequestModel model);
}
