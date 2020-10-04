package com.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:${jdbc.config}" })
public class MarketSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketSystemApplication.class, args);

	}

}
