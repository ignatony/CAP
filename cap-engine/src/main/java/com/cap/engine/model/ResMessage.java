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
public class ResMessage {

	private String correlationId;
	private String status;
	private String outPayload;

}
