package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface OrderItemMapper {
    @Insert({"INSERT INTO order_items(order_item_id,order_id,store_user_id,product_id) " +
            "VALUES (#{orderItemId},#{orderId},#{storeId},#{productId}) ;"})
    public Integer create (OrderItemEntity entity);
}
