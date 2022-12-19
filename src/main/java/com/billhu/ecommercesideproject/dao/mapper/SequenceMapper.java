package com.billhu.ecommercesideproject.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SequenceMapper {

    @Select({"SELECT nextID(#{sequenceName})"})
    public Integer generateID(@Param(value = "sequenceName") String sequenceName);
}
