package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.UserEntity;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT COUNT(*) " +
            "FROM users " +
            "WHERE user_mail = #{mail} AND  user_type = #{type}")
    public Integer findByMail(@Param("mail") String mail ,@Param("type") String type);

    /**
     * @SelectKey insert date 查詢db自動生成的id  ， keyColumn =>對應db 欄位  keyProperty => 對應 entity 參數  resultType=> 回傳type before=>執行insert 前或後執行 statement=>要執行得sql語法
     *參考 https://blog.csdn.net/Wangdiankun/article/details/106461414
     */
    @SelectKey( keyColumn = "user_id",keyProperty = "userId",resultType = long.class,before = false ,statement = "SELECT LAST_INSERT_ID()")
    @Insert("INSERT INTO users(user_mail,user_password,user_type,create_time,modify_time) " +
            " VALUES (#{userMail},#{userPassword},#{userType},#{createTime},#{modifyTime} ) ")
    public Long create (UserEntity user);
    @Select("SELECT * FROM users " +
            "WHERE user_mail = #{mail} AND user_password = #{password}")
    @Results({
            @Result(property = "userId" ,column = "user_id" ),
            @Result(property = "userMail",column = "user_mail"),
            @Result(property = "userPassword",column = "user_password"),
            @Result(property = "userType",column = "user_type"),
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "modifyTime",column = "modify_time")
    })
    public UserEntity findByMailAndPassWord(@Param("mail") String mail,@Param("password") String password);


}
