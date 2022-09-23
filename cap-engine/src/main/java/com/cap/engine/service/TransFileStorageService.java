/**
 * 
 */
package com.cap.engine.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cap.engine.db.TransFileRepository;
import com.cap.engine.exception.CapEngineException;
import com.cap.engine.model.TransformationFile;
import com.cap.engine.model.WorkFlowData;
import com.cap.engine.model.WorkFlowRequest;



/**
 * @author Ignatious
 *
 */
@Service
public class TransFileStorageService {

	@Autowired
	private TransFileRepository transFileRepository;

	public TransformationFile storeFile(MultipartFile file, int providerName, int serviceName) throws CapEngineException {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new CapEngineException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			TransformationFile transFile = new TransformationFile();

			transFile.setFileName(fileName);
			transFile.setFileType(file.getContentType());
			transFile.setData(file.getBytes());
			transFile.setProviderId(providerName);
			transFile.setServiceId(serviceName);

			return transFileRepository.save(transFile);
		} catch (IOException ex) {
			throw new CapEngineException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public TransformationFile getTransFile(int consumerId,int providerId, int serviceId, String fileName) {
		TransformationFile transformationFile = null;
		List<TransformationFile> transFileList = transFileRepository.findTransFile( consumerId, providerId,  serviceId,  fileName);
		if(!transFileList.isEmpty())
			transformationFile = transFileList.get(0);
		return transformationFile;

	}
	
	public byte[] getTransFileBytes(int consumerId,int providerId, int serviceId, String fileName) {
		TransformationFile transformationFile = null;
		List<TransformationFile> transFileList = transFileRepository.findTransFile( consumerId, providerId,  serviceId,  fileName);
		if(!transFileList.isEmpty()) {
			 transformationFile = transFileList.get(0);
		}
		return transformationFile != null ? transformationFile.getData() : null;
	}
	
	public void storeObject(WorkFlowRequest workFlowRequest) throws CapEngineException, IOException {
		WorkFlowData flowData  = workFlowRequest.getWorkFlowData();
		TransformationFile transFile = new TransformationFile();
		

		transFile.setFileName(workFlowRequest.getWorkFlowName());
		transFile.setFileType("flowData");
		transFile.setData( SerializationUtils.serialize(flowData));
		transFile.setProviderId(workFlowRequest.getProviderId());
		transFile.setServiceId(workFlowRequest.getServiceId());
		transFile.setCustomerId(workFlowRequest.getCustomerId());
		transFile.setTenantId(workFlowRequest.getTenantId());

		 transFileRepository.save(transFile);
	}
	
	
}
