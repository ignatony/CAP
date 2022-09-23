/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.api.db.CustomerRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class CustomerService {
	@Autowired
	CustomerRepo customerRepo;
	

	/**
	 * @param CustomerRequest
	 * @return
	 * @throws CapAPIException
	 */
	public Response createCustomer(com.cap.api.model.Customer customer) throws CapAPIException {
		log.info("CustomerService::createCustomer");
		try {
			
			customerRepo.save(customer);
		} catch (Exception e) {
			log.error("CustomerService::createCustomer: error: " + e.getMessage());
			throw new CapAPIException("CustomerService::createCustomer: error: " + e.getMessage());
		}
		return getResponse("Created Customer Successfully ", "Success");
	}

	/**
	 * @param Customer
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateCustomer(com.cap.api.model.Customer customer) throws CapAPIException {
		log.info("CustomerService::updateCustomer");
		try {
			customerRepo.save(customer);
		} catch (Exception e) {
			log.error("CustomerService::updateCustomer: error: " + e.getMessage());
			throw new CapAPIException("CustomerService::updateCustomer: error: " + e.getMessage());
		}
		return getResponse("Updated Customer Successfully ", "Success");
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public com.cap.api.model.Customer getCustomer(int id) throws CapAPIException {
		com.cap.api.model.Customer customer = null;
		
		log.info("CustomerService::getCustomer");
		
		Optional<com.cap.api.model.Customer> req = null; 
		try {
			req = customerRepo.findById(id);
			if(req.isPresent()) {
				customer =  req.get();
			}
			
		} catch (Exception e) {
			log.error("CustomerService::getCustomer: error: " + e.getMessage());
			throw new CapAPIException("CustomerService::getCustomer: error: " + e.getMessage());
		}
		return customer;
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<com.cap.api.model.Customer> getAllCustomer() throws CapAPIException {
		Optional<Iterable<com.cap.api.model.Customer>> customer = null;
		log.info("CustomerService::getAllCustomer");
		try {
			customer = Optional.of(customerRepo.findAll());
		} catch (Exception e) {
			log.error("CustomerService::getAllCustomer: error: " + e.getMessage());
			throw new CapAPIException("CustomerService::getAllCustomer: error: " + e.getMessage());
		}
		return customer.isPresent() ? customer.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public Response delteCustomer(int id) throws CapAPIException {
		log.info("CustomerService::delteCustomer");
		try {
			customerRepo.deleteById(id);
		} catch (Exception e) {
			log.error("CustomerService::delteCustomer: error: " + e.getMessage());
			throw new CapAPIException("CustomerService::delteCustomer: error: " + e.getMessage());
		}
		return getResponse("Deleted Customer Successfully ", "Success");
	}
	
	


	/**
	 * @param message
	 * @param status
	 * @return
	 */
	private Response getResponse(String message, String status) {
		return new Response(null, null, LocalDateTime.now(ZoneOffset.UTC), status, message);
	}
}