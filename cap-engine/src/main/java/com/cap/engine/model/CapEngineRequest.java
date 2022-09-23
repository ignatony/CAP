/**
 * 
 */
package com.cap.engine.model;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class CapEngineRequest {

	private String correlationId;
	private CapExecuteRequest capExRequest;
	private WorkFlowRequest workFlowRequest;

}
