package com.trree.rattattouille;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.trree.rattattouille", "com.forrrest.common.security"})
public class RattattouilleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RattattouilleApplication.class, args);
	}

}
