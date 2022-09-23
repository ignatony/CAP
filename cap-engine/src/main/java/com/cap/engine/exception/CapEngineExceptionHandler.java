/**
 * 
 */
package com.cap.engine.exception;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.engine.constant.CapEngineConstant;
import com.cap.engine.model.Response;

import lombok.extern.slf4j.Slf4j;



/**
 * @author Ignatious
 *
 */
@ControllerAdvice
@Slf4j
@RestController
public class CapEngineExceptionHandler {

	
//	@Value("${url.metrics}")
//	private String url;

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
	@ExceptionHandler({ CapEngineException.class })
	public ResponseEntity<Response> handleDogsServiceException(CapEngineException e) {
		return new ResponseEntity<Response>(createReponse(e), HttpStatus.BAD_REQUEST);
	}



	private Response createReponse(Exception e) {
		Response response = new Response();
		response.setCorrelationId(getUuid());
		response.setStartDateTime(getStartDate());
		//response.setEndDateTime(TimerUtil.dateTime());
		response.setStatus("FAIL");
		response.setMessage(e.getMessage());
		//sendMetrics(e.getMessage());
		return response;
	}

	

	/**
	 * @return
	 */
	private String getUuid() {
		return (String) RequestContextHolder.getRequestAttributes().getAttribute(CapEngineConstant.UUID,
				RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @return
	 */
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute(CapEngineConstant.STARTTIME,
				RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * @return
	 */
	private String getTaskName() {
		return (String) RequestContextHolder.getRequestAttributes().getAttribute(CapEngineConstant.TASK_NAME,
				RequestAttributes.SCOPE_REQUEST);
	}



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
