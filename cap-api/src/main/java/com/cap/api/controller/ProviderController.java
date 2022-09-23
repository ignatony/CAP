package com.cap.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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

import com.cap.api.db.ProviderRepository;
import com.cap.api.exception.CapAPIException;
import com.cap.api.model.Provider;
import com.cap.api.model.Response;
import com.cap.api.model.User;
import com.cap.api.service.ProviderService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "http://localhost:9000", maxAge = 3600)
@RequestMapping(path = "${api.base.url}")
@RestController
@Slf4j
public class ProviderController {
	
		
	
	
	@Autowired
	 private ProviderRepository providerRepository;
	@Autowired
	ProviderService providerService;

	  
	  @ApiOperation(value = "${api.createProvider.desc}")
		@PostMapping(path = "${api.createProvider.url}")
	  public @ResponseBody String createProvider (@RequestBody Provider provider, HttpServletRequest request
	      ) {
		  User user = (User) request.getAttribute("USER");
		  provider.setCustomerId(user.getCustomerId());
		  provider.setTenantId(user.getTenantId());
	    providerRepository.save(provider);
	    return "Saved";
	  }

	
	  
		@ApiOperation(value = "${api.getProvider.desc}")
		@GetMapping(path = "${api.getProvider.url}/{id}")
		public @ResponseBody Optional<Provider> getProvider(@PathVariable int id) {
			
			return providerRepository.findById(id);	
		} 
		
		@ApiOperation(value = "${api.getAllProviders.desc}")
		@GetMapping(path = "${api.getAllProviders.url}")
		public @ResponseBody ResponseEntity<Iterable<Provider>> getAllProviders() {
			
			Iterable<Provider> providers= providerRepository.findAll();
			
			return new ResponseEntity<>(providers, HttpStatus.OK);
				
		}
	  
	  @DeleteMapping(path="${api.deleteProvider.url}/{id}")
	  @ApiOperation(value = "${api.deleteProvider.desc}")
	  public @ResponseBody  ResponseEntity<Response> deleteProvider(@PathVariable int id) throws CapAPIException {
	    // This returns a JSON or XML with the users
		  Response response =  providerService.deleteProvider(id);
		  return new ResponseEntity<>(response, HttpStatus.OK);
	  }
	  

	  @ApiOperation(value = "${api.updateProvider.desc}")
		@PutMapping(path = "${api.updateProvider.url}")
	  public @ResponseBody String updateProvider(@RequestBody Provider provider) {
	    // This returns a JSON or XML with the users
	     providerRepository.save(provider);
	    return "Updated";
	  }
	  
	  
	  
	  

}
