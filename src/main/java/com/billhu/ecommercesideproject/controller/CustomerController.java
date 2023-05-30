package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.*;
import com.billhu.ecommercesideproject.service.CustomerLogic;
import com.billhu.ecommercesideproject.service.impl.ECPayService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Tag(name = "Customer-controller",description = "客戶控制項")
@RestController
@RequestMapping("/customer")
@SecurityRequirement(name ="bearer-key" ) //swagger auth 認證對應到 OpenAPIConfig 的@SecurityScheme
public class CustomerController {

    private static final Logger log= LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerLogic customerLogic;
    @Autowired
    ECPayService ecPayService;



    @Operation(summary = "查詢所有商家")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "select store success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
    @GetMapping("/query/store")
    public ResponseEntity<QueryStoreResponseDTO> queryStore()
    {
        return customerLogic.queryStore();

    }
    @Operation(summary = "查詢商家產品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "select product success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
    @GetMapping("/{store-id}/query/product")
    public  ResponseEntity<QueryProductResponseDTO> queryProduct(@Schema(description = "店家ID",example = "20003") @PathVariable(name = "store-id") Integer storeId)
    {
        return customerLogic.queryProduct(storeId);
    }


    @Operation(summary = "購買產品")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "buy product success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
    @PostMapping("/{customer-id}/buy/product")
    public ResponseEntity<BuyProductResponseDTO> buyProduct(@RequestBody @Valid BuyProductRequestModel model,
                                                            @Schema(description = "客戶ID",example = "30003") @PathVariable(name = "customer-id") Integer customerId,
                                                            BindingResult bindingResult,
                                                            HttpServletRequest request){
        BuyProductResponseDTO response=new BuyProductResponseDTO();

        if(bindingResult.hasErrors()){
            log.error("customerId {} invalid parameters received",customerId);

            response.setStoreId(model.getStoreId());
            response.setShoppingCartItems(model.getShoppingCartItems());
            response.setStatus("9001");
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

        return customerLogic.buyProduct(customerId,model,request);


    }

    @Operation(summary = "查詢所有使用者")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "select customer user success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
    @GetMapping("/query/customer")
    public ResponseEntity<QueryCustomerResponseDTO> queryCustomer(){

        return customerLogic.queryCustomer();


    }



}
