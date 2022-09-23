/**
 * 
 */
package com.cap.api.model.workflow;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
public class FileData  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fileType;
	private String fileName;
	private String username;
	private String password;
	private String dataType;
	private boolean zip;

}
