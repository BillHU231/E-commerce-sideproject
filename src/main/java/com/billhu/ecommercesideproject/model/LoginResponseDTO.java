package com.billhu.ecommercesideproject.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDTO {
    @Schema(description = "請求狀態碼")
    private String status;
    @Schema(description = "請求狀態碼說明")
    private String message;
    @Schema(description = "登入驗證token")
    private String token;
}
