package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.controller.StoreController;
import com.billhu.ecommercesideproject.dao.entity.ProductEntity;
import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.dao.mapper.ProductMapper;
import com.billhu.ecommercesideproject.dao.mapper.StoreUserMapper;
import com.billhu.ecommercesideproject.model.*;
import com.billhu.ecommercesideproject.service.StoreLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class StoreLogicImpl implements StoreLogic {

    private final static Logger log = LoggerFactory.getLogger(StoreLogicImpl.class);

    @Autowired
    StoreUserMapper storeUserMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public ResponseEntity<CreateProductResponseDTO> createProduct(Integer storeId, CreateProductRequestModel model) {
        CreateProductResponseDTO response=new CreateProductResponseDTO();
        String status =null;
        String message = null;
        // 驗證storeId
        List<StoreUserEntity> entity= storeUserMapper.findById(storeId);

        if(entity.isEmpty()){
            log.error("storeId {} can not find form table");
            status="9002";
            message= "This storeId "+storeId +" can not find user";
            response.setStoreName(model.getStoreName());
            response.setProductItems(model.getProductItems());
            response.setStatus(status);
            response.setMessage(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

        // 加入商品

        List<ProductItemModel> productItems =model.getProductItems();

        for(ProductItemModel product: productItems){
            ProductEntity productEntity = new ProductEntity();

            productEntity.setStoreUserId(storeId);
            productEntity.setProductName(product.getProduct());
            productEntity.setProductPrice(product.getPrice());
            productEntity.setQuantity( product.getQuantity());
            productEntity.setIsEnble(1);

            productMapper.createProduct(productEntity);
            product.setProductId(productEntity.getProductId());



           log.info("insert product {} ",product.toString());
        }

        //回傳
        response.setStoreName(model.getStoreName());
        response.setProductItems(productItems);
        response.setStatus("2000");
        response.setMessage("Create product success");


        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<DeleteProductResponseDTO> deleteProduct(Integer storeId, DeleteProductRequestModel model) {
        DeleteProductResponseDTO response =new DeleteProductResponseDTO();



        // 驗證storeId
        List<StoreUserEntity> entity= storeUserMapper.findById(storeId);

        if(entity.isEmpty()){
            log.error("storeId {} can not find form table");
            String status="9002";
            String message= "This storeId "+storeId +" can not find user";
            response.setStoreName(model.getStoreName());
            response.setProductItems(model.getProductItems());
            response.setStatus(status);
            response.setMessage(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //找尋product

        List<Integer> requestProductItems =model.getProductItems();

        List<Integer> responseProductItems =new ArrayList<>();



        for(Integer productId: requestProductItems){
             int result= productMapper.deleteProduct(storeId,productId);

             if(result >0){
                 responseProductItems.add(productId);
             }
        }


        //回傳
        response.setStoreName(model.getStoreName());
        response.setProductItems(responseProductItems);
        response.setStatus("20000");
        response.setMessage("delete product success");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
