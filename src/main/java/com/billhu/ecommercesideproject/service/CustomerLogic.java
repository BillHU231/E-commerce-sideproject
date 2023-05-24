package com.billhu.ecommercesideproject.service;

import com.billhu.ecommercesideproject.model.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;


public interface CustomerLogic {
    public ResponseEntity<QueryStoreResponseDTO> queryStore();

    public  ResponseEntity<QueryProductResponseDTO> queryProduct(Integer storeId);

    public ResponseEntity<BuyProductResponseDTO> buyProduct(Integer customerId, BuyProductRequestModel model, HttpServletRequest request);

    public ResponseEntity<QueryCustomerResponseDTO> queryCustomer();


}
