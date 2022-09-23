/**
 * 
 */
package com.cap.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.model.WorkFlow;
import com.cap.api.model.workflow.WorkFlowRequest;
import com.cap.api.service.WorkFlowService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class WorkFlowController {

	@Autowired
	WorkFlowService workFlowService;

	/**
	 * @param workFlow
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.createWorkFlow.desc}")
	@PostMapping(path = "${api.createWorkFlow.url}")
	public @ResponseBody ResponseEntity<Response> createWorkFlow(@RequestBody WorkFlowRequest workFlowRequest, HttpServletRequest request)
			throws CapAPIException {
		log.info("WorkFlowController::createWorkFlow");
		 User user = (User) request.getAttribute("USER");
		 workFlowRequest.setCustomerId(user.getCustomerId());
		 workFlowRequest.setTenantId(user.getTenantId());
		return new ResponseEntity<>(workFlowService.createWorkFlow(workFlowRequest), HttpStatus.OK);
	}

	/**
	 * @param workFlow
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.updateWorkFlow.desc}")
	@PutMapping(path = "${api.updateWorkFlow.url}")
	public @ResponseBody ResponseEntity<Response> updateWorkFlow(@RequestBody WorkFlowRequest workFlowRequest, HttpServletRequest request)
			throws CapAPIException {
		log.info("WorkFlowController::updateWorkFlow");
		User user = (User) request.getAttribute("USER");
		 workFlowRequest.setCustomerId(user.getCustomerId());
		 workFlowRequest.setTenantId(user.getTenantId());
		return new ResponseEntity<>(workFlowService.updateWorkFlow(workFlowRequest), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getWorkFlow.desc}")
	@GetMapping(path = "${api.getWorkFlow.url}/{id}")
	public @ResponseBody ResponseEntity<WorkFlowRequest> getWorkFlow(@PathVariable int id) throws CapAPIException {
		log.info("WorkFlowController::getWorkFlow");
		return new ResponseEntity<>(workFlowService.getWorkFlow(id), HttpStatus.OK);
	}
	
	@ApiOperation(value = "${api.getWorkFlowByServiceId.desc}")
	@GetMapping(path = "${api.getWorkFlowByServiceId.url}/{id}")
	public @ResponseBody ResponseEntity<List<WorkFlowRequest>> getWorkFlowByServiceId(@PathVariable int id) throws CapAPIException {
		log.info("WorkFlowController::getWorkFlow");
		return new ResponseEntity<>(workFlowService.getWorkFlowServiceId(id), HttpStatus.OK);
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getAllWorkFlows.desc}")
	@GetMapping(path = "${api.getAllWorkFlows.url}")
	public @ResponseBody ResponseEntity<Iterable<WorkFlow>> getAllWorkFlows() throws CapAPIException {
		log.info("WorkFlowController::getAllWorkFlows");
		return new ResponseEntity<>(workFlowService.getAllWorkFlow(), HttpStatus.OK);
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.deleteWorkFlow.desc}")
	@DeleteMapping(path = "${api.deleteWorkFlow.url}/{id}")
	public @ResponseBody ResponseEntity<Response> deleteWorkFlow(@PathVariable int id) throws CapAPIException {
		log.info("WorkFlowController::deleteWorkFlow");
		return new ResponseEntity<>(workFlowService.delteWorkFlow(id), HttpStatus.OK);
	}
}
