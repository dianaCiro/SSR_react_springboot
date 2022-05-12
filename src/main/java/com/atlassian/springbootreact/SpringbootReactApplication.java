package com.atlassian.springbootreact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.atlassian.springbootreact")
public class SpringbootReactApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootReactApplication.class, args);
	}

}
