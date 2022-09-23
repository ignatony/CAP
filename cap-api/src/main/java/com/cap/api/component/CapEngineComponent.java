/**
 * 
 */
package com.cap.api.component;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.CapEngineRequest;
import com.cap.api.model.CapExecuteRequest;
import com.cap.api.model.Response;
import com.cap.api.model.workflow.WorkFlowRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class CapEngineComponent {

	@Value("${url.cap_engine}")
	private String enginUrl;

	@Autowired(required = false)
	RestTemplate restTemplate;

	/**
	 * @param capExRequest
	 * @param workFlowRequest
	 * @param correlationId
	 * @return
	 * @throws CapAPIException
	 */
	public Response process(CapExecuteRequest capExRequest, WorkFlowRequest workFlowRequest, String correlationId)
			throws CapAPIException {
		log.info("CAP-API CapEngineComponent:: process ");
		return execute(exchange(capExRequest, workFlowRequest, correlationId));

	}

	/**
	 * @param capEngineRequest
	 * @return
	 * @throws CapAPIException
	 */
	private Response execute(CapEngineRequest capEngineRequest) throws CapAPIException {
		log.info("CAP-API  CapEngineComponent :: execute ");
		Response response = null;
		ResponseEntity<String> res = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<CapEngineRequest> entity = new HttpEntity<CapEngineRequest>(capEngineRequest, headers);
			res = restTemplate.exchange(enginUrl, HttpMethod.POST, entity, String.class);
			if (res != null) {
				try {
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					objectMapper.registerModule(new JavaTimeModule());

					response = objectMapper.readValue(res.getBody(), Response.class);

				} catch (JsonProcessingException e) {
					log.error("CAP-API  CapEngineComponent:: execution << Cap Engine Microserrvice >> response "
							+ e.getMessage());
					throw new CapAPIException(
							" CAP-API  CapEngineComponent:: execution << Cap Engine Microserrvice >> response" + e.getMessage());
				}
			}
		} catch (RestClientException e) {
			log.error("CAP-API  CapEngineComponent :: execution failed " + e.getMessage());
			throw new CapAPIException("CAP-API CapEngineComponent:: execution failed : " + e.getMessage());
		}
		return response;
	}

	/**
	 * @param capExRequest
	 * @param workFlowRequest
	 * @param correlationId
	 * @return
	 * @throws CapAPIException
	 */
	private CapEngineRequest exchange(CapExecuteRequest capExRequest, WorkFlowRequest workFlowRequest,
			String correlationId) throws CapAPIException {
		
		log.info("CAP-API CapEngineComponent :: exchange ");
		CapEngineRequest engineRequest = new CapEngineRequest();
		
		engineRequest.setWorkFlowRequest(workFlowRequest);
		engineRequest.setCapExRequest(capExRequest);
		engineRequest.setCorrelationId(correlationId);
	
		return engineRequest;
	}

}
