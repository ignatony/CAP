/**
 * 
 */
package com.cap.api.model;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class CapExecuteRequest  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2927786043304889014L;
	
	private String transactionId;
	private String providerName;
	private String ServiceName;
	private Map<String,Object> customHeader;
	private String payload;
}
