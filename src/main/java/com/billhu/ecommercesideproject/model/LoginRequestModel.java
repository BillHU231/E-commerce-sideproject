package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginRequestModel {
    @Schema(description = "用戶mail",example = "pchome@gmail.com")
    @NotBlank(message = "userName can not be null")
    @Email
    private String userMail;
    @Schema(description = "用戶密碼",example = "pchome")
    @NotBlank(message = "password can not be null")
    private  String passWord;
}
