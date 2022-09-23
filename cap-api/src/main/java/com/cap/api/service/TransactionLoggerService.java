/**
 * 
 */
package com.cap.api.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.api.constant.CapApiConstant;
import com.cap.api.model.CapExecuteRequest;
import com.cap.api.model.Logger;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.utils.TimerUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class TransactionLoggerService {

	@Value("${url.cap_common_crrate_logger}")
	private String url;

	@Autowired
	Environment environment;

	@Autowired(required = false)
	  RestTemplate restTemplate;

	/**
	 * @param req
	 * @param user
	 * @param correlationId
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public void setInLogger(CapExecuteRequest req, User user, String correlationId)
			throws InterruptedException {
		log.info("CAP API : setInLogger starts");
		String trnastion = "ERROR";

		Logger logger = new Logger(correlationId, "CAP-API-IN", 0, req.getPayload(), "SUCCESS",
				TimerUtil.dateTime().toString(), "", req.getProviderName(), req.getServiceName(), "CAP-API",
				user.getName(), getHostPort());

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			logger.setPayload(ow.writeValueAsString(req));
		} catch (JsonProcessingException e) {
			log.error("CAP API :  Logger request object parsing error");
		}

		trnastion = invoke(trnastion, logger);
		//return CompletableFuture.completedFuture(trnastion);
	}

	/**
	 * @param req
	 * @param user
	 * @param correlationId
	 * @param response
	 * @return
	 * @throws InterruptedException
	 */
	@Async
	public void setOutLogger(CapExecuteRequest req, User user, String correlationId,
			Response response) throws InterruptedException {
		log.info("RULE: setOutLogger starts");
		String trnastion = "ERROR";

		Logger logger = new Logger(correlationId, "CAP-API-OUT", 0, response.getMessage(), "SUCCESS",
				TimerUtil.dateTime().toString(), TimerUtil.getDiff(getStartDate(), TimerUtil.dateTime()),
				req.getProviderName(), req.getServiceName(), "CAP-API", user.getName(), getHostPort());

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			logger.setPayload(ow.writeValueAsString(response.getMessage()));
		} catch (JsonProcessingException e) {
			log.error("CAP API: Request object parsing error");
		}

		trnastion = invoke(trnastion, logger);

		log.info("CAP API: setOutLogger completed");
		//return CompletableFuture.completedFuture(trnastion);
	}

	/**
	 * @param trnastion
	 * @param logger
	 * @return
	 * @throws InterruptedException
	 */
	private String invoke(String trnastion, Logger logger) throws InterruptedException {
		String staus;
		staus = restTemplate.postForObject(url, logger, String.class);
		Thread.sleep(100L); // Intentional delay
		trnastion = staus;
		return trnastion;
	}

	/**
	 * @return
	 */
	private String getHostPort() {
		String data = null;
		try {
			data = InetAddress.getLocalHost().getHostName() + ":" + environment.getProperty("server.port");
		} catch (UnknownHostException e) {
			log.info("CAP API : get host and port failed");
		}
		return data;
	}

	/**
	 * @return
	 */
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute(CapApiConstant.STARTTIME,
				RequestAttributes.SCOPE_REQUEST);
	}

}
