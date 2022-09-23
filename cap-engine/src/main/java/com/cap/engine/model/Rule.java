/**
 * 
 */
package com.cap.engine.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class Rule implements Serializable{
	private static final long serialVersionUID = 1L;
	private String ruleName;
	private String ruleType;
	private String ruleFileName;
	private String dataType;
	

}
