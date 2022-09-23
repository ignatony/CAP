package com.cap.transport.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;


@Component
public class CapTransportRouteBuilder extends RouteBuilder{

	
	@Override
	public void configure() throws Exception {
		
		from("direct:invokeTransportProcess")
		 .log("Received request with payload:: ${body}")
		  .setHeader(Exchange.HTTP_METHOD, constant("POST"))
		   .log("Posting request to url:: ${exchangeProperty.apiUrl}")
		    .toD("${exchangeProperty.apiUrl}?bridgeEndpoint=true")
		     .convertBodyTo(String.class)
	          .log("Received Response from provider ::${body}");
	}

}
