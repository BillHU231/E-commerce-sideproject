package com.billhu.ecommercesideproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserResponseDTO {
    private String identity;
    private String userName;
    private String userMail;
    @JsonIgnore
    private String passWord;
    private String status;
    private String message;
}
