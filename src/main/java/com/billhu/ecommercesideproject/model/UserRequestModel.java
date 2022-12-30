package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestModel {
    @Schema(description = "註冊身份（store or customer）",example = "僅可選擇輸入store或customer")
    @NotBlank(message = "identity can not be null !")
    private String identity;
    @Schema(description = "用戶名稱",example = "BillHU")
    @NotBlank(message = "userName can not be null !")
    private String userName;
    @Schema(description = "用戶信箱",example = "billhu@gmail.comn")
    @NotBlank(message = "userMail can not be null !") @Email(message = "Please check you mail!")
    private String userMail;
    @Schema(description = "用戶密碼",example = "Password")
    @NotBlank(message = "passWord can not be null !")
    private String passWord;

}
