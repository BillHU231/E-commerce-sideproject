package com.billhu.ecommercesideproject.util;

import com.billhu.ecommercesideproject.controller.UserController;
import com.billhu.ecommercesideproject.model.LoginRequestModel;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable { //表示序列化

    private final static Logger log = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final long JWT_TOKEN_VALIDITY=1*60*60*1000; //設定JWT TOKEN有效時間


    @Value("${key.path}")
    public static  String PRIVATEKEYFILEPATH;


    public  String generateToken(LoginRequestModel model){
        Map<String,Object> header = new HashMap<>();
        header.put("type","JWT");
        header.put("alg","RS256");

        Map<String,Object> claims =new HashMap<>();
        claims.put("sub",model.getUserMail());
        return doGenerateToken(claims,header,model.getUserMail());
    }



    private  String doGenerateToken(Map<String, Object> claims,Map<String,Object> header, String userMail) {
        RSAPrivateKey key =getPrivateKey();

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))     //jwt token 建立時間
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))  //jwt token 失效時間
                .signWith(SignatureAlgorithm.RS256,key)
                .compact();

    }

    private  RSAPrivateKey getPrivateKey() {
        RSAPrivateKey rsaPrivate = null ;


        log.info("PRIVATEKEYFILEPATH {}",PRIVATEKEYFILEPATH);
        try( InputStream is=JwtTokenUtil.class.getResource(PRIVATEKEYFILEPATH).openStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(is));) {
            StringBuilder key = new StringBuilder();
            String st="";

            if(is!=null){
                int i=0;
                while (  (st= reader.readLine()) !=null){
                    i++;
                    if (i==1){
                        continue;
                    }
                    key.append(st);

                }
            }



            String privateKey= key.toString().substring(0, key.toString().indexOf('-'));
            log.info("privateKey {}",privateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            rsaPrivate= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        } catch (IOException e) {

            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rsaPrivate;

    }



    public String getMail(String token){
        return (String) Jwts.parser().setSigningKey(getPrivateKey()).parseClaimsJws(token).getBody().get("sub");
    }




}
