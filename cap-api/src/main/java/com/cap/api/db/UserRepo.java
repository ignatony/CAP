/**
 * 
 */
package com.cap.api.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cap.api.model.User;

/**
 * @author Ignatious
 *
 */
@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
	
	User findByUsername(String username);

}
