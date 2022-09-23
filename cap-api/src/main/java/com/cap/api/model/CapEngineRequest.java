/**
 * 
 */
package com.cap.api.model;

import java.io.Serializable;

import com.cap.api.model.workflow.WorkFlowRequest;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CapEngineRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String correlationId;
	CapExecuteRequest capExRequest;
	WorkFlowRequest workFlowRequest;
	

}
