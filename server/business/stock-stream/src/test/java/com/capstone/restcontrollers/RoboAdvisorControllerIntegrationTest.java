package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.capstone.models.Price;
import com.capstone.models.Trade;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "classpath:init.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class RoboAdvisorControllerIntegrationTest {

	private String buySuggestionUrl = "/stock_stream/robo_advisor/buy/";
	private String sellSuggestionUrl = "/stock_stream/robo_advisor/buy/";
	
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	void getBuySuggestionsToSucceed() {
		ResponseEntity<Price[]> response = restTemplate.getForEntity(buySuggestionUrl+"C001", Price[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().length);
	}

}
