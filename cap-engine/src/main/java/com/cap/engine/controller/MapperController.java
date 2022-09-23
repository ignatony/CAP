/**
 * 
 */
package com.cap.engine.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.engine.constant.CapEngineConstant;
import com.cap.engine.model.CapEngineRequest;
import com.cap.engine.model.Response;
import com.cap.engine.process.Excecuter;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class MapperController {
	@Autowired
	Excecuter excecuter;

	@ApiOperation(value = "${api.executeService.desc}")
	@PostMapping(path = "${api.executeService.url}")
	public @ResponseBody ResponseEntity<Response> executeEngineService(@RequestBody CapEngineRequest capEngineRequest) {
		Response response = null;
		genStartTime(capEngineRequest);
		response = excecuter.process(capEngineRequest);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}


	private void genStartTime(CapEngineRequest capEngineRequest) {
		RequestAttributes reqAttribute = RequestContextHolder.getRequestAttributes();
		reqAttribute.setAttribute(CapEngineConstant.STARTTIME, LocalDateTime.now(ZoneOffset.UTC),
				RequestAttributes.SCOPE_REQUEST);
		reqAttribute.setAttribute(CapEngineConstant.UUID, capEngineRequest.getCorrelationId(), RequestAttributes.SCOPE_REQUEST);
		reqAttribute.setAttribute(CapEngineConstant.SERVICE_NAME, capEngineRequest.getCapExRequest().getServiceName(),
				RequestAttributes.SCOPE_REQUEST);
//		reqAttribute.setAttribute(CapEngineConstant.USER_ID, capEngineRequest.g, RequestAttributes.SCOPE_REQUEST);
//
//		reqAttribute.setAttribute(CapEngineConstant.TRANS_ID, capExecuteRequest.getTransactionId(),
//				RequestAttributes.SCOPE_REQUEST);
	}

}
