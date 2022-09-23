package com.cap.transport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.transport.model.CapTransportRequest;
import com.cap.transport.model.Response;
import com.cap.transport.service.CapTransportService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(path = "${api.base.url}")
@RestController
public class CapTransportController {

	@Autowired
	private CapTransportService capTransportService;

	@ApiOperation(value = "${api.executeTransport.desc}")
	@PostMapping(path = "${api.executeTransport.url}")
	public @ResponseBody ResponseEntity<Response> postReqAndGetResponse(
			@RequestBody CapTransportRequest capTransportRequest) {

		return new ResponseEntity<>(capTransportService.processCapRequest(capTransportRequest), HttpStatus.OK);
	}
	
	
}
