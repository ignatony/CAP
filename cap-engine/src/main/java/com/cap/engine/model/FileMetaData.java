/**
 * 
 */
package com.cap.engine.model;

import org.springframework.core.io.Resource;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class FileMetaData {

	private String fileName;
	private String url;
	private String mime;
	private long size;
	private Resource resource;
}
