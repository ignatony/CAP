/**
 * 
 */
package com.cap.commons.logger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.commons.logger.model.Logger;
import com.cap.commons.logger.repo.LoggerRepoImpl;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RequestMapping(path = "/cap-commons/logger")
@RestController
@Slf4j
public class LoggerController {
@Autowired
	LoggerRepoImpl loggerRepoImpl;
	
	@ApiOperation(value = "Create new Logger")
	@PostMapping(path = "/createLog")
	public @ResponseBody ResponseEntity<String> addLogger(@RequestBody Logger logger) {
		log.info("Create Logger");
		loggerRepoImpl.add(logger);
		return new ResponseEntity<String>("Added Succussful", HttpStatus.OK);
	}
	@ApiOperation(value = "Find All Loggers")
	@GetMapping(path = "/findAllLogger")
	public @ResponseBody ResponseEntity<List<Logger>> findAllLogger(){
		log.info("Find All Loggers");
		List<Logger> logger = null;
		logger = loggerRepoImpl.findAllLogger();
		return new ResponseEntity<List<Logger>>(logger, HttpStatus.OK);
	}
	@ApiOperation(value = "Find Logger By FieldType")
	@GetMapping(path = "/findLogFieldType/{type}/{findStr}")
	public @ResponseBody ResponseEntity<List<Logger>> findLoggerByFieldType(@PathVariable String type, @PathVariable String findStr){
		
		log.info("Find Logger By FieldType");
		List<Logger> logger = null;
		logger = loggerRepoImpl.findLoggerByFieldType(type, findStr);
		return new ResponseEntity<List<Logger>>(logger, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Delete All Logger")
	@GetMapping(path = "/deleteAllLog")
	public  @ResponseBody ResponseEntity<String> deleteAllLogger(){
		log.info("Delete All Logger");
		loggerRepoImpl.deleteAllLogger();
		return new ResponseEntity<String>("Deleted All Succussful", HttpStatus.OK);
	}
}
