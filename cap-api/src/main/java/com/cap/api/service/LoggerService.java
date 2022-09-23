package com.cap.api.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cap.api.exception.CapAPIException;

@Service
public class LoggerService {

	@Autowired(required = false)
	RestTemplate restTemplate;

	@Value("${url.cap_common_get_logger}")
	private String url;

	public List<Object> findAllLogger() throws CapAPIException {
		List<Object> listLog = null;
		Object[] forNow = restTemplate.getForObject(url, Object[].class);
		if (forNow != null && forNow.length > 0) {
			listLog = Arrays.asList(forNow);
		}
		return listLog;
	}

}
