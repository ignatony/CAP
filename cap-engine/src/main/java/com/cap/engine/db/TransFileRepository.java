/**
 * 
 */
package com.cap.engine.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cap.engine.model.TransformationFile;


/**
 * @author Ignatious
 *
 */
@Repository
public interface TransFileRepository  extends JpaRepository<TransformationFile, Integer> {
	
public static final String FIND_TRANS_FILE = "SELECT * FROM cap_db.transformationfile WHERE customerId=:customerId AND providerId=:providerId AND serviceId=:serviceId AND fileName=:fileName";
	
	@Query(value = FIND_TRANS_FILE, nativeQuery = true)
	public List<TransformationFile> findTransFile(@Param("customerId")Integer customerId, @Param("providerId")Integer providerId, @Param("serviceId")Integer serviceId, @Param("fileName")String fileName );
	

}
