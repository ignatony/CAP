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
public class Exchanger  {
	
	private String correlationId;
	private String transactionId;
	private String providerName;
	private String ServiceName;
	private Map<String, Object> customHeader;
	private String payload;
	private String newPayload;

	private Integer workFlowId;
	private String workFlowName;
	private String type;
	private String dataType;
	private String domain;
	private String protocol;

	private Integer providerId;
	private Integer serviceId;
	private Integer TransFileId;
	private Integer customerId;
	private Integer tenantId;

	private WorkFlowData workFlowData;

}
