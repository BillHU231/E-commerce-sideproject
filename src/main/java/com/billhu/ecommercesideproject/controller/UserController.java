package com.billhu.ecommercesideproject.controller;

import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.Charset;


@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @PostMapping(value = "/sing-out" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public UserResponseDTO createUser(
           @RequestBody @Valid UserRequestModel requestBody) {
       log.info(" create account identity = {} , userName = {} ,userMail = {} ",requestBody.getIdentity(),requestBody.getUserName(),requestBody.getUserMail());



        return null;

   }
}
