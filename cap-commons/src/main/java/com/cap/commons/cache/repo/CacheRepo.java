/**
 * 
 */
package com.cap.commons.cache.repo;

import java.util.List;

import com.cap.commons.cache.model.Cache;

/**
 * @author Ignatious
 *
 */
public interface CacheRepo {
	public void add(Cache cache);

	public List<Cache> findAllCache();

	public List<Cache> findCacheByFieldType(String type, String findStr);

	public void deleteAllCache();
	
	public List<Cache> findCacheByKey(String key);

}
