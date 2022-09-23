package com.cap.transport.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.transport.handler.CapTranportHandler;
import com.cap.transport.model.CapTransportRequest;
import com.cap.transport.model.Response;
import com.cap.transport.model.ResponseMessage;

@Service
public class CapTransportService {

	@Autowired
	private CapTranportHandler handler;

	public Response processCapRequest(CapTransportRequest capTransportRequest) {
		ResponseMessage responseMessage = null;
		Response response = new Response();
		//response.setStartDateTime(LocalDateTime.now());

		//genStartTimeAndStoreCorelationId(capTransportRequest.getCorrelationId());
		responseMessage = handler.capRequestProcess(capTransportRequest);

		return createTransportResponse(responseMessage, response);
	}

	private Response createTransportResponse(ResponseMessage responseMessage, Response response) {
		response.setStatus(responseMessage.getStatus());
		response.setCorrelationId(responseMessage.getCorrelationId());
		response.setLast(Boolean.TRUE);
		response.setMessage(responseMessage.getMessage());
		response.setPayload(responseMessage.getPayload());
		//response.setEndDateTime(LocalDateTime.now());

		return response;
	}
	
	private void genStartTimeAndStoreCorelationId(String corelationId) {
//		RequestContextHolder.getRequestAttributes().setAttribute("startTime", LocalDateTime.now(),
//				RequestAttributes.SCOPE_REQUEST);
		
		RequestContextHolder.getRequestAttributes().setAttribute("correlationId", corelationId,	RequestAttributes.SCOPE_REQUEST);
	}
	
	


}
