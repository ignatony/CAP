/**
 * 
 */
package com.cap.api.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.api.model.TransformationFile;

/**
 * @author Ignatious
 *
 */
@Repository
public interface TransFileRepository  extends JpaRepository<TransformationFile, Integer> {
	
	public static final String FIND_TRANS_FILE = "SELECT * FROM cap_db.transformationfile WHERE providerId=:providerId AND serviceId=:serviceId AND fileName=:fileName";
	public static final String FIND_TRANS_FILE_BY_ID = "SELECT * FROM cap_db.transformationfile WHERE providerId=:providerId AND serviceId=:serviceId";

	public static final String DELETE_TRANS_FILE_SER_ID = "DELETE FROM cap_db.transformationfile WHERE serviceId=:serviceId";

	
	@Query(value = FIND_TRANS_FILE, nativeQuery = true)
	public List<TransformationFile> findTransFile( @Param("providerId")Integer providerId, @Param("serviceId")Integer serviceId, @Param("fileName")String fileName );
	
	@Query(value = FIND_TRANS_FILE_BY_ID, nativeQuery = true)
	public List<TransformationFile> findTransFileById( @Param("providerId")Integer providerId, @Param("serviceId")Integer serviceId );
	
	
//	@Query("SELECT salesOrder FROM SalesOrder salesOrder WHERE salesOrder.clientId=:clientId AND salesOrder.driver_username=:driver_username AND salesOrder.date>=:fdate AND salesOrder.date<=:tdate ")
//	 @Transactional(readOnly=true)
//	 List<SalesOrder> findAllSalesByDriver(@Param("clientId")Integer clientId, @Param("driver_username")String driver_username, @Param("fdate") Date fDate, @Param("tdate") Date tdate);
//	

	
	/**
	 * @param serviceId
	 */
	@Query(value = DELETE_TRANS_FILE_SER_ID, nativeQuery = true)
	public void deleteTransFileSerId(@Param("serviceId") Integer serviceId);
	
	Long removeByServiceId(Integer serviceId);
}
