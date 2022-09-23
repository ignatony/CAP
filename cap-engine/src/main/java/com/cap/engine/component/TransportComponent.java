package com.cap.engine.component;

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


import com.cap.engine.cache.CacheInvoke;
import com.cap.engine.component.model.TransportRequest;
import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Cache;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Flow;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Response;
import com.cap.engine.utils.ObjectMapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransportComponent implements PrepareComponent {

	@Value("${url.cap_transport}")
	private String transportUrl;
	

	@Value("${url.cap_common_get_cache}")
	private String cacheGetUrl;
	
	@Value("${url.cap_common_get_cache}")
	private String cacheStoreUrl;

	
	@Autowired
	CacheInvoke cacheInvoke;

	@Autowired(required = false)
	RestTemplate restTemplate;


	private Cache cacheOut;

	@Override
	public ResMessage prepare(Exchanger exchanger, Flow flow) throws CapEngineException {

		log.info(" CapEngine TransportComponent :: prepare ");
		ResMessage resMessage = new ResMessage();
		Response response = null;
		cacheOut = null;
		resMessage.setCorrelationId(exchanger.getCorrelationId());
		Cache cache = flow.getTransport().getCache();
		if (null != cache) {
			cacheOut = cacheInvoke.findCache(cacheKeyGen(exchanger));
			if (null != cacheOut) {
				response = new Response(exchanger.getCorrelationId(), null, null, "Success", "", cacheOut.getValue(),
						true);
			} else {
				response = execute(exchangeRequest(exchanger, flow));
			}
		} else {
			response = execute(exchangeRequest(exchanger, flow));
		}

		if (response != null) {
			if (null != cache && null == cacheOut) {
				cacheInvoke
						.storeCache(new Cache(cache.getCacheLifeSpan(), cacheKeyGen(exchanger), cacheOut.getValue()));
			}
			resMessage.setOutPayload(response.getPayload());
			resMessage.setStatus("Success");
			exchanger.setNewPayload(response.getPayload());
		} else {
			resMessage.setStatus("Failed");
		}

		return resMessage;
	}

	/**
	 * @param exchanger
	 * @return key
	 */
	private String cacheKeyGen(Exchanger exchanger) {
		int serviceId = exchanger.getServiceId();
		int providerId = exchanger.getProviderId();
		int customerId = exchanger.getCustomerId();
		return customerId + "_" + providerId + "_" + serviceId;
	}

	/**
	 * @param transportRequest
	 * @return Response
	 * @throws CapEngineException
	 */
	private Response execute(TransportRequest transportRequest) throws CapEngineException {
		log.info("CapEngine TransportComponent :: execute ");
		Response response = null;
		ResponseEntity<String> res = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			HttpEntity<TransportRequest> entity = new HttpEntity<TransportRequest>(transportRequest, headers);
			res = restTemplate.exchange(transportUrl, HttpMethod.POST, entity, String.class);
			if (res != null) {
				try {
					objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					objectMapper.registerModule(new JavaTimeModule());

					response = objectMapper.readValue(res.getBody(), Response.class);

				} catch (JsonProcessingException e) {
					log.error(" CapEngineComponent:: execution << Cap Transport Microserrvice >> response "
							+ e.getMessage());
					throw new CapEngineException(
							" CapEngineComponent:: execution << Cap Transport Microserrvice >> response"
									+ e.getMessage());
				}
			}
		} catch (RestClientException e) {
			log.error("CapEngineComponent :: execution failed " + e.getMessage());
			throw new CapEngineException("CapEngineComponent:: execution failed : " + e.getMessage());
		}
		return response;
	}

	/**
	 * @param exchanger
	 * @param flow
	 * @return TransportRequest
	 * @throws CapEngineException
	 */
	private TransportRequest exchangeRequest(Exchanger exchanger, Flow flow) throws CapEngineException {
		log.info(" CapEngine TransportComponent:: exchangeRequest ");
		TransportRequest transReq = ObjectMapperUtils.map(exchanger, TransportRequest.class);
		transReq.setPayload(exchanger.getNewPayload());
		transReq.setTransport(flow.getTransport());

		return transReq;
	}

}
