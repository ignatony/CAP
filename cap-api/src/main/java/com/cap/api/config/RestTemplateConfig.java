/**
 * 
 */
package com.cap.api.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
@Configuration
public class RestTemplateConfig {
	private final CloseableHttpClient httpClient;
	
	@Value("${connection.timeout.READ_TIMEOUT}")
	private int READ_TIMEOUT;


	@Autowired
	public RestTemplateConfig(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
		clientHttpRequestFactory.setReadTimeout(READ_TIMEOUT);
		return clientHttpRequestFactory;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplateBuilder().requestFactory(this::clientHttpRequestFactory)
				.errorHandler(new CustomClientErrorHandler()).interceptors(new CustomClientHttpRequestInterceptor())
				.build();
	}
}
