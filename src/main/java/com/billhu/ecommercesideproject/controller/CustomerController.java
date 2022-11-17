package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.model.BuyProductRequestModel;
import com.billhu.ecommercesideproject.model.BuyProductResponseDTO;
import com.billhu.ecommercesideproject.model.QueryProductResponseDTO;
import com.billhu.ecommercesideproject.model.QueryStoreResponseDTO;
import com.billhu.ecommercesideproject.service.CustomerLogic;
import com.billhu.ecommercesideproject.service.impl.ECPayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/customer")
@SecurityRequirement(name ="bearer-key" ) //swagger auth 認證對應到 OpenAPIConfig 的@SecurityScheme
public class CustomerController {

    private static final Logger log= LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    CustomerLogic customerLogic;
    @Autowired
    ECPayService ecPayService;



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

    @PostMapping("/<customer-id>/buy/product")
    public ResponseEntity<BuyProductResponseDTO> butProduct(@RequestBody @Valid BuyProductRequestModel model,
                                                            @PathVariable(name = "customer-id") Integer customerId,
                                                            BindingResult bindingResult){
        BuyProductResponseDTO response=new BuyProductResponseDTO();

        if(bindingResult.hasErrors()){
            log.error("customerId {} invalid parameters received",customerId);

            response.setStoreId(model.getStoreId());
            response.setShoppingCartItems(model.getShoppingCartItems());
            response.setStatus("9001");
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }

        return customerLogic.buyProduct(customerId,model);


    }

    @GetMapping("/ectest")
    public String ecTest(){
        DateFormat df =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date now =new Date(System.currentTimeMillis());

        String time = df.format(now);


        Map<String,Object> body =new TreeMap<>();

        body.put("MerchantTradeNo","billhutest12347");
        body.put("MerchantTradeDate",time);
        body.put("TotalAmount",10000);
        body.put("TradeDesc","促銷方案");
        body.put("ItemName","Apple iphone 7 手機殼");

        try {
            ecPayService.creatingOrder(body);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return "test";
    }



}
