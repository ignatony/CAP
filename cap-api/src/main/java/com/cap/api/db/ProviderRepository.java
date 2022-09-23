/**
 * 
 */
package com.cap.api.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.api.model.Provider;

/**
 * @author Ignatious
 *
 */
@Repository
public interface ProviderRepository extends CrudRepository<Provider, Integer> {

	public static final String FIND_SERVICE_BY_NAME = "SELECT * FROM cap_db.provider WHERE tenantId=:tenantId AND customerId=:customerId AND name=:name";

	@Query(value = FIND_SERVICE_BY_NAME, nativeQuery = true)
	public List<Provider> getProvIderByName(@Param("name") String name,
			@Param("tenantId") Integer tenantId, @Param("customerId") Integer customerId);

}
