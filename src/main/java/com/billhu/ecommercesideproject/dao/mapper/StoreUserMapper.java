package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StoreUserMapper {
    @Insert("INSERT INTO store_user(user_id,store_name) " +
            " VALUES(#{userId},#{storeName})" )
    @SelectKey(keyProperty = "storeUserId",keyColumn = "store_user_id",resultType = Integer.class ,before = false,statement = "SELECT LAST_INSERT_ID() ")
    public void create (StoreUserEntity entity);

    @Select("SELECT * FROM store_user " +
            "WHERE store_user_id = #{id} ;")
    @Results({
            @Result(property = "storeUserId", column = "store_user_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "storeName", column = "store_name")
    })
    public List<StoreUserEntity> findById(@Param("id") Integer storeId);
    @Select("SELECT * FROM store_user;")
    @Results({
            @Result(property = "storeUserId", column = "store_user_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "storeName", column = "store_name")
    })
    public  List<StoreUserEntity> findAll();
}
