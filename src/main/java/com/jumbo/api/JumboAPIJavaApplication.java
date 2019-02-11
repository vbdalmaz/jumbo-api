package com.jumbo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = JumboAPIJavaApplication.class)
public class JumboAPIJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JumboAPIJavaApplication.class, args);
	}
}
