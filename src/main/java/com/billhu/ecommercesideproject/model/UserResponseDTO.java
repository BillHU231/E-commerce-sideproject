package com.billhu.ecommercesideproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private String identity;
    private String userName;
    private String userMail;
    private String passWore;
    private String status;
    private String message;
}
