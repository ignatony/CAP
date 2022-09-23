/**
 * 
 */
package com.cap.api.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cap.api.db.UserRepo;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
@Service
public class CapUserService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}
	
	
	/**
	 * @param UserRequest
	 * @return
	 * @throws CapAPIException
	 */
	public User save(com.cap.api.model.User user) throws CapAPIException {
		log.info("CapUserService::createUser");
		try {
			user.setPassword(bcryptEncoder.encode(user.getPassword()));
			userRepo.save(user);
		} catch (Exception e) {
			log.error("CapUserService::createUser: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::createUser: error: " + e.getMessage());
		}
		return user;
	}

	/**
	 * @param User
	 * @return
	 * @throws CapAPIException
	 */
	public Response updateUser(com.cap.api.model.User User) throws CapAPIException {
		log.info("CapUserService::updateUser");
		try {
			userRepo.save(User);
		} catch (Exception e) {
			log.error("CapUserService::updateUser: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::updateUser: error: " + e.getMessage());
		}
		return getResponse("Updated User Successfully ", "Success");
	}

	/**
	 * @param username String
	 * @return
	 * @throws CapAPIException
	 */
	public com.cap.api.model.User getUserByUserName(String username) throws CapAPIException {
	

		log.info("CapUserService::getUser");

		com.cap.api.model.User user = null;
		try {
			 user = userRepo.findByUsername(username);
			

		} catch (Exception e) {
			log.error("CapUserService::getUserByUserName: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::getUserByUserName: error: " + e.getMessage());
		}
		return user;
	}
	
	public com.cap.api.model.User getUser(int id) throws CapAPIException {
		com.cap.api.model.User User = null;

		log.info("CapUserService::getUser");

		Optional<com.cap.api.model.User> req = null;
		try {
			req = userRepo.findById(id);
			if (req.isPresent()) {
				User = req.get();
			}

		} catch (Exception e) {
			log.error("CapUserService::getUser: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::getUser: error: " + e.getMessage());
		}
		return User;
	}


	/**
	 * @return
	 * @throws CapAPIException
	 */
	public Iterable<com.cap.api.model.User> getAllUser() throws CapAPIException {
		Optional<Iterable<com.cap.api.model.User>> User = null;
		log.info("CapUserService::getAllUser");
		try {
			User = Optional.of(userRepo.findAll());
		} catch (Exception e) {
			log.error("CapUserService::getAllUser: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::getAllUser: error: " + e.getMessage());
		}
		return User.isPresent() ? User.get() : null;

	}

	/**
	 * @param id
	 * @return
	 * @throws CapAPIException
	 */
	public Response delteUser(int id) throws CapAPIException {
		log.info("CapUserService::delteUser");
		try {
			userRepo.deleteById(id);
		} catch (Exception e) {
			log.error("CapUserService::delteUser: error: " + e.getMessage());
			throw new CapAPIException("CapUserService::delteUser: error: " + e.getMessage());
		}
		return getResponse("Deleted User Successfully ", "Success");
	}

	/**
	 * @param message
	 * @param status
	 * @return
	 */
	private Response getResponse(String message, String status) {
		return new Response(null, null, LocalDateTime.now(ZoneOffset.UTC), status, message);
	}

}



