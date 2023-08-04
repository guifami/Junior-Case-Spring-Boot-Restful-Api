package com.jsprestfulapi.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
					.title("Junior Case RESTful API with Java 17 and Spring Boot 3")
					.version("v1")
					.description("")
					.termsOfService("http://swagger.io/terms/")
					.license(
						new License()
							.name("Apache 2.0")
							.url("http://springdoc.org")
						)
					);
	}
	
}
