/**
 * 
 */
package com.cap.commons.logger.repo;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cap.commons.logger.model.Logger;

import one.microstream.storage.types.EmbeddedStorage;
import one.microstream.storage.types.EmbeddedStorageManager;

/**
 * @author Ignatious
 *
 */
@Component
public class LoggerRepoImpl implements LoggerRepo {

	private final List<Logger> loggers;
	private final EmbeddedStorageManager storage;

	public LoggerRepoImpl(@Value("${microstream.store.loggerLocation}") final String location) {
		super();
		this.loggers = new ArrayList<>();
		this.storage = EmbeddedStorage.start(this.loggers, Paths.get(location));
	}

	public void storeAll() {
		this.storage.store(this.loggers);
	}

	@Override
	public void add(Logger logger) {
		this.loggers.add(logger);
		this.storeAll();

	}

	@Override
	public List<Logger> findAllLogger() {
		return this.loggers;
	}

	@Override
	public List<Logger> findLoggerByFieldType(String type, String findStr) {
		List<Logger> loggerList = null;
		switch (type) {

		case "correlationId":
			loggerList = this.loggers.stream().filter(c -> c.getCorrelationId().equals(findStr))
					.collect(Collectors.toList());
			break;
		case "layerName":
			loggerList = this.loggers.stream().filter(c -> c.getLayerName().equals(findStr))
					.collect(Collectors.toList());
			break;
		case "providerName":
			loggerList = this.loggers.stream().filter(c -> c.getProviderName().equals(findStr))
					.collect(Collectors.toList());
			break;
		case "serviceName":
			loggerList = this.loggers.stream().filter(c -> c.getServiceName().equals(findStr))
					.collect(Collectors.toList());
			break;
		case "flowName":
			loggerList = this.loggers.stream().filter(c -> c.getFlowName().equals(findStr))
					.collect(Collectors.toList());
			break;

		default:
			break;
		}

		return loggerList;
	}

	@Override
	public void deleteAllLogger() {
		this.loggers.clear();
		this.storeAll();
	}

}
