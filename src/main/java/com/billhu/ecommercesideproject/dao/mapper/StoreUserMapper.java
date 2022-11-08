package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

@Mapper
public interface StoreUserMapper {
    @Insert("INSERT INTO store_user(user_id,store_name) " +
            " VALUES(#{userId},#{storeName})" )
    @SelectKey(keyProperty = "storeUserId",keyColumn = "store_user_id",resultType = long.class ,before = false,statement = "SELECT LAST_INSERT_ID() ")
    public Long create (StoreUserEntity entity);
}
