/**
 * 
 */
package com.cap.engine.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.engine.exception.CapEngineException;
import com.cap.engine.handler.ProcessHandler;
import com.cap.engine.model.CapEngineRequest;
import com.cap.engine.model.Mapperequest;
import com.cap.engine.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Component
@Slf4j
public class Excecuter { 
	@Autowired
	ProcessHandler processHandler;

	public Response process(CapEngineRequest capEngineRequest) throws CapEngineException{
		log.info("Cap Engine::Excecuter::process");
		if(null == capEngineRequest) {
			log.error ("Cap Engine::Excecuter::process :: capEngineRequest is : null");
			throw new CapEngineException("Cap Engine::Excecuter::process :: capEngineRequest is : null");
		}
		
		Response response = processHandler.executeProcess(capEngineRequest);
		
		return response;
	}
	

}
