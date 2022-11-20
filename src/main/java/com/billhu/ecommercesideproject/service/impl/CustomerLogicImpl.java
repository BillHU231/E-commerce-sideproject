package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.*;
import com.billhu.ecommercesideproject.dao.mapper.*;
import com.billhu.ecommercesideproject.model.*;
import com.billhu.ecommercesideproject.service.CustomerLogic;
import com.billhu.ecommercesideproject.util.CheckoutException;
import com.billhu.ecommercesideproject.util.OrderNumUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CustomerLogicImpl implements CustomerLogic {
    private final static Logger log = LoggerFactory.getLogger(CustomerLogicImpl.class);

    @Autowired
    StoreUserMapper storeUserMapper;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CustomerUserMapper customerUserMapper;
    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrderItemMapper ordersItemMapper;
    @Autowired
    ECPayService ecPayService;
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public ResponseEntity<QueryStoreResponseDTO> queryStore() {
        QueryStoreResponseDTO response = new QueryStoreResponseDTO();

        List<StoreItemsModel> storeUserModel = new ArrayList<>();

        List<StoreUserEntity> storeUserList = storeUserMapper.findAll();

        for (StoreUserEntity storeUser : storeUserList) {

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
        QueryProductResponseDTO response = new QueryProductResponseDTO();


        //檢查storeid 是否有存在
        List<StoreUserEntity> entity = storeUserMapper.findById(storeId);

        if (entity.isEmpty()) {
            log.error("storeId {} can not find form table");

            String status = "9002";
            String message = "This storeId " + storeId + " can not find user";
            response.setStatus(status);
            response.setMessage(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //利用storeId找product
        List<ProductEntity> productEntitiesList = productMapper.findByStoreUserId(storeId);

        List<ProductItemModel> productModelList = new ArrayList<>();

        for (ProductEntity product : productEntitiesList) {
            ProductItemModel model = new ProductItemModel();


            model.setProductId(product.getProductId());
            model.setProduct(product.getProductName());
            model.setPrice(product.getProductPrice());
            model.setQuantity(product.getQuantity());

            productModelList.add(model);
        }

        //回復
        String storeName = entity.get(0).getStoreName();
        response.setStoreName(storeName);
        response.setProductTotalCount(productEntitiesList.size());
        response.setProductItems(productModelList);
        response.setStatus("2000");
        response.setMessage("query product success");


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseEntity<BuyProductResponseDTO> buyProduct(Integer customerId, BuyProductRequestModel model, HttpServletRequest request) {

        BuyProductResponseDTO response = new BuyProductResponseDTO();

        //檢查customer id
        List<CustomerUserEntity> customerEntity = customerUserMapper.findById(customerId);

        if (customerEntity.size() != 1) {

            response.setStoreId(model.getStoreId());
            response.setShoppingCartItems(model.getShoppingCartItems());
            response.setStatus("9002");
            response.setMessage("The customerId is not register ");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //檢查storeId

        Integer storeId = model.getStoreId();

        List<StoreUserEntity> storeEntity = storeUserMapper.findById(storeId);
        if (storeEntity.size() != 1) {
            response.setStoreId(model.getStoreId());
            response.setShoppingCartItems(model.getShoppingCartItems());
            response.setStatus("9002");
            response.setMessage("Can't find this store");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        List<Integer> shoppingCart = model.getShoppingCartItems();
        List<Integer> canNotBuy = new ArrayList<>();

        StringBuilder buyItemsName = new StringBuilder();

        BigDecimal total = new BigDecimal(0);

        for (int i = 0; i < shoppingCart.size(); i++) {

            Integer productId = shoppingCart.get(i);

            ProductEntity product = productMapper.findByStoreIdAndProductId(storeId, productId);

            //找不到商品
            if (product == null) {
                canNotBuy.add(productId);
                shoppingCart.remove(i);
                continue;
            }
            //product 庫存不足
            if (product.getQuantity() <= 0) {
                canNotBuy.add(productId);
                shoppingCart.remove(i);
                continue;
            }

            total = total.add(product.getProductPrice());

            buyItemsName.append(product.getProductName())
                    .append("NT$ ").append(product.getProductPrice())
                    .append("#");
            log.info("product {}", product.toString());
        }

        //建立order table

        log.info("total count {} ", total);



        OrdersEntity orderEntity = new OrdersEntity();

        String orderId=OrderNumUtil.generateOrderNum();

        orderEntity.setOrderId(orderId);
        orderEntity.setCustomerUserId(customerId);
        orderEntity.setPurchasedTotal(total);
        orderEntity.setPurchasedTime(new Date(System.currentTimeMillis()));
        orderEntity.setPaymentFlag("N");

        Integer result = ordersMapper.create(orderEntity);

        if (result != 1) {

            throw new CheckoutException("Create orders table fail");

        }
        //建立ordersItem


        int i = 0;

        for (Integer productId : shoppingCart) {
            i++;
            OrderItemEntity ordersItemEntity = new OrderItemEntity();

            String ordersItemId =orderId+"-"+i;

            ordersItemEntity.setOrderItemId(ordersItemId);
            ordersItemEntity.setOrderId(orderId);
            ordersItemEntity.setStoreId(storeId);
            ordersItemEntity.setProductId(productId);

             ordersItemMapper.create(ordersItemEntity);

        }

        if (i != shoppingCart.size()) {
            throw new CheckoutException("Create ordersItem table fail");
        }

        //串接綠界

        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String orderDate = df.format(new java.util.Date());


        Map<String, Object> requestBody = new TreeMap<>();
        requestBody.put("MerchantTradeNo", orderId);
        requestBody.put("MerchantTradeDate", orderDate);
        requestBody.put("TotalAmount",  total.intValue());
        requestBody.put("TradeDesc", storeEntity.get(0).getStoreName() + " 銷售");
        requestBody.put("ItemName", buyItemsName.toString());

        requestBody = ecPayService.creatingOrder(requestBody);

        log.info("create order request body {}", requestBody.toString());

        //寫HTML黨
        String fileContent = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "\t<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "\t<title></title>\n" +
                "\n" +
                "</head>\n" +
                "<body onload=\"send()\">\n" +
                "\t<h1>付款頁面轉導中.....</h1>\n" +
                "\t<form  action=\""+requestBody.get("CreateOrderURL")+"\" method=\"post\" id=\"myForm\">\n" +
                "\t\t<input type=\"hidden\" name=\"MerchantID\" value=\""+requestBody.get("MerchantID")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"MerchantTradeNo\" value=\""+requestBody.get("MerchantTradeNo")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"MerchantTradeDate\" value=\""+requestBody.get("MerchantTradeDate")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"PaymentType\" value=\""+requestBody.get("PaymentType")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"TotalAmount\" value=\""+requestBody.get("TotalAmount")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"TradeDesc\" value=\""+requestBody.get("TradeDesc")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"ItemName\" value=\""+requestBody.get("ItemName")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"ReturnURL\" value=\""+requestBody.get("ReturnURL")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"ChoosePayment\" value=\""+requestBody.get("ChoosePayment")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"CheckMacValue\" value=\""+requestBody.get("CheckMacValue")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"EncryptType\" value=\""+requestBody.get("EncryptType")+"\" />\n" +
                "\t\t<input type=\"hidden\" name=\"OrderResultURL\" value=\""+requestBody.get("OrderResultURL")+"\" />\n" +
                "\n" +
                "\t</form>\n" +
                "\n" +
                "\t<script>\n" +
                "\t\n" +
                "\n" +
                "\t\tfunction send(){\n" +
                "\n" +
                "\t\t\tsetTimeout(function(){\n" +
                "\t\t\t\tdocument.getElementById(\"myForm\").submit();\n" +
                "\t\t\t},5000)\n" +
                "\t\t\t\n" +
                "\t\t}\n" +
                "\n" +
                "\t</script>\n" +
                "</body>\n" +
                "</html>";


        try {

            Resource resource = resourceLoader.getResource("classpath:templates");

            log.info("templates path {}", resource.getFile().getPath());
            String filePath = resource.getFile().getPath() + "/" + orderId + ".html";
            log.info("file path {}", filePath);

            Path file = Paths.get(filePath);
            Files.deleteIfExists(file);
            Files.createFile(file);

            Files.writeString(file, fileContent);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        //回傳
        String paymentURL ;

        if(request.isSecure()){ //判斷是否可用https

            paymentURL="https://"+ request.getHeader(HttpHeaders.HOST)+"/payment/"+orderId;
        }else{
            paymentURL="http://"+ request.getHeader(HttpHeaders.HOST)+"/payment/"+orderId;
        }


        log.info("paymentURL {}",paymentURL);



        response.setStoreId(model.getStoreId());
        response.setShoppingCartItems(shoppingCart);
        response.setTotal(total);
        response.setPaymentURL(paymentURL);
        response.setStatus("2000");
        response.setMessage("please use payment url check out");


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}