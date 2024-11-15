package com.leon.event.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties("storage")
@Configuration
public class FileStorageConfig {

	private String location = "C:/Users/leo/Documents/workspace-spring-tool-suite-4-4.25.0.RELEASE/eventbooker/src/main/static";

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
}
