/**
 * 
 */
package com.cap.api.model.workflow;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class Header implements Serializable {
	
	private String headerKey;
	
	private Object headerValue;

}
