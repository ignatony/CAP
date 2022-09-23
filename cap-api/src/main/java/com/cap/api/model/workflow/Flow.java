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
public class Flow implements Serializable{
	private static final long serialVersionUID = 1L;
	private int sequence;
	private String flowType; //TRANSPORT|TRANSFORM | RULE | DATABASE | FILE | CONDITION
	private String direction; //IN | OUT
	private String flowName; 
	private String description;
	
	private Transform transform;
	private Transport transport;
	private Rule rule;
}
