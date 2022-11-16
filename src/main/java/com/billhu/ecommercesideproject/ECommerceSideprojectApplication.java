package com.billhu.ecommercesideproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}) //Spring boot 2.7 後Spring security 會autoconfiguration(自動配置) 要把它關閉
public class ECommerceSideprojectApplication {

    private static final Logger log = LoggerFactory.getLogger(ECommerceSideprojectApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ECommerceSideprojectApplication.class, args);

		log.debug("start application");


	}

}
