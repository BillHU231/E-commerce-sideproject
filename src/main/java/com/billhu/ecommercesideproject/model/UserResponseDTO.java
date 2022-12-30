package com.billhu.ecommercesideproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDTO {

    @Schema(description = "註冊身份（store or customer）")
    private String identity;
    @Schema(description = "用戶ID")
    private  Long userId;
    @Schema(description = "用戶名稱")
    private String userName;
    @Schema(description = "用戶信箱")
    private String userMail;
    @JsonIgnore
    private String passWord;
    @Schema(description = "請求狀態碼")
    private String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
}
