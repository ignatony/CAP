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

import com.cap.api.db.ProviderRepository;
import com.cap.api.db.ServiceRepo;
import com.cap.api.db.TransFileRepository;
import com.cap.api.db.WorkFlowRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Provider;
import com.cap.api.model.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Service
@Slf4j
public class ProviderService {

	@Autowired
	ProviderRepository providerRepo;
	@Autowired
	ServiceRepo serviceRepo;
	@Autowired
	WorkFlowRepo workFlowRepo;
	@Autowired
	TransFileRepository transFileRepository;

	/**
	 * @param ProviderRequest
	 * @return
	 * @throws CapAPIException
	 */
	public Response createProvider(com.cap.api.model.Provider provider) throws CapAPIException {
		log.info("ProviderService::createProvider");
		try {

			providerRepo.save(provider);
		} catch (Exception e) {
			log.error("ProviderService::createProvider: error: " + e.getMessage());
			throw new CapAPIException("ProviderService::createProvider: error: " + e.getMessage());
		}
		return getResponse("Created Provider Successfully ", "Success");
	}

	/**
	 * @param Provider
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateProvider(com.cap.api.model.Provider provider) throws CapAPIException {
		log.info("ProviderService::updateProvider");
		try {
			providerRepo.save(provider);
		} catch (Exception e) {
			log.error("ProviderService::updateProvider: error: " + e.getMessage());
			throw new CapAPIException("ProviderService::updateProvider: error: " + e.getMessage());
		}
		return getResponse("Updated Provider Successfully ", "Success");
	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public com.cap.api.model.Provider getProvider(int id) throws CapAPIException {
		com.cap.api.model.Provider provider = null;

		log.info("ProviderService::getProvider");

		Optional<com.cap.api.model.Provider> req = null;
		try {
			req = providerRepo.findById(id);
			if (req.isPresent()) {
				provider = req.get();
			}

		} catch (Exception e) {
			log.error("ProviderService::getProvider: error: " + e.getMessage());
			throw new CapAPIException("ProviderService::getProvider: error: " + e.getMessage());
		}
		return provider;
	}

	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<com.cap.api.model.Provider> getAllProvider() throws CapAPIException {
		Optional<Iterable<com.cap.api.model.Provider>> provider = null;
		log.info("ProviderService::getAllProvider");
		try {
			provider = Optional.of(providerRepo.findAll());
		} catch (Exception e) {
			log.error("ProviderService::getAllProvider: error: " + e.getMessage());
			throw new CapAPIException("ProviderService::getAllProvider: error: " + e.getMessage());
		}
		return provider.isPresent() ? provider.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	@Transactional
	public Response deleteProvider(int id) throws CapAPIException {
		log.info("ProviderService::delteProvider");
		try {

			Provider provider = getProvider(id);
			if (provider != null) {
				List<com.cap.api.model.Service> services = serviceRepo.getServicesByProviderId(provider.getTenantId(),
						provider.getCustomerId(), provider.getProviderId());
				for (com.cap.api.model.Service ser : services) {
					transFileRepository.removeByServiceId(ser.getServiceId());
					workFlowRepo.removeByServiceId(ser.getServiceId());
					serviceRepo.deleteById(ser.getServiceId());
				}
				providerRepo.deleteById(provider.getProviderId());
			}
		} catch (Exception e) {
			log.error("ProviderService::delteProvider: error: " + e.getMessage());
			throw new CapAPIException("ProviderService::delteProvider: error: " + e.getMessage());
		}
		return getResponse("Deleted Provider Successfully ", "Success");
	}

	/**
	 * @param message
	 * @param status
	 * @return
	 */
	private Response getResponse(String message, String status) {
		return new Response(null, null, LocalDateTime.now(ZoneOffset.UTC), status, message);
	}

	public Provider getProvIderByName(String name, Integer tenantId, Integer customerId) {
		List<Provider> provider = providerRepo.getProvIderByName(name, tenantId, customerId);

		return provider.isEmpty() ? null : provider.get(0);

	}

}
