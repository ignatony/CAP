package com.cap.transport.model;

import java.util.List;
import java.util.Map;



import lombok.Data;

@Data
public class Transport {
	private String protocol;
	private String method;
	private String url;
	private boolean cache;
	//private Map<String, Object> header;
	private List<Header> header;
	
	

}
