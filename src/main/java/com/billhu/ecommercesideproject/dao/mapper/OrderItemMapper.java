package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrderItemEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface OrderItemMapper {
    @Insert({"INSERT INTO order_items(order_id,store_user_id,product_id) " +
            "VALUES (#{orderId},#{storeId},#{productId}) ;"})
    @SelectKey(keyProperty = "orderItemId",keyColumn = "order_item_id",resultType = Integer.class,before = false,statement = "SELECT LAST_INSERT_ID()")
    public Integer create (OrderItemEntity entity);
}
