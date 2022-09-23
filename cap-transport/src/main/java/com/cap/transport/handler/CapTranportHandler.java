package com.cap.transport.handler;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.support.DefaultExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cap.transport.component.TranportComponent;
import com.cap.transport.component.http.HttpComponent;
import com.cap.transport.model.CapTransportRequest;
import com.cap.transport.model.ResponseMessage;

@Component
public class CapTranportHandler {

	@Autowired
	private HttpComponent httpComponent;

	@Autowired
	private CamelContext camelContext;

	public ResponseMessage capRequestProcess(CapTransportRequest capTransportRequest) {
		return decideComponent(capTransportRequest.getTransport().getProtocol()).process(capTransportRequest,
				getProducerTemplate(), getDefaultExchange());
	}

	private TranportComponent decideComponent(String tranportType) {
		TranportComponent tranportComponent = null;
		if (tranportType.equalsIgnoreCase("http")) {
			tranportComponent = httpComponent;
		}
		return tranportComponent;
	}

	private ProducerTemplate getProducerTemplate() {
		return camelContext.createProducerTemplate();

	}

	private Exchange getDefaultExchange() {
		return new DefaultExchange(camelContext);

	}

}
