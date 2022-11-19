package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrdersMapper {
    @Insert({"INSERT INTO orders(order_id,customer_user_id,purchased_total,purchased_time,payment_flag) " +
            " VALUES(#{orderId},#{customerUserId},#{purchasedTotal},#{purchasedTime},#{paymentFlag}) ; "})
    public Integer create (OrdersEntity entity);
    @Update({"UPDATE orders " +
            "SET payment_flag =#{paymentFlag} ,payment_time=#{paymentTime} " +
            "WHERE order_id = #{orderId}"})
    public Integer updateByOrderId(OrdersEntity entity);
}
