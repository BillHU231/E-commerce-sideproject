package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginRequestModel {
    @NotBlank(message = "userName can not be null")
    @Email
    private String userMail;
    @NotBlank(message = "password can not be null")
    private  String passWord;
}
