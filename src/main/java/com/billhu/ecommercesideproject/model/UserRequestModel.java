package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestModel {
    @NotBlank(message = "identity can not be null !")
    private String identity;
    @NotBlank(message = "userName can not be null !")
    private String userName;
    @NotBlank(message = "userMail can not be null !") @Email(message = "Please check you mail!")
    private String userMail;
    @NotBlank(message = "passWord can not be null !")
    private String passWord;

}
