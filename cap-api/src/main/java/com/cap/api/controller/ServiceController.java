/**
 * 
 */
package com.cap.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.Service;
import com.cap.api.model.User;
import com.cap.api.service.ServiceService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class ServiceController {

	@Autowired
	ServiceService serviceService;

	@ApiOperation(value = "${api.createService.desc}")
	@PostMapping(path = "${api.createService.url}")
	public @ResponseBody ResponseEntity<Response> createService(@RequestBody Service service, HttpServletRequest request) throws CapAPIException {

		log.info("ServiceController :: createService ");
		Response response = null;
		try {
			 User user = (User) request.getAttribute("USER");
			 service.setCustomerId(user.getCustomerId());
			 service.setTenantId(user.getTenantId());
			response = serviceService.createService(service);
		} catch (Exception e) {
			log.error("ServiceController :: createService:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: createService:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param Service
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.updateService.desc}")
	@PutMapping(path = "${api.updateService.url}")
	public @ResponseBody ResponseEntity<Response> updateService(@RequestBody Service service,  HttpServletRequest request) throws CapAPIException {

		log.info("ServiceController :: updateService ");
		Response response = null;
		try {
			User user = (User) request.getAttribute("USER");
			 service.setCustomerId(user.getCustomerId());
			 service.setTenantId(user.getTenantId());
			response = serviceService.updateService(service);
		} catch (Exception e) {
			log.error("ServiceController :: updateService:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: updateService:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.getService.desc}")
	@GetMapping(path = "${api.getService.url}/{id}")
	public @ResponseBody ResponseEntity<Service> getService(@PathVariable int id) throws CapAPIException {

		log.info("ServiceController :: getService ");
		Service Service = null;
		try {
			Service = serviceService.getService(id);
		} catch (Exception e) {
			log.error("ServiceController :: getService:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: getService:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(Service, HttpStatus.OK);
	}
	
	@ApiOperation(value = "${api.getProviderService.desc}")
	@GetMapping(path = "${api.getProviderService.url}/{id}")
	public @ResponseBody ResponseEntity<List<Service>> getProviderService(@PathVariable int id, HttpServletRequest request) throws CapAPIException {

		log.info("ServiceController :: getProviderService ");
		List<Service> services = null;
		try {
			 User user = (User) request.getAttribute("USER");
			services = serviceService.getServicesByProviderId(user.getTenantId(), user.getCustomerId(), id);
		} catch (Exception e) {
			log.error("ServiceController :: getProviderService:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: getProviderService:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(services, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.getAllServices.desc}")
	@GetMapping(path = "${api.getAllServices.url}")
	public @ResponseBody ResponseEntity<Iterable<Service>> getAllServices() throws CapAPIException {
		log.info("ServiceController :: getAllServices ");
		Iterable<Service> Services = null;
		try {
			Services = serviceService.getAllService();
		} catch (Exception e) {
			log.error("ServiceController :: getAllServices:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: getAllServices:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(Services, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.deleteService.desc}")
	@DeleteMapping(path = "${api.deleteService.url}/{id}")
	public @ResponseBody ResponseEntity<Response> deleteService(@PathVariable int id) throws CapAPIException {

		log.info("ServiceController :: deleteService ");
		Response response = null;
		try {
			response = serviceService.delteService(id);
		} catch (Exception e) {
			log.error("ServiceController :: deleteService:: error :: " + e.getMessage());
			throw new CapAPIException("ServiceController :: deleteService:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}