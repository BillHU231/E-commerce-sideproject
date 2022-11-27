package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrderItemEntity;
import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    @Insert({"INSERT INTO order_items(order_item_id,order_id,store_user_id,product_id) " +
            "VALUES (#{orderItemId},#{orderId},#{storeId},#{productId}) ;"})
    public Integer create (OrderItemEntity entity);

    @Select({"SELECT * FROM order_items WHERE order_id =#{orderId} "})
    @Results({
            @Result(column = "order_item_id",property ="orderItemId" ),
            @Result(column = "order_id",property = "orderId"),
            @Result(column = "store_user_id",property = "storeId"),
            @Result(column = "product_id",property = "productId")
    })
    public List<OrderItemEntity> findByOrderId(String orderID);
}
