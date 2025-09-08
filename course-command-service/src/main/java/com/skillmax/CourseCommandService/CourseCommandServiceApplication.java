package com.skillmax.CourseCommandService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CourseCommandServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCommandServiceApplication.class, args);
	}

}
