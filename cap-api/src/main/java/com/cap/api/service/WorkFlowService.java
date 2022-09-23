/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.cap.api.db.WorkFlowRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.TransformationFile;
import com.cap.api.model.WorkFlow;
import com.cap.api.model.workflow.Flow;
import com.cap.api.model.workflow.WorkFlowData;
import com.cap.api.model.workflow.WorkFlowRequest;
import com.cap.api.utils.ObjectMapperUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class WorkFlowService {

	@Autowired
	WorkFlowRepo workFlowRepo;
	@Autowired
	TransFileStorageService transFileStorageService;

	/**
	 * @param workFlowRequest
	 * @return
	 * @throws CapAPIException
	 */
	public Response createWorkFlow(WorkFlowRequest workFlowRequest) throws CapAPIException {
		log.info("WorkFlowService::createWorkFlow");
		try {
			WorkFlow workflow = ObjectMapperUtils.map(workFlowRequest, WorkFlow.class);

			transFileStorageService.storeObject(workFlowRequest);

			workFlowRepo.save(workflow);
		} catch (Exception e) {
			log.error("WorkFlowService::createWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::createWorkFlow: error: " + e.getMessage());
		}
		return getResponse("Created WorkFlow Successfully ", "Success");
	}

	/**
	 * @param workFlow
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateWorkFlow(WorkFlowRequest workFlowRequest) throws CapAPIException {
		log.info("WorkFlowService::updateWorkFlow");
		try {

			TransformationFile tranFile = transFileStorageService.getTransFile(workFlowRequest.getProviderId(),
					workFlowRequest.getServiceId(), "workFlowData");

			if (tranFile != null) {
				transFileStorageService.deleteFileById(tranFile.getTransFileId());

			}
			WorkFlow workflow = ObjectMapperUtils.map(workFlowRequest, WorkFlow.class);
			transFileStorageService.storeObject(workFlowRequest);
			workFlowRepo.save(workflow);
		} catch (Exception e) {
			log.error("WorkFlowService::updateWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::updateWorkFlow: error: " + e.getMessage());
		}
		return getResponse("Updated WorkFlow Successfully ", "Success");
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public WorkFlowRequest getWorkFlow(int id) throws CapAPIException {
		Optional<WorkFlow> workFlow = null;
		log.info("WorkFlowService::getWorkFlow");

		WorkFlowRequest req = null;
		try {
			workFlow = workFlowRepo.findById(id);
			if (workFlow.isPresent()) {

				WorkFlow wFlow = workFlow.get();
				req = ObjectMapperUtils.map(wFlow, WorkFlowRequest.class);

				TransformationFile transFile = transFileStorageService.getTransFile(wFlow.getProviderId(),
						wFlow.getServiceId(), "workFlowData");
				if (transFile != null) {
					WorkFlowData flowData = (WorkFlowData) SerializationUtils.deserialize(transFile.getData());
					req.setWorkFlowData(flowData);
					if(!flowData.getFlow().isEmpty()) {
						Flow flow = flowData.getFlow().stream().max(Comparator.comparing(Flow::getSequence)).get();
						req.setCurrentSequence(flow.getSequence());
					}
					
					System.out.print(flowData.toString());
				}
				
			}
		} catch (Exception e) {
			log.error("WorkFlowService::getWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::getWorkFlow: error: " + e.getMessage());
		}
		return req;
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public List<WorkFlowRequest> getWorkFlowServiceId(int id) throws CapAPIException {
		List<WorkFlow> workFlow = null;
		List<WorkFlowRequest> workFlowRequests = new ArrayList<>();
		log.info("WorkFlowService::getWorkFlowServiceId");

		WorkFlowRequest req = null;
		try {
			workFlow = workFlowRepo.findWorkFlowBySerId(id);
			for (WorkFlow flow : workFlow) {

				WorkFlow wFlow = workFlow.get(0);
				req = ObjectMapperUtils.map(wFlow, WorkFlowRequest.class);

				TransformationFile transFile = transFileStorageService.getTransFile(wFlow.getProviderId(),
						wFlow.getServiceId(), wFlow.getWorkFlowName());
				if (transFile != null) {
					WorkFlowData flowData = (WorkFlowData) SerializationUtils.deserialize(transFile.getData());
					req.setWorkFlowData(flowData);
					System.out.print(flowData.toString());
				}
				workFlowRequests.add(req);
			}
		} catch (Exception e) {
			log.error("WorkFlowService::getWorkFlowServiceId: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::getWorkFlowServiceId: error: " + e.getMessage());
		}
		return workFlowRequests;
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<WorkFlow> getAllWorkFlow() throws CapAPIException {
		Optional<Iterable<WorkFlow>> workFlow = null;
		log.info("WorkFlowService::getAllWorkFlow");
		try {
			workFlow = Optional.of(workFlowRepo.findAll());

		} catch (Exception e) {
			log.error("WorkFlowService::getAllWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::getAllWorkFlow: error: " + e.getMessage());
		}
		return workFlow.isPresent() ? workFlow.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public Response delteWorkFlow(int id) throws CapAPIException {
		log.info("WorkFlowService::delteWorkFlow");
		try {
			workFlowRepo.deleteById(id);
		} catch (Exception e) {
			log.error("WorkFlowService::delteWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::delteWorkFlow: error: " + e.getMessage());
		}
		return getResponse("Deleted WorkFlow Successfully ", "Success");
	}

	public WorkFlowRequest getWorkFlowByCusProvServId(int customerId, int providerId, int serviceId)
			throws CapAPIException {
		List<WorkFlow> workFlow = null;
		log.info("WorkFlowService::getgetWorkFlowByCusProvServIdWorkFlow");

		WorkFlowRequest req = null;
		try {
			workFlow = workFlowRepo.findWorkFlowByCustProvSerId(customerId, providerId, serviceId);
			if (!workFlow.isEmpty()) {

				WorkFlow wFlow = workFlow.get(0);
				req = ObjectMapperUtils.map(wFlow, WorkFlowRequest.class);

				TransformationFile transFile = transFileStorageService.getTransFile(wFlow.getProviderId(),
						wFlow.getServiceId(), "workFlowData");
				if (transFile != null) {
					WorkFlowData flowData = (WorkFlowData) SerializationUtils.deserialize(transFile.getData());
					req.setWorkFlowData(flowData);
					System.out.print(flowData.toString());
				}
			}
		} catch (Exception e) {
			log.error("WorkFlowService::getWorkFlowByCusProvServIdtWorkFlow: error: " + e.getMessage());
			throw new CapAPIException("WorkFlowService::getWorkFlowByCusProvServId: error: " + e.getMessage());
		}
		return req;
	}

	/**
	 * @param message
	 * @param status
	 * @return
	 */
	private Response getResponse(String message, String status) {
		return new Response(null, null, LocalDateTime.now(ZoneOffset.UTC), status, message);
	}

}
