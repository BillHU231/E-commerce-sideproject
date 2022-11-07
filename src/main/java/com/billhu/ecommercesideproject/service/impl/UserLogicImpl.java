package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.UserEntity;
import com.billhu.ecommercesideproject.dao.mapper.UserMapper;
import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import com.billhu.ecommercesideproject.service.UserLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogicImpl implements UserLogic {

    private  final static Logger log = LoggerFactory.getLogger(UserLogicImpl.class);

    @Autowired
    UserMapper userMapper;
    @Override
    public UserResponseDTO createUser(UserRequestModel model) {
        UserResponseDTO response =new UserResponseDTO();



        Integer accountNum = userMapper.findByMail(model.getUserMail(),model.getIdentity());
        log.info("find user number {} ",accountNum);

        if(accountNum !=0){
            String message ="This mail is registered";
            String status ="9004";
            response.setIdentity(model.getIdentity());
            response.setUserName(model.getUserName());
            response.setUserMail(model.getUserMail());
            response.setPassWord(model.getPassWord());
            response.setStatus(status);
            response.setMessage(message);
            log.info("invalid parameters received : status:{} message:{} ",status,"identity only reseller and customer");
            return response;
        }

        UserEntity userEntity =new UserEntity();





        return null;
    }
}
