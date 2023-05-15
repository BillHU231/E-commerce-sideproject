package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.ProductEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {
    //    @SelectKey(keyProperty = "productId",keyColumn = "product_id",resultType = Integer. class ,before = false,statement = "SELECT LAST_INSERT_ID() ")
    @Insert({"INSERT INTO product(product_id,store_user_id,product_name,product_price,quantity,is_enble) " +
            " VALUE (#{productId},#{storeUserId},#{productName},#{productPrice},#{quantity},#{isEnble}) ;"})
    public void createProduct(ProductEntity entity);

    @Delete({"DELETE FROM product " +
            "WHERE store_user_id =#{storeId} AND product_id =#{productId}"})
    public Integer deleteProduct(@Param(value = "storeId") Integer storeId,@Param(value = "productId") Integer productId);

    @Select({"SELECT * FROM product " +
            "WHERE store_user_id = #{storeId}"})
    @Results({
            @Result(property = "productId",column = "product_id"),
            @Result(property = "storeUserId",column = "store_user_id"),
            @Result(property = "productName",column = "product_name"),
            @Result(property = "productPrice",column = "product_price"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "isEnble",column = "is_enble")
    })
    public List<ProductEntity> findByStoreUserId(@Param(value = "storeId") Integer storeId);

    @Select({"SELECT * FROM product " +
            " WHERE store_user_id =#{storeId} " +
            " AND product_id =#{productId}"})
    @Results({
            @Result(property = "productId",column = "product_id"),
            @Result(property = "storeUserId",column = "store_user_id"),
            @Result(property = "productName",column = "product_name"),
            @Result(property = "productPrice",column = "product_price"),
            @Result(property = "quantity",column = "quantity"),
            @Result(property = "isEnble",column = "is_enble")
    })
    public  ProductEntity findByStoreIdAndProductId(@Param(value = "storeId") Integer storeId,
                                                    @Param(value = "productId") Integer productId);

}
