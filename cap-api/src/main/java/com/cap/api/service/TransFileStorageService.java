/**
 * 
 */
package com.cap.api.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cap.api.db.TransFileRepository;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.TransformationFile;
import com.cap.api.model.User;
import com.cap.api.model.workflow.WorkFlowData;
import com.cap.api.model.workflow.WorkFlowRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class TransFileStorageService {

	@Autowired
	private TransFileRepository transFileRepository;

	public TransformationFile storeFile(MultipartFile file, int providerId, int serviceId, User user)
			throws CapAPIException {
		// Normalize file name
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new CapAPIException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			TransformationFile transFile = new TransformationFile();

			transFile.setFileName(fileName);
			transFile.setFileType(file.getContentType());

			transFile.setData(file.getBytes());
			transFile.setProviderId(providerId);
			transFile.setServiceId(serviceId);
			transFile.setTenantId(user.getTenantId());
			transFile.setCustomerId(user.getCustomerId());

			return transFileRepository.save(transFile);
		} catch (IOException ex) {
			throw new CapAPIException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public TransformationFile getTransFile(int providerId, int serviceId, String fileName) {
		TransformationFile transformationFile = null;
		List<TransformationFile> transFileList = transFileRepository.findTransFile(providerId, serviceId, fileName);
		if (!transFileList.isEmpty())
			transformationFile = transFileList.get(0);
		return transformationFile;

	}

	public TransformationFile getTransFileById(int providerId, int serviceId) {
		TransformationFile transformationFile = null;
		List<TransformationFile> transFileList = transFileRepository.findTransFileById(providerId, serviceId);
		if (!transFileList.isEmpty())
			transformationFile = transFileList.get(0);
		return transformationFile;

	}

	public List<TransformationFile> getListFileById(int providerId, int serviceId) {
		return transFileRepository.findTransFileById(providerId, serviceId);

	}
	
	public void deleteFileById(int id) throws CapAPIException {
		log.info("TransFileStorageService::deleteFileById");
		try {
			transFileRepository.deleteById(id);
		} catch (Exception e) {
			log.error("TransFileStorageService::deleteFileById: error: " + e.getMessage());
			throw new CapAPIException("TransFileStorageService::deleteFileById: error: " + e.getMessage());
		}
	}

	public void storeObject(WorkFlowRequest workFlowRequest) throws CapAPIException, IOException {
		WorkFlowData flowData = workFlowRequest.getWorkFlowData();
		TransformationFile transFile = new TransformationFile();

		transFile.setFileName("workFlowData");
		transFile.setFileType("application/x-binary");
		transFile.setData(SerializationUtils.serialize(flowData));
		transFile.setProviderId(workFlowRequest.getProviderId());
		transFile.setServiceId(workFlowRequest.getServiceId());
		transFile.setCustomerId(workFlowRequest.getCustomerId());
		transFile.setTenantId(workFlowRequest.getTenantId());

		transFileRepository.save(transFile);
	}

}
