/**
 * 
 */
package com.cap.commons.cache.model;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class Cache {

	public Cache(long cacheLifeSpan, String key, String value, LocalDateTime dateTime) {
		super();
		this.cacheLifeSpan = cacheLifeSpan;
		this.key = key;
		this.value = value;
		this.dateTime = dateTime;
	}

	private long cacheLifeSpan;
	private String key;
	private String value;
	LocalDateTime dateTime;

}
