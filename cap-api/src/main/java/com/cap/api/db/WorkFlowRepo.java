/**
 * 
 */
package com.cap.api.db;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.api.model.WorkFlow;

/**
 * @author Ignatious
 *
 */
@Repository
public interface WorkFlowRepo extends CrudRepository<WorkFlow, Integer> {

	public static final String FIND_WORKFLOW_CUST_PROV_SER_ID = "SELECT * FROM cap_db.workFlow WHERE customerId=:customerId AND providerId=:providerId AND serviceId=:serviceId";

	public static final String FIND_WORKFLOW_SER_ID = "SELECT * FROM cap_db.workFlow WHERE serviceId=:serviceId";
	
	public static final String DELETE_WORKFLOW_SER_ID = "DELETE FROM cap_db.workFlow WHERE  serviceId=:serviceId";
	
	/**
	 * @param customerId
	 * @param providerId
	 * @param serviceId
	 * @return
	 */
	@Query(value = FIND_WORKFLOW_CUST_PROV_SER_ID, nativeQuery = true)
	public List<WorkFlow> findWorkFlowByCustProvSerId(@Param("customerId") Integer customerId, @Param("providerId") Integer providerId,
			@Param("serviceId") Integer serviceId);
	

	/**
	 * @param serviceId
	 * @return
	 */
	@Query(value = FIND_WORKFLOW_SER_ID, nativeQuery = true)
	public List<WorkFlow> findWorkFlowBySerId(@Param("serviceId") Integer serviceId);
	
	
	Long removeByServiceId(Integer serviceId);
}
