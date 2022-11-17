package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface OrdersMapper {
    @Insert({"INSERT INTO orders(customer_user_id,purchased_total,purchased_time,payment_flag) " +
            " VALUES(#{customerUserId},#{purchasedTotal},#{purchasedTime},#{paymentFlag}) ; "})
    @SelectKey(keyColumn = "order_id",keyProperty = "orderId",resultType = Integer.class,before =false,statement = "SELECT LAST_INSERT_ID()")
    public Integer create (OrdersEntity entity);
}
