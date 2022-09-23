package com.cap.transport.config;

import org.apache.camel.CamelContext;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CapCamelConfiguration {

	@Autowired
	private ApplicationContext applicationContext;

	private RouteBuilder[] routeBuilder;

	@Bean
	public CamelContext camelContext() {
		CamelContext camelContext = new SpringCamelContext(applicationContext);
		if (routeBuilder != null) {
			for (RouteBuilder route : routeBuilder) {
				try {
					camelContext.addRoutes(route);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return camelContext;
	}

}
