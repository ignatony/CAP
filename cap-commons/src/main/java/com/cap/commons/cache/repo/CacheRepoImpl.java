package com.cap.commons.cache.repo;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cap.commons.cache.model.Cache;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;
@Component
public class CacheRepoImpl implements CacheRepo {

	private final List<Cache> cachees;
	private final EmbeddedStorageManager cacheStorage;

	public CacheRepoImpl(@Value("${microstream.store.cacheLocation}") final String location) {
		super();
		this.cachees = new ArrayList<>();
		this.cacheStorage = EmbeddedStorage.start(this.cachees, Paths.get(location));
	}

	public void storeAll() {
		this.cacheStorage.store(this.cachees);
	}

	public void add(Cache cache) {
		this.cachees.add(cache);
		this.storeAll();
	}

	public List<Cache> findAllCache() {
		return this.cachees;
	}

	public void deleteAllCache() {
		this.cachees.clear();
		this.storeAll();
	}

	public List<Cache> findCacheByFieldType(String type, String findStr) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Cache> findCacheByKey(String key) {
		return this.cachees.stream().filter(c -> c.getKey().equals(key)).collect(Collectors.toList());
	}

}
