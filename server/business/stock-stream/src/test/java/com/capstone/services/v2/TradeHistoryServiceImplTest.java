package com.capstone.services.v2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.NoTradingHistoryFoundForClientException;
import com.capstone.exceptions.UserNotLoggedInToPerformAction;
import com.capstone.models.Client;
import com.capstone.models.Trade;



@SpringBootTest
@Transactional
class TradeHistoryServiceImplTest {

	private long token;
	
	private Client client;
	
	@Autowired
	private TradeHistoryService historyService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Logger logger;
	
	@BeforeEach
	void setup() {
		client = clientService.login("john.doe@example.com", "password123");
		token = clientService.getToken();
		
	}
	
	@Test
	void getClientTradeHistoryToSucceed() {
		System.out.println(clientService.getToken()+","+token);
		List<Trade> clientTradingHistory = historyService.getClientTradingHistory(client.getClientId(),token);
		
		
		
		assertNotNull(clientTradingHistory.get(0));
		
		assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "ss_trades", "client_id='"+client.getClientId()+"'"), clientTradingHistory.size());
		
	}
	
	@Test
	void getClientTradeHistoryToThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, ()->{
			historyService.getClientTradingHistory(null,null);
		});
	}
	
	@Test
	void getClientTradeHistoryToThrowIllegalNoHistoryException() {
		assertThrows(NoTradingHistoryFoundForClientException.class, ()->{
			historyService.getClientTradingHistory("C0010",token);
		});
	}
	
	@Test
	void getClientTradeHistoryToThrowUserNotLoggedInToPerformAction() {
		assertThrows(UserNotLoggedInToPerformAction.class, ()->{
			historyService.getClientTradingHistory(client.getClientId(),Long.valueOf(1234));
		});
	}
	
	

}
