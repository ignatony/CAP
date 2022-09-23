/**
 * 
 */
package com.cap.api.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.api.constant.CapApiConstant;
import com.cap.api.model.Logger;
import com.cap.api.model.Response;
import com.cap.api.utils.TimerUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RestControllerAdvice
@Slf4j
public class CapApiExceptionHandler {

	@Value("${url.cap_common_crrate_logger}")
	private String url;

	@Autowired
	private RestTemplate restTemplate;

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
	@ExceptionHandler({ CapAPIException.class })
	public ResponseEntity<Response> handleDogsServiceException(CapAPIException e) {
		return new ResponseEntity<Response>(createReponse(e), HttpStatus.BAD_REQUEST);
	}

	/**
	 * @param e
	 * @return
	 */
	private Response createReponse(Exception e) {
		Response response = new Response();
		response.setCorrelationId(getUuid());
		response.setStartDateTime(getStartDate());
		response.setEndDateTime(LocalDateTime.now(ZoneOffset.UTC));
		response.setStatus("FAIL");
		response.setMessage(e.getMessage());
		if (getUuid() != null)
			sendLogger(e.getMessage());
		return response;
	}

	private void sendLogger(String error) {
		String staus = null;

		Logger logger = new Logger(getUuid(), "CAP-API-ERR", 0, error, "ERROR", TimerUtil.dateTime().toString(),
				TimerUtil.getDiff(getStartDate(), TimerUtil.dateTime()), "Provider", "Service", "CAP-API", "User",
				getHostPort());
		staus = restTemplate.postForObject(url, logger, String.class);
		log.info("setLogger completed");

	}

	/**
	 * @return
	 */
	private String getUuid() {
		return (String) RequestContextHolder.getRequestAttributes().getAttribute(CapApiConstant.UUID,
				RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @return
	 */
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute(CapApiConstant.STARTTIME,
				RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @return
	 */
	private String getTaskName() {
		return (String) RequestContextHolder.getRequestAttributes().getAttribute(CapApiConstant.USER_ID,
				RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @return
	 */
	private String getHostPort() {
		String data = null;
		try {
			data = InetAddress.getLocalHost().getHostName() + ":" + environment.getProperty("server.port");
		} catch (UnknownHostException e) {
			log.info("CapEngine: failed get host and port ");
		}
		return data;
	}
}
