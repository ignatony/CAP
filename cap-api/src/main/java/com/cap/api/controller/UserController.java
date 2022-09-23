/**
 * 
 */
package com.cap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.service.CapUserService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:9000")
public class UserController {
	@Autowired
	CapUserService capUserService;

//	@ApiOperation(value = "${api.createUser.desc}")
//	@PostMapping(path = "${api.createUser.url}")
//	public @ResponseBody ResponseEntity<Response> createUser(@RequestBody User user) throws CapAPIException {
//
//		log.info("UserController :: createUser ");
//		Response response = null;
//		try {
//			response = capUserService.createUser(user);
//		} catch (Exception e) {
//			log.error("UserController :: createUser:: error :: " + e.getMessage());
//			throw new CapAPIException("UserController :: createUser:: error :: " + e.getMessage());
//		}
//		return new ResponseEntity<>(response, HttpStatus.OK);
//	}

	/**
	 * @param user
	 * @return
	 * @throws CapAPIException
	 */
	@ApiOperation(value = "${api.updateUser.desc}")
	@PutMapping(path = "${api.updateUser.url}")
	public @ResponseBody ResponseEntity<Response> updateUser(@RequestBody User user) throws CapAPIException {

		log.info("UserController :: updateUser ");
		Response response = null;
		try {
			response = capUserService.updateUser(user);
		} catch (Exception e) {
			log.error("UserController :: updateUser:: error :: " + e.getMessage());
			throw new CapAPIException("UserController :: updateUser:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.getUser.desc}")
	@GetMapping(path = "${api.getUser.url}/{id}")
	public @ResponseBody ResponseEntity<User> getUser(@PathVariable int id) throws CapAPIException {

		log.info("UserController :: getUser ");
		User user = null;
		try {
			user = capUserService.getUser(id);
		} catch (Exception e) {
			log.error("UserController :: getUser:: error :: " + e.getMessage());
			throw new CapAPIException("UserController :: getUser:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.getAllUsers.desc}")
	@GetMapping(path = "${api.getAllUsers.url}")
	public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() throws CapAPIException {
		log.info("UserController :: getAllUsers ");
		Iterable<User> users = null;
		try {
			users = capUserService.getAllUser();
		} catch (Exception e) {
			log.error("UserController :: getAllUsers:: error :: " + e.getMessage());
			throw new CapAPIException("UserController :: getAllUsers:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@ApiOperation(value = "${api.deleteUser.desc}")
	@DeleteMapping(path = "${api.deleteUser.url}/{id}")
	public @ResponseBody ResponseEntity<Response> deleteUser(@PathVariable int id) throws CapAPIException {

		log.info("UserController :: deleteUser ");
		Response response = null;
		try {
			response = capUserService.delteUser(id);
		} catch (Exception e) {
			log.error("UserController :: deleteUser:: error :: " + e.getMessage());
			throw new CapAPIException("UserController :: deleteUser:: error :: " + e.getMessage());
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
