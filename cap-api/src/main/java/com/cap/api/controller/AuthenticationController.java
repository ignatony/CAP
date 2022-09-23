package com.cap.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cap.api.config.security.JwtTokenUtil;
import com.cap.api.model.User;
import com.cap.api.model.jwt.JwtRequest;
import com.cap.api.model.jwt.JwtResponse;
import com.cap.api.service.CapUserService;

import io.swagger.annotations.ApiOperation;

@RequestMapping(path = "${api.base.url}")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CapUserService capUserService;

	/**
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "UserAuthenticate")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		Authentication authentication = authenticate(authenticationRequest.getUsername(),
				authenticationRequest.getPassword());

	    UserDetails userDetails = capUserService.loadUserByUsername(authenticationRequest.getUsername());
		User user = capUserService.getUserByUserName(userDetails.getUsername());

		//final String token = jwtTokenUtil.generateToken(userDetails);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	    String jwt = jwtTokenUtil.generateToken(userDetails);
//	    List<String> roles = new ArrayList<>();
//	    roles.add("ROLE_ADMIN");
//	    roles.add(" ROLE_MODERATOR");
	    
	    List<String> roles = user.getRoles().stream()
	            .map(item -> item.getName().name())
	            .collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
				user.getUserId(), 
				user.getUsername(), 
				user.getName(), 
				roles));
		
		
		
	    
	       
//	    List<String> roles = userDetails.getAuthorities().stream()
//	        .map(item -> item.getAuthority())
//	        .collect(Collectors.toList());

	   // return ResponseEntity.ok();
	}

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "UserRegister")
	@RequestMapping(value = "/userRegister", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		capUserService.save(user);
		return ResponseEntity.ok("{\"message\":\"Registration Success\"}");
	}

	/**
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private Authentication authenticate(String username, String password) throws Exception {
		Authentication authentication = null;
		try {
			authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
		return authentication;
	}
}