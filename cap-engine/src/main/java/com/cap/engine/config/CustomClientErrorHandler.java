/**
 * 
 */
package com.cap.engine.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;


import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
public class CustomClientErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
		return clientHttpResponse.getStatusCode().is4xxClientError();
	}

	@Override
	public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
		log.error("CustomClientErrorHandler | HTTP Status Code: " + clientHttpResponse.getStatusCode().value());
	}
}
