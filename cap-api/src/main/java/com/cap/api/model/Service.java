/**
 * 
 */
package com.cap.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
@Entity
public class Service {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer serviceId;
	private String serviceName;
	private String type;
	private String dataType;
	private String domain;
	private String protocol;
	private Integer providerId;
	private Integer customerId;
	private Integer tenantId;
	
	
}
