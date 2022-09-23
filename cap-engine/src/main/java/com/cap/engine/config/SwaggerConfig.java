/**
 * 
 */
package com.cap.engine.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
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
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
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

}
