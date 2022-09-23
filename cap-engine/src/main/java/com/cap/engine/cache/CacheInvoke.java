/**
 * 
 */
package com.cap.engine.cache;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.Cache;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
@Component
public class CacheInvoke {
	
	
	@Value("${url.cap_common_get_cache}")
	private String cacheGetUrl;
	
	@Value("${url.cap_common_store_cache}")
	private String cacheStoreUrl;

	@Autowired(required = false)
	RestTemplate restTemplate;

	/**
	 * @param key
	 * @return
	 */
	public Cache findCache(String key) {
		log.info("findCache ksy is {}", key);
		return restTemplate.getForObject(cacheGetUrl+key, Cache.class);
	}

	/**
	 * @param cache
	 */
	public void storeCache(Cache cache)  {
		log.info("IN storeCache");
	    try {
			URI uri = new URI(cacheStoreUrl);
			ResponseEntity<String> result = restTemplate.postForEntity(uri, cache, String.class);
		} catch (RestClientException | URISyntaxException e) {
		 throw new CapEngineException("Cache Store error :"+e.getMessage());
		}
	}

}
