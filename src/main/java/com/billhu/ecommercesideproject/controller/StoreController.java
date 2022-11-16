package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.CreateProductRequestModel;
import com.billhu.ecommercesideproject.model.CreateProductResponseDTO;
import com.billhu.ecommercesideproject.model.DeleteProductRequestModel;
import com.billhu.ecommercesideproject.model.DeleteProductResponseDTO;
import com.billhu.ecommercesideproject.service.StoreLogic;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/store")
@SecurityRequirement(name ="bearer-key" )
public class StoreController {

    private final static Logger log = LoggerFactory.getLogger(StoreController.class);
    @Autowired
    StoreLogic storeLogic;

    @PostMapping("/{store-id}/create/product")
    public ResponseEntity<CreateProductResponseDTO> createProduct(@PathVariable(name = "store-id") Integer storeId,
                                                                  @RequestBody @Valid CreateProductRequestModel model,
                                                                  BindingResult result) {
        CreateProductResponseDTO response = new CreateProductResponseDTO();

        if (result.hasErrors()) {
            log.error("invalid parameters received ");
            response.setStoreName(model.getStoreName());
            response.setProductItems(model.getProductItems());
            response.setStatus("9001");
            response.setMessage(result.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        log.info(" Create product {} , productItems {} ", model.toString(), model.getProductItems().toString());

        return storeLogic.createProduct(storeId, model);

    }
    @DeleteMapping("/{store-id}/delete/product")
    private ResponseEntity<DeleteProductResponseDTO> deleteProduct(@PathVariable(name = "store-id") Integer storeId,
                                                                   @RequestBody@ Valid DeleteProductRequestModel model,
                                                                   BindingResult bindingResult)
    {
        DeleteProductResponseDTO response=new DeleteProductResponseDTO();

        if(bindingResult.hasErrors()){
            log.error("invalid parameters received");
            String status ="9001";
            response.setStoreName(model.getStoreName());
            response.setProductItems(model.getProductItems());
            response.setStatus(status);
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return storeLogic.deleteProduct(storeId,model);
    }

}


