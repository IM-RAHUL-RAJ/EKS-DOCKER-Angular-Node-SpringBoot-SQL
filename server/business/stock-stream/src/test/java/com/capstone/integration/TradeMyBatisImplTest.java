package com.capstone.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.enums.OrderStatus;
import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:beans.xml")
@Transactional
class TradeMyBatisImplTest {
	
	@Autowired
	private TradeMyBatisImpl tradeDao;
	    

	@Test
	void testGetAllInstruments (){
		List<Instrument> instruments = tradeDao.getAllInstruments();
		assertNotNull(instruments);
		assertEquals(5, instruments.size());
	}

	@Test
	void testGetInstrumentById(){
		Instrument instrument = tradeDao.getInstrumentById("I001");
		assertEquals("EID001", instrument.getExternalId());
	}

    @Test
    public void testGetPrice() {
        Price price = tradeDao.getPrice("I001");
        assertNotNull(price);
        assertEquals(BigDecimal.valueOf(150.00).setScale(2), price.getAskPrice()); 
    }
    
    @Test
    public void testGetTradeHistory() {
        List<Trade> trades = tradeDao.getTradeHistory();
        assertNotNull(trades);
        assertFalse(trades.isEmpty());
        
    }
    
    @Test
	public void testGetTradeById() {
		Trade trade = tradeDao.getTradeById("T002");
		assertEquals(trade.getDirection(), "S");
    }
    
    @Test
    public void testInsertTrade() {
    	Trade newTrade = new Trade("T007", "C005", "O005", "I005", 50, BigDecimal.valueOf(145.00), "B", Timestamp.valueOf("2023-01-01 20:00:00"));
        assertDoesNotThrow(() -> tradeDao.insertTrade(newTrade));
        Order retrievedTrade = tradeDao.getOrderById("O005");
        assertNotNull(retrievedTrade);
        assertEquals(50, retrievedTrade.getQuantity());
    }
    
    @Test
	void testGetAllPendingOrders (){
		List<Order> pendingOrders = tradeDao.getAllPendingOrders();
		assertNotNull(pendingOrders);
		assertEquals(1, pendingOrders.size());
	}
    
    @Test
    public void testGetAllOrders() {
        List<Order> allOrders = tradeDao.getAllOrders();
        assertNotNull(allOrders);
        assertFalse(allOrders.isEmpty());
    }
    
    @Test
    public void testGetOrderById() {
    	Order order = tradeDao.getOrderById("O002");
    	assertEquals(20,order.getQuantity());
    }
    
    @Test
    public void testInsertOrder() {
    	 
        Order newOrder = new Order("O006", "C005", "I005", 11, BigDecimal.valueOf(145.00), "B", OrderStatus.EXECUTED,Timestamp.valueOf("2023-01-01 20:00:00") );
        assertDoesNotThrow(() -> tradeDao.insertOrder(newOrder));

        Order retrievedOrder = tradeDao.getOrderById("O006");
        assertNotNull(retrievedOrder);
        assertEquals(11, retrievedOrder.getQuantity());
    }
    
    @Test
    public void testCancelOrder() {
        Order order = tradeDao.getOrderById("O005");
        assertNotNull(order);
        assertDoesNotThrow(() -> tradeDao.cancelOrder("O005"));
        assertEquals(2, OrderStatus.CANCELED.getCode());
    }
    
    @Test
	void testModifyOrder() {
		Order modifyOrder = new Order("O001", "C001", "I001", 20, new BigDecimal(245.00).setScale(2), "B", OrderStatus.CANCELED,Timestamp.valueOf("2023-01-02 21:00:00"));
		assertDoesNotThrow(() -> tradeDao.modifyOrder(modifyOrder));
		Order retrievedTrade = tradeDao.getOrderById("O001");
        assertNotNull(retrievedTrade);
        assertEquals(20, retrievedTrade.getQuantity());
	}
    
    @Test
	void testExecuteOrder() {
		Trade trade = tradeDao.executeOrder(new Order("O002", "C001", "I001", 1, new BigDecimal(1), "S", OrderStatus.EXECUTED,
				Timestamp.valueOf("2023-01-01 20:00:00")));
		assertNotNull(trade);
	}

}
