/**
 * 
 */
package com.cap.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

/**
 * @author Ignatious
 *
 */
@Data
@Entity
public class TransformationFile {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer transFileId;

    private String fileName;

    private String fileType;
    
    private Integer serviceId;
    
    private Integer providerId;
    
    private Integer customerId;
    
    private Integer tenantId;

    @Lob
    private byte[] data;
    

}
