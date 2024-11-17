package com.leon.event.config;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	@Autowired
	private FileStorageConfig fileStorageConfig;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		exposeDirectory("src/main/resources/static/events", registry);
		
//		registry.addResourceHandler("/images/**").addResourceLocations("file:/ressources/static/events/");
//
//		System.out.println(registry.hasMappingForPattern(fileStorageConfig.getLocation()));
	}

	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
		Path uploadDir = Paths.get(dirName);
		String uploadPath = uploadDir.toFile().getAbsolutePath();

		System.out.println(uploadDir);
		System.out.println(uploadPath);
		
		if (dirName.startsWith("../"))
			dirName = dirName.replace("../", "");
		
		System.out.println(dirName);

		registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:/" + uploadPath + "/");
	}

}
