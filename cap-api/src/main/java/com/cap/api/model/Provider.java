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
public class Provider {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer providerId;
	private String name;
	private String emailId;
	private String orgName;
	private String domain;
	private String region;
	private Integer customerId;
	private Integer tenantId;
}
