/**
 * 
 */
package com.cap.engine.component.model;

import java.io.Serializable;
import java.util.Map;

import com.cap.engine.model.Transport;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class TransportRequest  implements Serializable{
	
	private String correlationId;
	private String transactionId;
	private Map<String, Object> customHeader;
	private String payload;
	//private String newPayload;
	
	private Integer providerId;
	private Integer serviceId;
	private Integer TransFileId;
	private Integer customerId;
	private Integer tenantId;
	
	private Transport transport;

}
