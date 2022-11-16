package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.util.JwtTokenUtil;
import com.billhu.ecommercesideproject.dao.entity.CustomerUserEntity;
import com.billhu.ecommercesideproject.dao.entity.StoreUserEntity;
import com.billhu.ecommercesideproject.dao.entity.UserEntity;
import com.billhu.ecommercesideproject.dao.mapper.CustomerUserMapper;
import com.billhu.ecommercesideproject.dao.mapper.StoreUserMapper;
import com.billhu.ecommercesideproject.dao.mapper.UserMapper;
import com.billhu.ecommercesideproject.model.LoginRequestModel;
import com.billhu.ecommercesideproject.model.LoginResponseDTO;
import com.billhu.ecommercesideproject.model.UserRequestModel;
import com.billhu.ecommercesideproject.model.UserResponseDTO;
import com.billhu.ecommercesideproject.service.UserLogic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;


@Service
public class UserLogicImpl implements UserLogic {

    private  final static Logger log = LoggerFactory.getLogger(UserLogicImpl.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    StoreUserMapper storeUserMapper;
    @Autowired
    CustomerUserMapper customerUserMapper;
    @Autowired
    JwtTokenUtil tokenUtil;

    /**
     * @Transactional 交易控制  propagationg事件的傳播行為  rollbackFor 發生什麼錯誤rollback
     *參考資料 https://www.796t.com/content/1547149023.html
     *
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED ,rollbackFor = Exception.class)
    public ResponseEntity<UserResponseDTO> register(UserRequestModel model) {
        UserResponseDTO response =new UserResponseDTO();

        Integer accountNum = userMapper.findByMailAndType(model.getUserMail(),model.getIdentity());
        log.info("find user number {} ",accountNum);

        if(accountNum !=0){

            String status ="9003";
            String message ="This email is already register";
            response.setIdentity(model.getIdentity());
            response.setUserName(model.getUserName());
            response.setUserMail(model.getUserMail());
            response.setPassWord(model.getPassWord());
            response.setStatus(status);
            response.setMessage(message);
            log.warn("invalid parameters received ");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        //利用MD5加密密碼
        String passWord= DigestUtils.md5DigestAsHex(model.getPassWord().getBytes());

        //insert user

        UserEntity userEntity =new UserEntity();
        userEntity.setUserMail(model.getUserMail());
        userEntity.setUserPassword(passWord);
        userEntity.setUserType(model.getIdentity());
        userEntity.setCreateTime(new Timestamp( System.currentTimeMillis()));
        userEntity.setModifyTime(new Timestamp( System.currentTimeMillis()));
        log.info("userEntity {} ",userEntity.toString());

        userMapper.create(userEntity);
        log.info("user id {}",userEntity.getUserId());

        long userId = 0L;

        if("reseller".equals(userEntity.getUserType())){
            log.info("create reseller user ");
            StoreUserEntity storeUserEntity =new StoreUserEntity();
            storeUserEntity.setUserId(userEntity.getUserId());
            storeUserEntity.setStoreName(model.getUserName());
            storeUserMapper.create(storeUserEntity);

            userId=storeUserEntity.getStoreUserId();
            log.info("store user id {} ",storeUserEntity.getUserId());
        }else {
            log.info("create customer user ");
            CustomerUserEntity customerUserEntity =new CustomerUserEntity();
            customerUserEntity.setUserId(userEntity.getUserId());
            customerUserEntity.setCustomerName(model.getUserName());

             customerUserMapper.create(customerUserEntity);

             userId= customerUserEntity.getCustomerUserId();
            log.info("customer user id {} ",customerUserEntity.getUserId());
        }

        response.setIdentity(model.getIdentity());
        response.setUserId(userId);
        response.setUserName(model.getUserName());
        response.setUserMail(model.getUserMail());
        response.setPassWord(model.getPassWord());
        response.setStatus("2000");
        response.setMessage("create user success");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<LoginResponseDTO> login(LoginRequestModel model) {

        LoginResponseDTO response=new LoginResponseDTO();

        //檢查帳號是否存在
        String mail =model.getUserMail();
        String passWord = DigestUtils.md5DigestAsHex(model.getPassWord().getBytes());
        UserEntity user= userMapper.findByMailAndPassWord(mail,passWord);
        log.info("userMail {} password {}",mail,passWord);

        if(user==null){
            log.warn("mail {} validate user failed user " ,mail);
            String status="9001";
            String message=" the user mail or the password is incorrect ";
            response.setStatus(status);
            response.setMessage(message);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        log.info("mail {} validate user success " ,mail);

        //create token

        String token= tokenUtil.generateToken(model);

        response.setToken(token);
        response.setStatus("2000");
        response.setMessage("log in success");


        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
