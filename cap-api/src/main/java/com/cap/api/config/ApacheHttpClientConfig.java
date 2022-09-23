package com.cap.api.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ignatious
 *
 */
@Slf4j
@Configuration
@EnableScheduling
public class ApacheHttpClientConfig {
	@Value("${connection.pool.MAX_ROUTE_CONNECTIONS}")
	private int MAX_ROUTE_CONNECTIONS;
	@Value("${connection.pool.MAX_TOTAL_CONNECTIONS}")
	private int MAX_TOTAL_CONNECTIONS;
	@Value("${connection.pool.MAX_LOCALHOST_CONNECTIONS}")
	private int MAX_LOCALHOST_CONNECTIONS;

	// Keep alive
	@Value("${connection.alive.DEFAULT_KEEP_ALIVE_TIME}")
	private int DEFAULT_KEEP_ALIVE_TIME;

	// Timeouts
	@Value("${connection.timeout.CONNECTION_TIMEOUT}")
	private int CONNECTION_TIMEOUT;
	@Value("${connection.timeout.REQUEST_TIMEOUT}")
	private int REQUEST_TIMEOUT;
	@Value("${connection.timeout.SOCKET_TIMEOUT}")
	private int SOCKET_TIMEOUT;

	// Idle connection monitor
	@Value("${connection.idel.IDLE_CONNECTION_WAIT_TIME}")
	private int IDLE_CONNECTION_WAIT_TIME;

	@Autowired
	Environment environment;

	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();

		// set total amount of connections across all HTTP routes
		poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);

		log.info("MAX_TOTAL_CONNECTIONS -------->:" + MAX_TOTAL_CONNECTIONS);

		// set maximum amount of connections for each http route in pool
		poolingConnectionManager.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

		log.info("MAX_ROUTE_CONNECTIONS  -------->:" + MAX_ROUTE_CONNECTIONS);

		// increase the amounts of connections if host is localhost
		// HttpHost localhost = new HttpHost("http://localhost", 8080);

		HttpHost localhost = null;
		String port = environment.getProperty("server.port");
		try {
			localhost = new HttpHost(InetAddress.getLocalHost().getHostName(), Integer.valueOf(port));
		} catch (UnknownHostException e) {
			log.info("UnknownHostException : " + e.getMessage());
		}

		poolingConnectionManager.setMaxPerRoute(new HttpRoute(localhost), MAX_LOCALHOST_CONNECTIONS);

		// log.info("MAX_LOCALHOST_CONNECTIONS -------->:"+MAX_LOCALHOST_CONNECTIONS);

		return poolingConnectionManager;
	}

	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return (httpResponse, httpContext) -> {
			HeaderIterator headerIterator = httpResponse.headerIterator(HTTP.CONN_KEEP_ALIVE);
			HeaderElementIterator elementIterator = new BasicHeaderElementIterator(headerIterator);

			while (elementIterator.hasNext()) {
				HeaderElement element = elementIterator.nextElement();
				String param = element.getName();
				String value = element.getValue();
				if (value != null && param.equalsIgnoreCase("timeout")) {
					return Long.parseLong(value) * 1000; // convert to ms
				}
			}

			return DEFAULT_KEEP_ALIVE_TIME;
		};
	}

	@Bean
	public Runnable idleConnectionMonitor(PoolingHttpClientConnectionManager pool) {
		return new Runnable() {
			@Override
			@Scheduled(fixedDelay = 20000)
			public void run() {
				// only if connection pool is initialised
				if (pool != null) {
					pool.closeExpiredConnections();
					pool.closeIdleConnections(IDLE_CONNECTION_WAIT_TIME, TimeUnit.MILLISECONDS);
					// log.info("IDLE_CONNECTION_WAIT_TIME -------->:"+IDLE_CONNECTION_WAIT_TIME);
					// log.info("Idle connection monitor: Closing expired and idle connections");
				}
			}
		};
	}

	@Bean
	public TaskScheduler taskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("idleMonitor");
		scheduler.setPoolSize(5);
		return scheduler;
	}

	@Bean
	public CloseableHttpClient httpClient() {
		log.info("CONNECTION_TIMEOUT  -------->:" + CONNECTION_TIMEOUT);
		log.info("REQUEST_TIMEOUT  -------->:" + REQUEST_TIMEOUT);
		log.info("SOCKET_TIMEOUT  -------->:" + SOCKET_TIMEOUT);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT)
				.setConnectionRequestTimeout(REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

		return HttpClients.custom().setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingConnectionManager()).setKeepAliveStrategy(connectionKeepAliveStrategy())
				.build();
	}

}