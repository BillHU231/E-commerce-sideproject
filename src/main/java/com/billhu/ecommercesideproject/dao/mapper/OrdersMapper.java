package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.OrdersEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrdersMapper {
    @Insert({"INSERT INTO orders(order_id,customer_user_id,purchased_total,purchased_time,payment_flag) " +
            " VALUES(#{orderId},#{customerUserId},#{purchasedTotal},#{purchasedTime},#{paymentFlag}) ; "})
    public Integer create (OrdersEntity entity);
    @Update({"UPDATE orders " +
            "SET payment_flag =#{paymentFlag} ,payment_time=#{paymentTime} " +
            "WHERE order_id = #{orderId}"})
    public Integer updateByOrderId(OrdersEntity entity);
    @Select({"SELECT * FROM orders WHERE order_id =#{orderId} ;"})
    @Results({
            @Result(property = "orderId",column = "order_id"),
            @Result(property = "customerUserId",column = "customer_user_id"),
            @Result(property = "purchasedTotal",column = "purchased_total"),
            @Result(property = "purchasedTime",column = "purchased_time"),
            @Result(property = "paymentFlag",column = "payment_flag"),
            @Result(property = "paymentTime",column = "payment_time")
    })
    public OrdersEntity findByOrderId(@Param(value = "orderId") String orderId);
}
