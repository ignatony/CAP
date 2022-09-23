package com.cap.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class CapConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(CapConfigApplication.class, args);
	}

}
