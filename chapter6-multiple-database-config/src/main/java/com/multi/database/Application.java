package com.multi.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.multi.database.config")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
