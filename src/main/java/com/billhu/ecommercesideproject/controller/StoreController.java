package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.CreateProductRequestModel;
import com.billhu.ecommercesideproject.model.CreateProductResponseDTO;
import com.billhu.ecommercesideproject.model.DeleteProductRequestModel;
import com.billhu.ecommercesideproject.model.DeleteProductResponseDTO;
import com.billhu.ecommercesideproject.service.StoreLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "store-Controller",description = "經銷商控制項")
@RestController
@RequestMapping("/store")
@SecurityRequirement(name ="bearer-key" )
public class StoreController {

    private final static Logger log = LoggerFactory.getLogger(StoreController.class);
    @Autowired
    StoreLogic storeLogic;

    @Operation(summary = "經銷商新增產品")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Create product success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")

    })
    @PostMapping("/{store-id}/create/product")
    public ResponseEntity<CreateProductResponseDTO> createProduct(@Schema(description = "店家ID",example = "20001") @PathVariable(name = "store-id") Integer storeId,
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
    @Operation(summary = "經銷商刪除產品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Delete product success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")

    })
    @DeleteMapping("/{store-id}/delete/product")
    private ResponseEntity<DeleteProductResponseDTO> deleteProduct(@Schema(description = "店家ID",example = "20001") @PathVariable(name = "store-id") Integer storeId,
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


