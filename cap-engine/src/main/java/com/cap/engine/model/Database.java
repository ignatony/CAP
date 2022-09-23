package com.cap.engine.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Database implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String dbType;
	private String username;
	private String password;
	private String schemaName;
	private String query;
	private String queryType;
	private String dataType;
}
