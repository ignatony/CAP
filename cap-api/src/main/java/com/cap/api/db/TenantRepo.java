/**
 * 
 */
package com.cap.api.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cap.api.model.Tenant;

/**
 * @author Ignatious
 *
 */
@Repository
public interface TenantRepo extends CrudRepository<Tenant, Integer> {

}
