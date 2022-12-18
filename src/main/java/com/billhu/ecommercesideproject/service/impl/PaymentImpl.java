package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.OrderItemEntity;
import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import com.billhu.ecommercesideproject.dao.entity.ProductEntity;
import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.dao.mapper.OrderItemMapper;
import com.billhu.ecommercesideproject.dao.mapper.OrdersMapper;
import com.billhu.ecommercesideproject.dao.mapper.ProductMapper;
import com.billhu.ecommercesideproject.dao.mapper.StoreUserMapper;
import com.billhu.ecommercesideproject.service.PaymentLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class PaymentImpl implements PaymentLogic {

    private final static Logger log = LoggerFactory.getLogger(PaymentImpl.class);

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    OrderItemMapper orderItemMapper;
    @Autowired
    StoreUserMapper storeUserMapper;
    @Autowired
    ProductMapper productMapper;

    @Autowired
    ECPayService ecPayService;
    @Override
    public Integer paymentSucceed(String orderId) {
        OrdersEntity entity=new OrdersEntity();
        entity.setOrderId(orderId);
        entity.setPaymentFlag("Y");
        entity.setPaymentTime(new Date(System.currentTimeMillis()));

        return ordersMapper.updateByOrderId(entity);

    }

    @Override
    public void payment(String orderId,String serverPath, Model model) {
        //find orders and orderitem  table
        OrdersEntity orders= ordersMapper.findByOrderId(orderId);
        List<OrderItemEntity> ordersItemList= orderItemMapper.findByOrderId(orderId);

        StringBuilder buyItems=new StringBuilder();
        Integer storeId =null;

        for(OrderItemEntity items:ordersItemList){
           ProductEntity product =productMapper.findByStoreIdAndProductId(items.getStoreId(), items.getProductId());
            if (product != null) {
                buyItems.append(product.getProductName())
                        .append(" NT$")
                        .append(product.getProductPrice())
                        .append("#");


            }
            storeId= items.getStoreId();
        }

        log.info("buyItems {}",buyItems.toString());

        int total =orders.getPurchasedTotal().intValue();

        StringBuilder tradeDesc =new StringBuilder();
        if(storeId !=null){
            List<StoreUserEntity> storeUser= storeUserMapper.findById(storeId);
            String storeName =null;
            for(StoreUserEntity entity:storeUser){
                storeName =entity.getStoreName();
            }

            if(storeName!=null){
                tradeDesc.append(storeName)
                        .append(" 銷售");
            }

        }

        log.info("tradeDesc {} ",tradeDesc);

        //  buy time
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String merchantTradeDate = df.format(new java.util.Date());

        //串接綠界
        Map<String,Object> requestBody=new TreeMap<>();
        requestBody.put("MerchantTradeNo",orderId);
        requestBody.put("MerchantTradeDate",merchantTradeDate);
        requestBody.put("TotalAmount",total);
        requestBody.put("TradeDesc",tradeDesc);
        requestBody.put("OrderResultURL" ,serverPath+"/payment/result");
        requestBody.put("ItemName" ,buyItems.toString());
        requestBody.put("ReturnURL",serverPath+"/payment/return");

        requestBody = ecPayService.creatingOrder(requestBody);

        //傳給html
        model.addAttribute("url",requestBody.get("CreateOrderURL"));
        model.addAttribute("MerchantID",requestBody.get("MerchantID"));
        model.addAttribute("MerchantTradeNo",requestBody.get("MerchantTradeNo"));
        model.addAttribute("MerchantTradeDate",requestBody.get("MerchantTradeDate"));
        model.addAttribute("PaymentType",requestBody.get("PaymentType"));
        model.addAttribute("TotalAmount",requestBody.get("TotalAmount"));
        model.addAttribute("TradeDesc",requestBody.get("TradeDesc"));
        model.addAttribute("ItemName",requestBody.get("ItemName"));
        model.addAttribute("ReturnURL",requestBody.get("ReturnURL"));
        model.addAttribute("ChoosePayment",requestBody.get("ChoosePayment"));
        model.addAttribute("CheckMacValue",requestBody.get("CheckMacValue"));
        model.addAttribute("EncryptType",requestBody.get("EncryptType"));
        model.addAttribute("OrderResultURL",requestBody.get("OrderResultURL"));

    }
}
