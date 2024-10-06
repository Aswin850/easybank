package com.easybank.loans;

import com.easybank.loans.dto.LoansContactInfoDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(value = {LoansContactInfoDTO.class})
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(info = @Info(
		title = "Loans microservice REST API Documentation",
		description = "EazyBank Loans microservice REST API Documentation",
		version = "V1",
		summary = "The EazyBank Loans microservice REST API enables users to manage loan applications. Endpoints include creating and retrieving loan details",
		contact = @Contact(name = "Aswin Senniappan",email = "saswin.senniappan@gmail.com"),
		license = @License(
				name = "Apache 2.0"
		)

))
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
