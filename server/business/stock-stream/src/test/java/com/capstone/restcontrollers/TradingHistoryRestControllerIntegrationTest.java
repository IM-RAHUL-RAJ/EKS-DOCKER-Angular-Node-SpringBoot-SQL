package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.capstone.dto.HttpErrorDto;
import com.capstone.dto.TokenDto;
import com.capstone.models.Client;
import com.capstone.models.Trade;
import com.capstone.services.v2.ClientService;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Sql(scripts = { "classpath:init.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TradingHistoryRestControllerIntegrationTest {

	private String requestUrl = "/stock_stream/trades/trade_history/";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ClientService clientService;
	
	private Client client;

	@Test
	public void getClientTradingHistoryToSucceed() throws URISyntaxException {

		client = clientService.login("john.doe@example.com", "password123");

		TokenDto requestDto = new TokenDto(Long.valueOf(1116351980));

		RequestEntity<TokenDto> requestEntity = RequestEntity.post(new URI(requestUrl + "C001"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(requestDto);

		ResponseEntity<Trade[]> response = restTemplate.exchange(requestEntity, Trade[].class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, response.getBody().length);
	}

	@Test
	public void getClientTradingHistoryToReturn403() throws URISyntaxException {
		TokenDto requestDto = new TokenDto(Long.valueOf(1116351980));

		RequestEntity<TokenDto> requestEntity = RequestEntity.post(new URI(requestUrl + "C001"))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(requestDto);

		ResponseEntity<HttpErrorDto> response = restTemplate.exchange(requestEntity, HttpErrorDto.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}
	
	@Test
	public void getClientTradingHistoryToReturn404() throws URISyntaxException {
		
		client = clientService.login("michael.white@example.com", "passw0rd");
		
		TokenDto requestDto = new TokenDto(clientService.getToken());

		RequestEntity<TokenDto> requestEntity = RequestEntity.post(new URI(requestUrl + client.getClientId()))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).body(requestDto);

		ResponseEntity<HttpErrorDto> response = restTemplate.exchange(requestEntity, HttpErrorDto.class);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
}
