package com.billhu.ecommercesideproject.service.impl;

import com.billhu.ecommercesideproject.dao.entity.UserEntity;
import com.billhu.ecommercesideproject.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        List<UserEntity> userList= mapper.findByMail(mail);

        if(userList.isEmpty()){
            throw new UsernameNotFoundException("Could not find user");
        }

        UserEntity user =userList.get(0);

        return new org.springframework.security.core.userdetails.User(mail,user.getUserPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}
