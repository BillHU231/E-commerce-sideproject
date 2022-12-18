package com.billhu.ecommercesideproject.config;

import com.billhu.ecommercesideproject.filters.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity  //Spring Security config 註解
public class SecurityConfig  {

 private static final String[] AUTH_WHITELIST = {
         // -- Swagger UI v2
         "/v2/api-docs",
         "/swagger-resources",
         "/swagger-resources/**",
         "/configuration/ui",
         "/configuration/security",
         "/swagger-ui.html",
         "/webjars/**",
         // -- Swagger UI v3 (OpenAPI)
         "/v3/api-docs/**",
         "/swagger-ui/**"
         // other public endpoints of your API may be appended to this array
 };

 @Autowired
 private  JWTAuthenticationFilter jwtFilter;

 @Bean
 public SecurityFilterChain configure(HttpSecurity http) throws Exception {
  http.authorizeRequests() //定義那些請求路徑要被保護
          .antMatchers("/ping").permitAll()
          .antMatchers("/login/ping").permitAll()
          .antMatchers("/payment/**").permitAll() //進入付款頁面不驗證
          .antMatchers(HttpMethod.POST,"/user/**").permitAll() //定義那些路徑不須驗譖
          .antMatchers("/").permitAll()
          .antMatchers(AUTH_WHITELIST).permitAll()
          .anyRequest().authenticated()   //其餘的都需要驗證
          .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //取消Session使用
          .and()
          .csrf().disable()     //disable csrf 安全設定
          .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class);   //設定Filter



  return http.build();
 }






}
