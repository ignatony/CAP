package com.cap.commons.logger.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Logger {

	public Logger(String correlationId, String layerName, int flowSequence, String payload, String payloadType,
			LocalDateTime dateTime, String duration, String providerName, String serviceName, String flowName,
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
	LocalDateTime dateTime;
	String duration;
	String providerName;
	String serviceName;
	String flowName;
	String userName;
	String domain;
}
