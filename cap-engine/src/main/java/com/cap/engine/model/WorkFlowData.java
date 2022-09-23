/**
 * 
 */
package com.cap.engine.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class WorkFlowData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private Integer id;
	
	private String name;
	
	@JsonIgnore
	private LocalDateTime date;
	
	private String devName;
	
   private List<Flow> flow;

}
