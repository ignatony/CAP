/**
 * 
 */
package com.cap.commons.cache.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.commons.cache.model.Cache;
import com.cap.commons.cache.repo.CacheRepoImpl;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */

@RequestMapping(path = "/cap-commons/cache")
@RestController
@Slf4j
public class CacheController {
	
	@Autowired
	CacheRepoImpl cacheRepoImpl;
	
	/**
	 * @param cache
	 * @return
	 */
	@ApiOperation(value = "Create new Cache")
	@PostMapping(path = "/createCache")
	public @ResponseBody ResponseEntity<String> addCache(@RequestBody Cache cache) {
		log.info("Create Cache");
		cacheRepoImpl.add(cache);
		return new ResponseEntity<String>("Added Succussful", HttpStatus.OK);
	}
	
	/**
	 * @param key
	 * @return
	 */
	@ApiOperation(value = "Find Cache by key")
	@GetMapping(path = "/findCacheByKey/{key}")
	public @ResponseBody ResponseEntity<Cache> findCacheByKey(@PathVariable String key){
		log.info("Find Cache by key");
		List<Cache> caches = null;
		Cache cache = null;
		caches = cacheRepoImpl.findCacheByKey(key);
		if(caches != null && !caches.isEmpty()) {
			cache = caches.get(0);
		}
		return new ResponseEntity<Cache>(cache, HttpStatus.OK);
	}
	/**
	 * @return
	 */
	@ApiOperation(value = "Find All Caches")
	@GetMapping(path = "/findAllCache")
	public @ResponseBody ResponseEntity<List<Cache>> findAllCache(){
		log.info("Find All Caches");
		List<Cache> Cache = null;
		Cache = cacheRepoImpl.findAllCache();
		return new ResponseEntity<List<Cache>>(Cache, HttpStatus.OK);
	}
	
	
	/**
	 * @return
	 */
	@ApiOperation(value = "Delete All Cache")
	@GetMapping(path = "/deleteAllCache")
	public  @ResponseBody ResponseEntity<String> deleteAllCache(){
		log.info("Delete All Cache");
		cacheRepoImpl.deleteAllCache();
		return new ResponseEntity<String>("Deleted All Succussful", HttpStatus.OK);
	}

}
