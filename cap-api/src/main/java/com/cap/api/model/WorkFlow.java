/**
 * 
 */
package com.cap.api.model;


import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkFlow  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer workFlowId;
	private String workFlowName;
	private String type;
	private String dataType;
	private String domain;
	private String protocol;

	private Integer providerId;

	private Integer serviceId;
	private Integer TransFileId;
	
	private Integer customerId;
	private Integer tenantId;
	
	
	
}
