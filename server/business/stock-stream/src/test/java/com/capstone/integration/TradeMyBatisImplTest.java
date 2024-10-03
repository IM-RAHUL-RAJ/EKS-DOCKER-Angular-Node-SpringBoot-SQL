package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.models.Instrument;
import com.capstone.models.Order;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class TradeMyBatisImplTest {
	
	@Autowired
	private TradeMyBatisImpl tradeDao;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String testInstrumentId = "test_instrument_1";
    private String testOrderId = "test_order_1";
    private String testTradeId = "test_trade_1";
    private String testClientId = "test_client_1";

	@Test
    public void testGetAllOrders() {
        List<Order> allOrders = tradeDao.getAllOrders();
        assertNotNull(allOrders);
        assertFalse(allOrders.isEmpty());
    }

}
