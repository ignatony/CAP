/**
 * 
 */
package com.cap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.cap.api.model.Tenant;
import com.cap.api.service.TenantService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class TenantController {
	@Autowired
	TenantService tenantService;
	
	/**
	 * @param tenant
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.createTenant.desc}")
	@PostMapping(path = "${api.createTenant.url}")
	public @ResponseBody ResponseEntity<Response> createTenant(@RequestBody Tenant tenant) throws CapAPIException{
		log.info("TenantController :: createTenant ");
		Response response = null;
		try {
			response = tenantService.createTenant(tenant);
		} catch (Exception e) {
			log.error("TenantController :: createTenant:: error :: "+ e.getMessage());
			throw new CapAPIException("TenantController :: createTenant:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * @param tenant
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.updateTenant.desc}")
	@PutMapping(path = "${api.updateTenant.url}")
	public @ResponseBody ResponseEntity<Response> updateTenant(@RequestBody Tenant tenant) throws CapAPIException{
		log.info("TenantController :: updateTenant ");
		Response response = null;
		try {
			response = tenantService.updateTenant(tenant);
		} catch (Exception e) {
			log.error("TenantController :: updateTenant:: error :: "+ e.getMessage());
			throw new CapAPIException("TenantController :: updateTenant:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getTenant.desc}")
	@GetMapping(path = "${api.getTenant.url}/{id}")
	public @ResponseBody ResponseEntity<Tenant> getTenant(@PathVariable int id) throws CapAPIException{
		log.info("TenantController :: getTenant ");
		Tenant tenant = null;
		try {
			tenant = tenantService.getTenant(id);
		} catch (Exception e) {
			log.error("TenantController :: getTenant:: error :: "+ e.getMessage());
			throw new CapAPIException("TenantController :: getTenant:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(tenant, HttpStatus.OK);
	}
	
	/**
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getAllTenants.desc}")
	@GetMapping(path = "${api.getAllTenants.url}")
	public @ResponseBody ResponseEntity<Iterable<Tenant>> getAllTenants() throws CapAPIException{
		log.info("TenantController :: getAllTenants ");
		Iterable<Tenant> tenants = null;
		try {
			tenants = tenantService.getAllTenant();
		} catch (Exception e) {
			log.error("TenantController :: getAllTenants:: error :: "+ e.getMessage());
			throw new CapAPIException("TenantController :: getAllTenants :: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(tenants, HttpStatus.OK);	
	}
	
	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.deleteTenant.desc}")
	@DeleteMapping(path = "${api.deleteTenant.url}/{id}")
	public @ResponseBody ResponseEntity<Response> deleteTenant(@PathVariable int id) throws CapAPIException{
		log.info("TenantController :: deleteTenant ");
		Response response = null;
		try {
			response = tenantService.delteTenant(id);
		} catch (Exception e) {
			log.error("TenantController :: deleteTenant:: error :: "+ e.getMessage());
			throw new CapAPIException("TenantController :: deleteTenant:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
}
