package com.cap.transport.component.http;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.springframework.stereotype.Component;

import com.cap.transport.component.TranportComponent;
import com.cap.transport.exception.TransportException;
import com.cap.transport.model.CapTransportRequest;
import com.cap.transport.model.Header;
import com.cap.transport.model.ResponseMessage;
import com.cap.transport.model.Transport;

@Component
public class HttpComponent implements TranportComponent {

	@Override
	public ResponseMessage process(CapTransportRequest capTransportRequest, ProducerTemplate producerTemplate,
			Exchange exchange) {

		setTransportHeaders(capTransportRequest, exchange);
		exchange.getIn().setBody(capTransportRequest.getPayload());
		exchange.setProperty("apiUrl", prepareUrl(capTransportRequest.getTransport()));
		Exchange reExchange = producerTemplate.send("direct:startHttpProcess", exchange);
		return prepareOutResponse(reExchange,capTransportRequest.getCorrelationId());
	}

	private String prepareUrl(Transport transport) {
		return transport.getUrl();
	}

	private void setTransportHeaders(CapTransportRequest request, Exchange exchange) {
		Map<String, Object> customHeaders = request.getCustomHeader();

		List<Header> httpHeaders = request.getTransport().getHeader();

		for (Map.Entry<String, Object> header : customHeaders.entrySet()) {
			exchange.getIn().setHeader(header.getKey(), header.getValue());
		}

		for (Header header : httpHeaders) {
			exchange.getIn().setHeader(header.getHeaderKey(), header.getHeaderValue());
		}
	}

	private ResponseMessage prepareOutResponse(Exchange reExchange, String correlationId) throws TransportException {
		ResponseMessage resBody = new ResponseMessage();
		resBody.setCorrelationId(correlationId);

		if (reExchange.getIn().getHeader("CamelHttpResponseCode") != null
				&& reExchange.getIn().getHeader("CamelHttpResponseCode").equals(200)) {
			resBody.setMessage("Transaction Success");
			resBody.setStatus("SUCCESS");
			resBody.setPayload(reExchange.getIn().getBody(String.class));
		} else {
			throw new TransportException(getErrorMessage(reExchange));
		}

		return resBody;
	}

	private String getErrorMessage(Exchange reExchange) {
		Exception exception = reExchange.getException();
		String errorMessage = null;

		if (exception instanceof HttpOperationFailedException) {
			HttpOperationFailedException ex = (HttpOperationFailedException) exception;
			errorMessage = ex.getMessage();
		}
		return errorMessage;
	}
}
