package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class UserEntity {

    public Integer userId;
    public String userMail;
    public String userPassword;
    public String userType;
    public Timestamp createTime;
    public Timestamp modifyTime;
}
