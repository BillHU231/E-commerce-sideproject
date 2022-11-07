package com.billhu.ecommercesideproject.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
@Getter
@Setter
@ToString
public class UserEntity {
    @TableI
    public String userId;
    public String userMail;
    public String userPassword;
    public String userType;
    public Date createTime;
    public Date modifyTime;
}
