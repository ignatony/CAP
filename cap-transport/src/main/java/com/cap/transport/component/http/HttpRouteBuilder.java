package com.cap.transport.component.http;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HttpRouteBuilder extends RouteBuilder{
	
	@Override
	public void configure() throws Exception {
		from("direct:startHttpProcess")
		 .log("Received request with payload:: ${body}")
		  .setHeader(Exchange.HTTP_METHOD, constant("POST"))
		   .to("direct:invokeTransportProcess")
	         .log("Received Response from provider ::${body}");
		
	}

}
