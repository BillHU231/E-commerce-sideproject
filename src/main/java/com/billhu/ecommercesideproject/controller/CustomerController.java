package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.model.QueryProductResponseDTO;
import com.billhu.ecommercesideproject.model.QueryStoreResponseDTO;
import com.billhu.ecommercesideproject.service.CustomerLogic;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
@SecurityRequirement(name ="bearer-key" ) //swagger auth 認證對應到 OpenAPIConfig 的@SecurityScheme
public class CustomerController {
    @Autowired
    CustomerLogic customerLogic;

    @GetMapping("/query/store")
    public ResponseEntity<QueryStoreResponseDTO> queryStore()
    {
        return customerLogic.queryStore();

    }
    @GetMapping("/{store-id}/query/product")
    public  ResponseEntity<QueryProductResponseDTO> queryProduct(@PathVariable(name = "store-id") Integer storeId)
    {
        return customerLogic.queryProduct(storeId);
    }



}
