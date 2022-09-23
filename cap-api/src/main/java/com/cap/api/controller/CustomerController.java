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
import com.cap.api.model.Customer;
import com.cap.api.model.Response;
import com.cap.api.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	/**
	 * @param Customer
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.createCustomer.desc}")
	@PostMapping(path = "${api.createCustomer.url}")
	public @ResponseBody ResponseEntity<Response> createCustomer(@RequestBody Customer customer) throws CapAPIException{
		log.info("CustomerController :: createCustomer ");
		Response response = null;
		try {
			response = customerService.createCustomer(customer);
		} catch (Exception e) {
			log.error("CustomerController :: createCustomer:: error :: "+ e.getMessage());
			throw new CapAPIException("CustomerController :: createCustomer:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
	
	/**
	 * @param Customer
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.updateCustomer.desc}")
	@PutMapping(path = "${api.updateCustomer.url}")
	public @ResponseBody ResponseEntity<Response> updateCustomer(@RequestBody Customer customer) throws CapAPIException{
		log.info("CustomerController :: updateCustomer ");
		Response response = null;
		try {
			response = customerService.updateCustomer(customer);
		} catch (Exception e) {
			log.error("CustomerController :: updateCustomer:: error :: "+ e.getMessage());
			throw new CapAPIException("CustomerController :: updateCustomer:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getCustomer.desc}")
	@GetMapping(path = "${api.getCustomer.url}/{id}")
	public @ResponseBody ResponseEntity<Customer> getCustomer(@PathVariable int id) throws CapAPIException{
		log.info("CustomerController :: getCustomer ");
		Customer Customer = null;
		try {
			Customer = customerService.getCustomer(id);
		} catch (Exception e) {
			log.error("CustomerController :: getCustomer:: error :: "+ e.getMessage());
			throw new CapAPIException("CustomerController :: getCustomer:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(Customer, HttpStatus.OK);
	}
	
	/**
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.getAllCustomers.desc}")
	@GetMapping(path = "${api.getAllCustomers.url}")
	public @ResponseBody ResponseEntity<Iterable<Customer>> getAllCustomers() throws CapAPIException{
		log.info("CustomerController :: getAllCustomers ");
		Iterable<Customer> Customers = null;
		try {
			Customers = customerService.getAllCustomer();
		} catch (Exception e) {
			log.error("CustomerController :: getAllCustomers:: error :: "+ e.getMessage());
			throw new CapAPIException("CustomerController :: getAllCustomers :: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(Customers, HttpStatus.OK);	
	}
	
	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.deleteCustomer.desc}")
	@DeleteMapping(path = "${api.deleteCustomer.url}/{id}")
	public @ResponseBody ResponseEntity<Response> deleteCustomer(@PathVariable int id) throws CapAPIException{
		log.info("CustomerController :: deleteCustomer ");
		Response response = null;
		try {
			response = customerService.delteCustomer(id);
		} catch (Exception e) {
			log.error("CustomerController :: deleteCustomer:: error :: "+ e.getMessage());
			throw new CapAPIException("CustomerController :: deleteCustomer:: error :: "+ e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);	
	}
}
