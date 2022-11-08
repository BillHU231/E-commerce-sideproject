package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResponseDTO {
    private String status;
    private String message;
    private String token;
}
