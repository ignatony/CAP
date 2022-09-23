/**
 * 
 */
package com.cap.engine.model;

import java.util.Map;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class CapExecuteRequest {
	private String transactionId;
	private String providerName;
	private String ServiceName;
	private Map<String,Object> customHeader;
	private String payload;
}
