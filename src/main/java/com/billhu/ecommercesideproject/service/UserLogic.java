package com.billhu.ecommercesideproject.service;

import com.billhu.ecommercesideproject.model.LoginRequestModel;
import com.billhu.ecommercesideproject.model.LoginResponseDTO;
import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface UserLogic {
    public ResponseEntity<UserResponseDTO> register(UserRequestModel model);

    public ResponseEntity<LoginResponseDTO> login(LoginRequestModel model);

}
