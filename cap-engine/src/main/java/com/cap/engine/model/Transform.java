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
public class Transform implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String transformName;
	private String transformType;
	private String transformFileName;
	private String sourceFileName;
	private String sourceFileType;
	
	
	
	

}
