package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import com.billhu.ecommercesideproject.service.UserLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserLogic userLogic;

    @PostMapping(value = "/sing-out" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDTO createUser(
           @RequestBody @Valid UserRequestModel requestBody,
           BindingResult bindingResult) {  // BindingResult  為 validated 檢查的錯誤訊息
        String[] identityArray={"reseller","customer"};
        UserResponseDTO response =new UserResponseDTO();
        String message =null;
        String status =null;
        log.info(" create account identity = {} , userName = {} ,userMail = {} ",requestBody.getIdentity(),requestBody.getUserName(),requestBody.getUserMail());
        //檢查輸入參數
        if(bindingResult.hasErrors()){
            message = bindingResult.getFieldError().getDefaultMessage();
            status="9001";

            response.setIdentity(requestBody.getIdentity());
            response.setUserName(requestBody.getUserName());
            response.setUserMail(requestBody.getUserMail());
            response.setPassWord(requestBody.getPassWord());
            response.setStatus(status);
            response.setMessage(message);
            log.info("invalid parameters received : status:{} message:{} ",status,message);

            return  response;
        }
        //檢查身份是否輸入正確
        for(int i=0;i< identityArray.length;i++){

            String identity =identityArray[i];
            log.info("request body identity {} ",requestBody.getIdentity().trim());
            if(identity.equals(requestBody.getIdentity().trim())){

                break;
            }

            if(i== identityArray.length-1){
                message ="identity only reseller and customer";
                status="9002";

                response.setIdentity(requestBody.getIdentity());
                response.setUserName(requestBody.getUserName());
                response.setUserMail(requestBody.getUserMail());
                response.setPassWord(requestBody.getPassWord());
                response.setStatus(status);
                response.setMessage(message);
                log.info("invalid parameters received : status:{} message:{} ",status,message);
                return  response;
            }

        }

        response=userLogic.createUser(requestBody);


        return response;

   }
}
