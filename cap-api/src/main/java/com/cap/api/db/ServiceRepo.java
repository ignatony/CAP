/**
 * 
 */
package com.cap.api.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Ignatious
 *
 */
@Repository
public interface ServiceRepo extends CrudRepository<com.cap.api.model.Service, Integer> {

	public static final String FIND_SERVICE_BY_NAME = "SELECT * FROM cap_db.service WHERE tenantId=:tenantId AND providerId=:providerId AND customerId=:customerId AND serviceName=:serviceName";

	public static final String FIND_SERVICE_BY_PROVIDER_ID = "SELECT * FROM cap_db.service WHERE tenantId=:tenantId AND providerId=:providerId AND customerId=:customerId";

	@Query(value = FIND_SERVICE_BY_NAME, nativeQuery = true)
	public List<com.cap.api.model.Service> getServiceIdByName(@Param("serviceName") String serviceName,
			@Param("tenantId") Integer tenantId, @Param("customerId") Integer customerId,
			@Param("providerId") Integer providerId);
	
	@Query(value = FIND_SERVICE_BY_PROVIDER_ID, nativeQuery = true)
	public List<com.cap.api.model.Service> getServicesByProviderId(
			@Param("tenantId") Integer tenantId, @Param("customerId") Integer customerId,
			@Param("providerId") Integer providerId);

}
