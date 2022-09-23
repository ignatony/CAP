/**
 * 
 */
package com.cap.api.model.workflow;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class Transport implements Serializable {
	private static final long serialVersionUID = 1L;
	
	TransportType transportType;
	private String protocol;
	private String method;
	private String url;
	private List<Header> header;
	private Database database;
	private FileData fileData;
	private Cache cache;

}
