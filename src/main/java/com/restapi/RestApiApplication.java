package com.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class RestApiApplication {

	@Bean
	public RestTemplate GetRestTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args){
		SpringApplication.run(RestApiApplication.class, args);
	}

}
