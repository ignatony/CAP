/**
 * 
 */
package com.cap.api.db;

import org.springframework.data.repository.CrudRepository;

import com.cap.api.model.Customer;


/**
 * @author Ignatious
 *
 */
public interface CustomerRepo extends CrudRepository<Customer, Integer> {

}
