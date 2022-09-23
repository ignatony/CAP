package com.cap.transport.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class Header implements Serializable {
	
	private String headerKey;
	
	private Object headerValue;

}