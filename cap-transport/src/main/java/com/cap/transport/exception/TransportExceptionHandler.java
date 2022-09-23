/**
 * 
 */
package com.cap.transport.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.transport.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@ControllerAdvice
@Slf4j
@RestController
public class TransportExceptionHandler {

	

	@Autowired
	Environment environment;

	/**
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Response> handleRunTimeException(RuntimeException e) {
		return new ResponseEntity<Response>(createReponse(e), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param e
	 * @return
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ TransportException.class })
	public ResponseEntity<Response> handleDogsServiceException(TransportException e) {
		return new ResponseEntity<Response>(createReponse(e), HttpStatus.BAD_REQUEST);
	}



	private Response createReponse(Exception e) {
		Response response = new Response();
		response.setCorrelationId(getCorelationId());
		//response.setStartDateTime(getStartDate());
		//response.setEndDateTime(LocalDateTime.now());
		response.setStatus("FAIL");
		response.setMessage(e.getMessage());
		response.setLast(Boolean.TRUE);
		return response;
	}

	private String getHostPort() {
		String data = null;
		try {
			data = InetAddress.getLocalHost().getHostName() + ":" + environment.getProperty("server.port");
		} catch (UnknownHostException e) {
		}
		return data;
	}
	
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute("startTime",
				RequestAttributes.SCOPE_REQUEST);
	}
	
	private String getCorelationId() {
		return (String) RequestContextHolder.getRequestAttributes().getAttribute("correlationId",
				RequestAttributes.SCOPE_REQUEST);
	}

}
