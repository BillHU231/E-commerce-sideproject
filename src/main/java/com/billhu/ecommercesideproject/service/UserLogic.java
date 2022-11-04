package com.billhu.ecommercesideproject.service;

import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;

public interface UserLogic {
    public UserResponseDTO createUser(UserRequestModel model);

}
