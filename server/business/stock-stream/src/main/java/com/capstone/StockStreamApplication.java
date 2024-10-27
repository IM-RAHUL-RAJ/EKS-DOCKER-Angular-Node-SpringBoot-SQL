package com.capstone;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.capstone.*"}) 
//@MapperScan(value = { "com.capstone.integration.mapper"})
public class StockStreamApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockStreamApplication.class, args);
	}

	/**
	 * This method creates a Logger that can be autowired in other
	 * classes:{@code @Autowired private Logger logger; }
	 */
	@Bean
	@Scope("prototype")
	Logger createLogger(InjectionPoint ip) {
		Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
		return LoggerFactory.getLogger(classThatWantsALogger);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
