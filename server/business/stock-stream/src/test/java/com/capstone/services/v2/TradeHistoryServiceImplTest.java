package com.capstone.services.v2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.NoTradingHistoryFoundForClientException;
import com.capstone.models.Trade;


@SpringBootTest
@Transactional
class TradeServiceImplTest {

	@Autowired
	private TradeHistoryService historyService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	void getClientTradeHistoryToSucceed() {
		List<Trade> clientTradingHistory = historyService.getClientTradingHistory("C001");
		
		assertNotNull(clientTradingHistory.get(0));
		
		assertEquals(JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "ss_trades", "client_id='C001'"), clientTradingHistory.size());
		
	}
	
	@Test
	void getClientTradeHistoryToThrowIllegalArgumentException() {
		assertThrows(IllegalArgumentException.class, ()->{
			historyService.getClientTradingHistory(null);
		});
	}
	
	@Test
	void getClientTradeHistoryToThrowIllegalNoHistoryException() {
		assertThrows(NoTradingHistoryFoundForClientException.class, ()->{
			historyService.getClientTradingHistory("C0010");
		});
	}
	
	

}
