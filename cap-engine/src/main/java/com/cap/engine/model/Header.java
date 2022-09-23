package com.cap.engine.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;

	private String headerKey;
	
	private Object headerValue;
}
