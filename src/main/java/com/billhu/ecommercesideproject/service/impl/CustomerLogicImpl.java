package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.ProductEntity;
import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.dao.mapper.ProductMapper;
import com.billhu.ecommercesideproject.dao.mapper.StoreUserMapper;
import com.billhu.ecommercesideproject.model.ProductItemModel;
import com.billhu.ecommercesideproject.model.QueryProductResponseDTO;
import com.billhu.ecommercesideproject.model.QueryStoreResponseDTO;
import com.billhu.ecommercesideproject.model.StoreItemsModel;
import com.billhu.ecommercesideproject.service.CustomerLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerLogicImpl implements CustomerLogic {
    private final static Logger log = LoggerFactory.getLogger(CustomerLogicImpl.class);

    @Autowired
    StoreUserMapper storeUserMapper;
    @Autowired
    ProductMapper productMapper;
    @Override
    public ResponseEntity<QueryStoreResponseDTO> queryStore() {
        QueryStoreResponseDTO response =new QueryStoreResponseDTO();

        List<StoreItemsModel> storeUserModel=new ArrayList<>();

        List<StoreUserEntity> storeUserList= storeUserMapper.findAll();

        for(StoreUserEntity storeUser: storeUserList){

            StoreItemsModel model = new StoreItemsModel();
            model.setStoreId(storeUser.getStoreUserId());
            model.setStoreName(storeUser.getStoreName());
            storeUserModel.add(model);
        }


        response.setStoreTotalCount(storeUserList.size());
        response.setStoreItems(storeUserModel);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<QueryProductResponseDTO> queryProduct(Integer storeId) {
       QueryProductResponseDTO response =new QueryProductResponseDTO();



       //檢查storeid 是否有存在
        List<StoreUserEntity> entity= storeUserMapper.findById(storeId);

        if(entity.isEmpty()){
            log.error("storeId {} can not find form table");

            String status="9002";
            String message= "This storeId "+storeId +" can not find user";
            response.setStatus(status);
            response.setMessage(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //利用storeId找product
        List<ProductEntity> productEntitiesList= productMapper.findByStoreUserId(storeId);

        List<ProductItemModel> productModelList=new ArrayList<>();

        for(ProductEntity product:productEntitiesList){
            ProductItemModel model =new ProductItemModel();


            model.setProductId(product.getProductId());
            model.setProduct(product.getProductName());
            model.setPrice(product.getProductPrice());
            model.setQuantity(product.getQuantity());

            productModelList.add(model);
        }

        //回復
        String storeName =entity.get(0).getStoreName();
        response.setStoreName(storeName);
        response.setProductTotalCount(productEntitiesList.size());
        response.setProductItems(productModelList);
        response.setStatus("2000");
        response.setMessage("query product success");


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
