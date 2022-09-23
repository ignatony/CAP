/**
 * 
 */
package com.cap.engine.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class Cache implements Serializable {

	private static final long serialVersionUID = 1L;

	public Cache(long cacheLifeSpan, String key, String value) {
		super();
		this.cacheLifeSpan = cacheLifeSpan;
		this.key = key;
		this.value = value;
	}

	private long cacheLifeSpan;
	private String key;
	private String value;

}
