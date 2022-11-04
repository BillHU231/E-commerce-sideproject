package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
public class UserEntity {
    public String userId;
    public String userMail;
    public String userPassword;
    public String userType;
    public Date createTime;
    public Date modifyTime;
}
