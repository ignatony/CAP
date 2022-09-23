/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.api.db.TenantRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class TenantService {

	@Autowired
	TenantRepo tenantRepo;

	/**
	 * @param TenantRequest
	 * @return
	 * @throws CapAPIException
	 */
	public Response createTenant(com.cap.api.model.Tenant Tenant) throws CapAPIException {
		log.info("TenantService::createTenant");
		try {

			tenantRepo.save(Tenant);
		} catch (Exception e) {
			log.error("TenantService::createTenant: error: " + e.getMessage());
			throw new CapAPIException("TenantService::createTenant: error: " + e.getMessage());
		}
		return getResponse("Created Tenant Successfully ", "Success");
	}

	/**
	 * @param Tenant
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateTenant(com.cap.api.model.Tenant Tenant) throws CapAPIException {
		log.info("TenantService::updateTenant");
		try {
			tenantRepo.save(Tenant);
		} catch (Exception e) {
			log.error("TenantService::updateTenant: error: " + e.getMessage());
			throw new CapAPIException("TenantService::updateTenant: error: " + e.getMessage());
		}
		return getResponse("Updated Tenant Successfully ", "Success");
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public com.cap.api.model.Tenant getTenant(int id) throws CapAPIException {
		com.cap.api.model.Tenant Tenant = null;

		log.info("TenantService::getTenant");

		Optional<com.cap.api.model.Tenant> req = null;
		try {
			req = tenantRepo.findById(id);
			if (req.isPresent()) {
				Tenant = req.get();
			}

		} catch (Exception e) {
			log.error("TenantService::getTenant: error: " + e.getMessage());
			throw new CapAPIException("TenantService::getTenant: error: " + e.getMessage());
		}
		return Tenant;
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<com.cap.api.model.Tenant> getAllTenant() throws CapAPIException {
		Optional<Iterable<com.cap.api.model.Tenant>> tenant = null;
		log.info("TenantService::getAllTenant");
		try {
			tenant = Optional.of(tenantRepo.findAll());
		} catch (Exception e) {
			log.error("TenantService::getAllTenant: error: " + e.getMessage());
			throw new CapAPIException("TenantService::getAllTenant: error: " + e.getMessage());
		}
		return tenant.isPresent() ? tenant.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public Response delteTenant(int id) throws CapAPIException {
		log.info("TenantService::delteTenant");
		try {
			tenantRepo.deleteById(id);
		} catch (Exception e) {
			log.error("TenantService::delteTenant: error: " + e.getMessage());
			throw new CapAPIException("TenantService::delteTenant: error: " + e.getMessage());
		}
		return getResponse("Deleted Tenant Successfully ", "Success");
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
