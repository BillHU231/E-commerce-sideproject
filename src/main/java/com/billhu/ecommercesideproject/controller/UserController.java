package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.LoginRequestModel;
import com.billhu.ecommercesideproject.model.LoginResponseDTO;
import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import com.billhu.ecommercesideproject.service.UserLogic;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Tag(name = "user-controller",description = "user 控制項")
@RestController
@RequestMapping("/user")
@SecurityRequirement(name ="bearer-key" )
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserLogic userLogic;


    @Operation(summary = "user 註冊")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "create user success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
    @PostMapping(value = "/sing-out" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> createUser(
           @RequestBody @Valid UserRequestModel requestBody,
           BindingResult bindingResult) {  // BindingResult  為 validated 檢查的錯誤訊息

        String[] identityArray={"store","customer"};
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

            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        }

        return userLogic.register(requestBody);

   }
    @Operation(summary = "user 登入")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "login  success"),
            @ApiResponse(responseCode = "403", description = "You are not Authorised"),
            @ApiResponse(responseCode = "401", description = "Access token is Expired"),
            @ApiResponse(responseCode = "400",description = "invalid parameters received")
    })
   @PostMapping("/login")
   public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestModel model ,BindingResult bindingResult){

        LoginResponseDTO response =new LoginResponseDTO();
        log.info(" user log in {}",model.toString());


        if(bindingResult.hasErrors()){
            response.setStatus("9001");
            response.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

       return userLogic.login(model);

   }
}
