package com.samuelTI.smartpoint.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@Profile("dev")
@EnableSwagger2
public class SwaggerConfig {


	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.samuelTI.smartpoint.api.controllers"))
				.paths(PathSelectors.any()).build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Smart Point API")
				.description("Documentation with SmartPoint API Endpoints Access API. ").version("1.0").build();
		
	}
//	@SuppressWarnings("deprecation")
//	@Bean
//	public SecurityConfiguration security() {
//		String token;
//		try {
//			UserDetails details = this.userDetailsService.loadUserByUsername("faculdade@gmail.com");
//			token = this.jwtTokenUtil.obterToken(details);
//		} catch (Exception e) {
//			token = "";
//		}
//		return new SecurityConfiguration(null, null, null, null, "Bearer " + token, ApiKeyVehicle.HEADER,
//				"Authorization", ",");
//	}
}
