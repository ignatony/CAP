/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cap.api.db.ServiceRepo;
import com.cap.api.db.TransFileRepository;
import com.cap.api.db.WorkFlowRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class ServiceService {

	@Autowired
	ServiceRepo serviceRepo;
	@Autowired
	WorkFlowRepo workFlowRepo;
	@Autowired
	TransFileRepository transFileRepository;

	/**
	 * @param ServiceRequest
	 * @return
	 * @throws CapAPIException
	 */
	public Response createService(com.cap.api.model.Service service) throws CapAPIException {
		log.info("ServiceService::createService");
		try {

			serviceRepo.save(service);
		} catch (Exception e) {
			log.error("ServiceService::createService: error: " + e.getMessage());
			throw new CapAPIException("ServiceService::createService: error: " + e.getMessage());
		}
		return getResponse("Created Service Successfully ", "Success");
	}

	/**
	 * @param Service
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateService(com.cap.api.model.Service service) throws CapAPIException {
		log.info("ServiceService::updateService");
		try {
			serviceRepo.save(service);
		} catch (Exception e) {
			log.error("ServiceService::updateService: error: " + e.getMessage());
			throw new CapAPIException("ServiceService::updateService: error: " + e.getMessage());
		}
		return getResponse("Updated Service Successfully ", "Success");
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public com.cap.api.model.Service getService(int id) throws CapAPIException {
		com.cap.api.model.Service service = null;

		log.info("ServiceService::getService");

		Optional<com.cap.api.model.Service> req = null;
		try {
			req = serviceRepo.findById(id);
			if (req.isPresent()) {
				service = req.get();
			}

		} catch (Exception e) {
			log.error("ServiceService::getService: error: " + e.getMessage());
			throw new CapAPIException("ServiceService::getService: error: " + e.getMessage());
		}
		return service;
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<com.cap.api.model.Service> getAllService() throws CapAPIException {
		Optional<Iterable<com.cap.api.model.Service>> service = null;
		log.info("ServiceService::getAllService");
		try {
			service = Optional.of(serviceRepo.findAll());
		} catch (Exception e) {
			log.error("ServiceService::getAllService: error: " + e.getMessage());
			throw new CapAPIException("ServiceService::getAllService: error: " + e.getMessage());
		}
		return service.isPresent() ? service.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@Transactional
	public Response delteService(int serviceId) throws CapAPIException {
		log.info("ServiceService::delteService");
		try {
			transFileRepository.removeByServiceId(serviceId);
			workFlowRepo.removeByServiceId(serviceId);
			serviceRepo.deleteById(serviceId);
		} catch (Exception e) {
			log.error("ServiceService::delteService: error: " + e.getMessage());
			throw new CapAPIException("ServiceService::delteService: error: " + e.getMessage());
		}
		return getResponse("Deleted Service Successfully ", "Success");
	}

	/**
	 * @param message
	 * @param status
	 * @return
	 */
	private Response getResponse(String message, String status) throws CapAPIException {
		return new Response(null, null, LocalDateTime.now(ZoneOffset.UTC), status, message);
	}

	public com.cap.api.model.Service getServiceIdByName(String serviceName, int tenantId, int customerId,
			int providerId) throws CapAPIException {
		log.info("ServiceService::getServiceIdByName");
		List<com.cap.api.model.Service> services = serviceRepo.getServiceIdByName(serviceName, tenantId, customerId,
				providerId);
		return services.isEmpty() ? null : services.get(0);
	}

	public List<com.cap.api.model.Service> getServicesByProviderId( int tenantId, int customerId,
			int providerId) throws CapAPIException {
		log.info("ServiceService::getServicesByProviderId");
		return serviceRepo.getServicesByProviderId( tenantId, customerId, providerId);

	}

}
