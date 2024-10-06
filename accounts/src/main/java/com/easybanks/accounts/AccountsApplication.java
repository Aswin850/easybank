package com.easybanks.accounts;

import com.easybanks.accounts.dto.AccountsContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(value = {AccountsContactInfoDTO.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(
		title = "Accounts microservice REST API Documentation",
		description = "EasyBank Accounts microservice REST API Documentation",
		version = "V1",
		contact = @Contact(
				name = "Aswin Senniappan",
				email = "s.aswin.senniappan@gmail.com",
				url = "https://www.linkedin.com/in/aswin-senniappan-441193209/"
		),
		license = @License(
				name = "Apache 2.0",
				url = "https://www.linkedin.com/in/aswin-senniappan-441193209/"
		)))
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
