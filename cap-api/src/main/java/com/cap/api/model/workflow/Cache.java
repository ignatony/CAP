/**
 * 
 */
package com.cap.api.model.workflow;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data 
public class Cache implements Serializable {

	private static final long serialVersionUID = 1L;
	private long cacheLifeSpan;
	private String key;
	private String value;

}
