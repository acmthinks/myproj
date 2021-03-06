package com.ibm.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableAutoConfiguration
@EnableConfigServer
public class ConfigServer {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServer.class, args);
		System.out.println("\nAccess the local version of the Cloud Config Server at:\n\n    http://localhost:8001/ConfigData/dev");
		System.out.println("\n\nFind the resources for this project in Github at https://github.com/acmthinks/myproj");
		System.out.println("\n\nNow using GitHub Actions for CI workflow and package storage.");
	}
}
