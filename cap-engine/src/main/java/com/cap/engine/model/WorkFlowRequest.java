package com.cap.engine.model;



import java.io.Serializable;



import lombok.Data;
@Data
public class WorkFlowRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer workFlowId;
	private String workFlowName;
	private String type;
	private String dataType;
	private String domain;
	private String protocol;
	private Integer providerId;
	private Integer serviceId;
	private Integer customerId;
	private Integer tenantId;
	
	private Integer currentSequence;
	
	private WorkFlowData workFlowData;
	
}
