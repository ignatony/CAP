package com.cap.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.api.exception.CapAPIException;
import com.cap.api.service.LoggerService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RequestMapping(path = "/cap-api/logger")
@RestController
@Slf4j
public class LoggerController {
@Autowired
LoggerService loggerService;
	

	@ApiOperation(value = "Find All Loggers")
	@GetMapping(path = "/findAllLogger")
	public @ResponseBody ResponseEntity<List<Object>> findAllLogger() throws CapAPIException{
		log.info("Find All Loggers");
		List<Object> logger = null;
		logger = loggerService.findAllLogger();
		return new ResponseEntity<List<Object>>(logger, HttpStatus.OK);
	}
}
	