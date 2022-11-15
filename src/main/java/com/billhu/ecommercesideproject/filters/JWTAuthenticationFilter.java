package com.billhu.ecommercesideproject.filters;

import com.billhu.ecommercesideproject.service.impl.MyUserDetailsService;
import com.billhu.ecommercesideproject.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter { // spring security filter chen 要繼承OncePerRequestFilter

    private  static final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    @Value("${shouldNotFilter.url}")
    private String noInFilterUrl;
    @Autowired
    JwtTokenUtil tokenUtil;
    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException
    {
        log.info(" in side  the spring security filter ");

        // 取的 JWT token
        String auth= request.getHeader(HttpHeaders.AUTHORIZATION);



        if(auth != null && !auth.isBlank() && auth.startsWith("Bearer")){
            String token =auth.replace("Bearer ","");
            log.info("token {} ",token);


            String  mail = tokenUtil.getMail(token);

            if(mail.isBlank()){
                response.sendError(HttpServletResponse.SC_ACCEPTED,"Access token has not mail");
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(mail);

            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(mail,userDetails.getPassword(),userDetails.getAuthorities());
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }


        }

        filterChain.doFilter(request,response);

    }

    /**
     * 設定不要進入Filter url
     * @param request current HTTP request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        log.info("in the shouldNotFilter");

        Boolean isShouldNotFilter =false;
        String path= request.getRequestURI();



        String[] noInFilterArray =noInFilterUrl.split(",");


        for (String url: noInFilterArray){
           if(url.equals(path)){
               isShouldNotFilter =true;
           }
        }

        return isShouldNotFilter;

    }
}
