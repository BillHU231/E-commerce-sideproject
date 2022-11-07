package com.billhu.ecommercesideproject.dao.mapper;

import com.billhu.ecommercesideproject.dao.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT COUNT(*) " +
            "FROM users " +
            "WHERE user_mail = #{mail} AND  user_type = #{type}")
    public Integer findByMail(@Param("mail") String mail ,@Param("type") String type);


}
