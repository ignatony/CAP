package com.cap.api.model.jwt;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
@Data
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private String token;
	  private String type = "Bearer";
	  private Integer id;
	  private String username;
	  private String name;
	  private List<String> roles;
	
	  public JwtResponse(String accessToken, Integer id, String username, String name, List<String> roles) {
		    this.token = accessToken;
		    this.id = id;
		    this.username = username;
		    this.name = name;
		    this.roles = roles;
		  }

}