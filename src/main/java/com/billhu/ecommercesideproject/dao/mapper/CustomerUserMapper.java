package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.CustomerUserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerUserMapper {
//    @SelectKey(keyProperty = "customerUserId",keyColumn = "customer_user_id",resultType = Integer.class ,before = false,statement = "SELECT LAST_INSERT_ID() ")

    @Insert("INSERT INTO customer_user(customer_user_id,user_id,customer_name) " +
            " VALUES (#{customerUserId},#{userId}, #{customerName})")
    public void create (CustomerUserEntity entity);

    @Select({"SELECT * FROM customer_user " +
            "WHERE customer_user_id = #{id} ;"})
    @Results({
            @Result(property = "customerUserId",column = "customer_user_id"),
            @Result(property = "userId",column = "user_id"),
            @Result(property = "customerName",column = "customer_name")
    })
    public List<CustomerUserEntity> findById(@Param(value = "id") Integer customerId);
}
