package com.cap.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CapApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapApiApplication.class, args);
	}
//	@RestController
//	class ServiceInstanceRestController {
//
//		@Autowired
//		private DiscoveryClient discoveryClient;
//
//		@RequestMapping("/service-instances/{applicationName}")
//		public List<ServiceInstance> serviceInstancesByApplicationName(
//				@PathVariable String applicationName) {
//			return this.discoveryClient.getInstances(applicationName);
//		}
//	}
}
