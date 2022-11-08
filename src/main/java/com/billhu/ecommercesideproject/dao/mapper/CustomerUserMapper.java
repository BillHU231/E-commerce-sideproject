package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.CustomerUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface CustomerUserMapper {
    @Insert("INSERT INTO customer_user(user_id,customer_name) " +
            " VALUES (#{userId}, #{customerName})")
    @SelectKey(keyProperty = "customerUserId",keyColumn = "customer_user_id",resultType = long.class ,before = false,statement = "SELECT LAST_INSERT_ID() ")
    public Long create (CustomerUserEntity entity);
}
