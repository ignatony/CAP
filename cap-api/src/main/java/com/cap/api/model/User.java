/**
 * 
 */
package com.cap.api.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer userId;
	private String name;
	private String username;
	private String password;
	private String address;
	private Integer customerId;
	private Integer tenantId;
	
	  @ManyToMany(fetch = FetchType.EAGER)
	  @JoinTable(  name = "userRoles",
	        joinColumns = @JoinColumn(name = "userId"), 
	        inverseJoinColumns = @JoinColumn(name = "roleId"))
	  private Set<Role> roles = new HashSet<>();
}
