package com.cap.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Tenant  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer tenantId;
	private String tenantName;
}
