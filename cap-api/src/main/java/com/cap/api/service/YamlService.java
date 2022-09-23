/**
 * 
 */
package com.cap.api.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.workflow.WorkFlowData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class YamlService {
	
	public WorkFlowData loadYaml(byte[] fileDate)  throws CapAPIException{
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());		
		WorkFlowData workFlowRequest = null;
		try {
			workFlowRequest = mapper.readValue(fileDate, WorkFlowData.class);
		} catch (IOException e) {
			log.error("loadYaml failed in YamlService : "+ e.getMessage());
			throw new CapAPIException("loadYaml failed in YamlService : "+ e.getMessage());
		}	
		return workFlowRequest;
	}
	
//	public void createYaml(String yamlfile, Object obj) {
//		
//		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
//		
//		try {
//			mapper.writeValue(new File("src/main/resources/orderOutput.yaml"), obj);
//		} catch (IOException e) {
//		}
//		
	
	public byte[] createYaml(WorkFlowData workFlowRequest) {
		//ObjectMapper mapper = new ObjectMapper(new YAMLFactory());	
		return  SerializationUtils.serialize(workFlowRequest);
	}

}
