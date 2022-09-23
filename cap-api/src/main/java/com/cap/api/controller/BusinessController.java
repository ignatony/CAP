/**
 * 
 */
package com.cap.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

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

import com.cap.api.constant.CapApiConstant;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.CapExecuteRequest;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.service.LoggerService;
import com.cap.api.service.ServiceExecutionService;
import com.cap.api.service.TransactionLoggerService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class BusinessController {
	@Autowired
	ServiceExecutionService serviceExecutionService;
	@Autowired
	TransactionLoggerService transactionLoggerService;

	/**
	 * @param capExecuteRequest
	 * @param request
	 * @return
	 * @throws CapAPIException
	 * @throws InterruptedException 
	 */
	@ApiOperation(value = "${api.createBussinesReq.desc}")
	@PostMapping(path = "${api.createBussinesReq.url}")
	public @ResponseBody ResponseEntity<Response> createBussinesReq(@RequestBody CapExecuteRequest capExecuteRequest,
			HttpServletRequest request) throws CapAPIException, InterruptedException {
		log.info("BusinessController::createBussinesReq:: request is {}", capExecuteRequest);

		User user = (User) request.getAttribute("USER");
		if (null == user) {
			log.error("InValid User Request !!");
			throw new CapAPIException("InValid User Request !!");
		}
		Response response = null;
		String correlationId = createCorrelation(user.getName(), capExecuteRequest.getServiceName());
		genStartTime(capExecuteRequest, correlationId, user.getName());
		transactionLoggerService.setInLogger(capExecuteRequest, user, correlationId);
		response = serviceExecutionService.executeService(capExecuteRequest, user, correlationId);
		transactionLoggerService.setOutLogger(capExecuteRequest, user, correlationId,response);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param userName
	 * @param serviceName
	 * @return
	 */
	private String createCorrelation(String userName, String serviceName) {
		LocalDateTime today = LocalDateTime.now(ZoneOffset.UTC);
		String strDate = today.format(DateTimeFormatter.ofPattern("dd_MMM_yy_HH:mm:ss"));
		return userName + "_" + serviceName + "_" + strDate;
	}
	/**
	 * @param capExecuteRequest
	 * @param UUID
	 * @param userName
	 */
	private void genStartTime(CapExecuteRequest capExecuteRequest, String UUID, String userName) {
		RequestAttributes reqAttribute = RequestContextHolder.getRequestAttributes();
		reqAttribute.setAttribute(CapApiConstant.STARTTIME, LocalDateTime.now(ZoneOffset.UTC),
				RequestAttributes.SCOPE_REQUEST);
		reqAttribute.setAttribute(CapApiConstant.UUID, UUID, RequestAttributes.SCOPE_REQUEST);
		reqAttribute.setAttribute(CapApiConstant.SERVICE_NAME, capExecuteRequest.getServiceName(),
				RequestAttributes.SCOPE_REQUEST);
		reqAttribute.setAttribute(CapApiConstant.USER_ID, userName, RequestAttributes.SCOPE_REQUEST);

		reqAttribute.setAttribute(CapApiConstant.TRANS_ID, capExecuteRequest.getTransactionId(),
				RequestAttributes.SCOPE_REQUEST);
	}

}
