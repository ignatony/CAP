/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.api.component.CapEngineComponent;
import com.cap.api.constant.CapApiConstant;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.CapExecuteRequest;
import com.cap.api.model.Provider;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.model.workflow.WorkFlowRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class ServiceExecutionService {
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
	ServiceService capServiceLayer;
	@Autowired
	ProviderService providerService;
	@Autowired
	CapEngineComponent capEngineComponent;
	
	public Response executeService(CapExecuteRequest capExecuteRequest, User user, String correlationId )throws CapAPIException{
		Response res = null;
		
		if(null == capExecuteRequest) {
			log.error("Invalid Input Request");
			throw new CapAPIException("Invalid Input Request") ;
		}
		
		int tenantId =user.getTenantId();
		int customerId = user.getCustomerId();
		
		Provider provider = getProvIderByName(capExecuteRequest.getProviderName(), tenantId, customerId);
		
		if(null == provider) {
			log.error("Provider Not Exist");
			throw new CapAPIException("Provider is Not Exist") ;
		}
		
		com.cap.api.model.Service service = getServiceIdByName(capExecuteRequest.getServiceName(), tenantId, customerId, provider.getProviderId());
		if(null == service) {
			log.error("Service Not Exist");
			throw new CapAPIException("Service is Not Exist") ;
		}
		
		WorkFlowRequest  workFlowRequest = workFlowService.getWorkFlowByCusProvServId(customerId, provider.getProviderId(), service.getServiceId());
		if(null == workFlowRequest) {
			log.error("WorkFlowRequest Data is Not Exist");
			throw new CapAPIException("WorkFlowRequest Data is Not Exist") ;
		}
		
		res = capEngineComponent.process(capExecuteRequest, workFlowRequest, correlationId);
		if(res == null) {
			res = new Response(correlationId, getStartDate() , LocalDateTime.now(ZoneOffset.UTC), "Failed", "Cap Engine Response null");
		}
		
		return res;
	}
 
	/**
	 * @param serviceName
	 * @param tenantId
	 * @param customerId
	 * @param providerId
	 * @return
	 * @throws CapAPIException
	 */
	private com.cap.api.model.Service getServiceIdByName(String serviceName, int tenantId, int customerId, int providerId) throws CapAPIException{
		return capServiceLayer.getServiceIdByName( serviceName,  tenantId,  customerId,  providerId);
		 
	}

	/**
	 * @param providerName
	 * @param tenantId
	 * @param customerId
	 * @return
	 * @throws CapAPIException
	 */
	private Provider getProvIderByName(String providerName, int tenantId, int customerId) throws CapAPIException{
		return providerService.getProvIderByName( providerName,  tenantId,  customerId);
		
	}
	
	/**
	 * @return
	 */
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute(CapApiConstant.STARTTIME,
				RequestAttributes.SCOPE_REQUEST);
	}

	
}
