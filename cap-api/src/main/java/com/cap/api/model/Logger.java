package com.cap.api.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Logger implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Logger(String correlationId, String layerName, int flowSequence, String payload, String payloadType,
			String dateTime, String duration, String providerName, String serviceName, String flowName,
			String userName, String domain) {
		super();
		this.correlationId = correlationId;
		this.layerName = layerName;
		this.flowSequence = flowSequence;
		this.payload = payload;
		this.payloadType = payloadType;
		this.dateTime = dateTime;
		this.duration = duration;
		this.providerName = providerName;
		this.serviceName = serviceName;
		this.flowName = flowName;
		this.userName = userName;
		this.domain = domain;
	}

	String correlationId;
	String layerName;
	int flowSequence;
	String payload;
	String payloadType;
	String dateTime;
	String duration;
	String providerName;
	String serviceName;
	String flowName;
	String userName;
	String domain;
}