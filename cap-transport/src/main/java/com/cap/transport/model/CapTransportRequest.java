package com.cap.transport.model;

import java.util.Map;

public class CapTransportRequest {

	private String correlationId;
	private String transactionId;
	private Map<String, Object> customHeader;
	private String payload;
	private String newPayload;

	private Integer providerId;
	private Integer serviceId;
	private Integer TransFileId;
	private Integer customerId;
	private Integer tenantId;

	private Transport transport;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, Object> getCustomHeader() {
		return customHeader;
	}

	public void setCustomHeader(Map<String, Object> customHeader) {
		this.customHeader = customHeader;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getNewPayload() {
		return newPayload;
	}

	public void setNewPayload(String newPayload) {
		this.newPayload = newPayload;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getTransFileId() {
		return TransFileId;
	}

	public void setTransFileId(Integer transFileId) {
		TransFileId = transFileId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getTenantId() {
		return tenantId;
	}

	public void setTenantId(Integer tenantId) {
		this.tenantId = tenantId;
	}

	public Transport getTransport() {
		return transport;
	}

	public void setTransport(Transport transport) {
		this.transport = transport;
	}
	
	
}
