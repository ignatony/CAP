/**
 * 
 */
package com.cap.api.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Ignatious
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Value("${api.name}")
	private String name;

	@Value("${api.version}")
	private String version;

	@Value("${api.description}")
	private String description;

	@Value("${api.title}")
	private String title;

	@Value("${api.package}")
	private String apiPackage;

	/**
	 * @return
	 */
	@Bean
	public Docket apiDocket() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				 .securityContexts(Arrays.asList(securityContext()))
			      .securitySchemes(Arrays.asList(apiKey()))
				.select()
				
				.apis(RequestHandlerSelectors.basePackage(apiPackage)).paths(PathSelectors.any()).build();
		docket.apiInfo(getApiInfo());

		return docket;
	}

	/**
	 * @return
	 */
	private ApiInfo getApiInfo() {

		return new ApiInfo(title, description, version, "TERMS OF SERVICE URL",
				new Contact(name, "URL", "info@ignatony.in"), "LICENSE", "LICENSE URL", Collections.emptyList());
	}
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("JWT", authorizationScopes)); 
	}
}
