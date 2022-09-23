/**
 * 
 */
package com.cap.engine.handler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.cap.engine.component.PrepareComponent;
import com.cap.engine.component.RuleComponent;
import com.cap.engine.component.TransformComponent;
import com.cap.engine.component.TransportComponent;
import com.cap.engine.constant.CapEngineConstant;
import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.CapEngineRequest;
import com.cap.engine.model.CapExecuteRequest;
import com.cap.engine.model.Exchanger;
import com.cap.engine.model.Flow;
import com.cap.engine.model.ResMessage;
import com.cap.engine.model.Response;
import com.cap.engine.model.WorkFlowData;
import com.cap.engine.model.WorkFlowRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class ProcessHandler {
	@Autowired
	TransformComponent transformComponent;
	@Autowired
	TransportComponent transportComponent;
	@Autowired
	RuleComponent ruleComponent;


	public Response executeProcess(CapEngineRequest capEngineRequest) throws CapEngineException {
		Response response = null;

		Exchanger exchanger = exchange(capEngineRequest);

		WorkFlowData workFlowData = exchanger.getWorkFlowData();
		if (null == workFlowData) {
			log.info("ProcessHandler :: executeProcess workFlowData null");
			throw new CapEngineException("ProcessHandler :: executeProcess workFlowData null");
		}

		List<Flow> flows = workFlowData.getFlow();
		if (!flows.isEmpty()) {

			List<Flow> sortedFlows = flows.stream().sorted(Comparator.comparingInt(Flow::getSequence))
					.collect(Collectors.toList());

			try {
				for (Flow flow : sortedFlows) {
					ResMessage message = getComponent(flow).prepare(exchanger, flow);

					if (message != null && message.getOutPayload() != null) {
						exchanger.setNewPayload(message.getOutPayload());
					}
				}
			} catch (CapEngineException e) {
				log.info("ProcessHandler :: executeProcess error {}", e.getMessage());
				throw new CapEngineException("ProcessHandler :: executeProcess error " + e.getMessage());
			}
			response = genResponse(capEngineRequest.getCorrelationId(), "Success", "", exchanger.getNewPayload());
		}

		return response;
	}

	/**
	 * @param flowType
	 * @return
	 * @throws CapEngineException
	 */
	private PrepareComponent getComponent(Flow flow) throws CapEngineException {
		log.info("ProcessHandler :: getComponent flowType {}", flow.getFlowType());

		PrepareComponent prepareComponent = null;

		String flowType = flow.getFlowType();

		if (flowType.equalsIgnoreCase(CapEngineConstant.TRANSFORM)) {
			prepareComponent = (PrepareComponent) transformComponent;

		} else if (flowType.equalsIgnoreCase(CapEngineConstant.TRANSPORT)) {
			prepareComponent = (PrepareComponent) transportComponent;
			
		} else if (flowType.equalsIgnoreCase(CapEngineConstant.RULE)) {
			prepareComponent = (PrepareComponent) ruleComponent;
		}
		return prepareComponent;
	}

	/**
	 * @param correlationId
	 * @param status
	 * @param message
	 * @param payload
	 * @return
	 */
	private Response genResponse(String correlationId, String status, String message, String payload) {
		return new Response(correlationId, getStartDate(), LocalDateTime.now(ZoneOffset.UTC), status, message, payload,
				false);
	}

	/**
	 * @param capEng
	 * @return
	 */
	private Exchanger exchange(CapEngineRequest capEng) {

		log.info("ProcessHandler :: exchange");
		Exchanger exchanger = new Exchanger();

		exchanger.setCorrelationId(capEng.getCorrelationId());
		CapExecuteRequest capEx = capEng.getCapExRequest();

		exchanger.setTransactionId(capEx.getTransactionId());
		exchanger.setProviderName(capEx.getProviderName());
		exchanger.setServiceName(capEx.getServiceName());
		exchanger.setCustomHeader(capEx.getCustomHeader());
		exchanger.setPayload(capEx.getPayload());
		exchanger.setNewPayload(capEx.getPayload());

		WorkFlowRequest capFlow = capEng.getWorkFlowRequest();
		exchanger.setWorkFlowName(capFlow.getWorkFlowName());
		exchanger.setType(capFlow.getType());
		exchanger.setDataType(capFlow.getDataType());
		exchanger.setDomain(capFlow.getDomain());
		exchanger.setProtocol(capFlow.getProtocol());

		exchanger.setProviderId(capFlow.getProviderId());
		exchanger.setServiceId(capFlow.getServiceId());
		//exchanger.setTransFileId(capFlow.getTransFileId());
		exchanger.setCustomerId(capFlow.getCustomerId());
		exchanger.setTenantId(capFlow.getTenantId());

		exchanger.setWorkFlowData(capFlow.getWorkFlowData());

		return exchanger;
	}

	/**
	 * @return
	 */
	private LocalDateTime getStartDate() {
		return (LocalDateTime) RequestContextHolder.getRequestAttributes().getAttribute(CapEngineConstant.STARTTIME,
				RequestAttributes.SCOPE_REQUEST);
	}
}
