package com.cap.transport.component;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;

import com.cap.transport.model.CapTransportRequest;
import com.cap.transport.model.ResponseMessage;

public interface TranportComponent {
	
	ResponseMessage process(CapTransportRequest capTransportRequest,ProducerTemplate template,Exchange exchange);

}
