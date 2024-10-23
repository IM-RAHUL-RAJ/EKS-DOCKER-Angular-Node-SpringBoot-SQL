package com.capstone.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.exceptions.TradeException;
import com.capstone.models.Order;
import com.capstone.models.Trade;
import com.capstone.services.TradeService;
import com.capstone.exceptions.PortfolioException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
class TradeServiceTest {

	@Autowired
    private TradeService tradeService;
    private Trade trade;

    @BeforeEach
    void setUp() {
        trade = new Trade();
        trade.setInstrumentId("I001");
        trade.setQuantity(10);
        trade.setExecutionPrice(new BigDecimal(100).setScale(2));
        trade.setDirection("B");
        trade.setClientId("C001");
        trade.setTradeId("T007");
        trade.setCashValue(1000.0);
        trade.setOrderId("O001");
        trade.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void testExecuteTrade_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        assertEquals(6, tradeService.listAllTrades().size());
    }

    @Test
    void testExecuteTrade_DuplicateTradeId() {
        assertThrows(TradeException.TradeAlreadyExistsException.class, () -> {
            tradeService.executeTrade(trade);
            tradeService.executeTrade(trade);
        });
    }

    @Test
    void testCreateOrder_Valid() throws TradeException {
        Order order = tradeService.createOrder(trade);
        assertEquals(trade.getTradeId(), order.getOrderId());
    }

    @Test
    void testValidateTrade_NullTrade() {
        assertThrows(TradeException.class, () -> tradeService.validateTrade(null));
    }

    @Test
    void testCalculateTradeValue_Valid() throws TradeException {
        BigDecimal value = tradeService.calculateTradeValue(trade);
        assertEquals(1000.00, value);
    }

    @Test
    void testCancelTrade_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        tradeService.cancelOrder(trade.getTradeId());
        assertEquals(0, tradeService.listAllTrades().size());
    }

    @Test
    void testCancelTrade_TradeNotFound() {
        assertThrows(TradeException.TradeNotFoundException.class, () -> tradeService.cancelOrder("nonexistent"));
    }

    @Test
    void testUpdateTrade_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        Order updatedOrder = new Order();
        updatedOrder.setInstrumentId("123");
        updatedOrder.setQuantity(20);
        updatedOrder.setTargetPrice(new BigDecimal(200).setScale(2));
        updatedOrder.setDirection("S");
        updatedOrder.setClientId("client123");
        updatedOrder.setOrderId("trade123cti");
        updatedOrder.setCreationTime(Timestamp.valueOf(LocalDateTime.now()));
        tradeService.updateOrder(updatedOrder);
        assertEquals(20, tradeService.getTradeById("trade123").getQuantity());
    }
    @Test
    void testGetTradeById_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        Trade foundTrade = tradeService.getTradeById(trade.getTradeId());
        assertEquals(trade.getTradeId(), foundTrade.getTradeId());
    }

    @Test
    void testGetTradeById_TradeNotFound() {
        assertThrows(TradeException.TradeNotFoundException.class, () -> tradeService.getTradeById("nonexistent"));
    }

    @Test
    void testListAllTrades() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        List<Trade> trades = tradeService.listAllTrades();
        assertEquals(6, trades.size());
    }
    
    @Test
    void testGetClientTradeHistory() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        
        List<Trade> tradeHistory = tradeService.getClientTradeHistory(trade.getClientId());
        assertEquals(6, tradeHistory.size());
    }

    @Test
    void testListTradesByClient_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        List<Trade> trades = tradeService.listTradesByClient(trade.getClientId());
        assertEquals(2, trades.size());
    }

    @Test
    void testListTradesByInstrument_Valid() throws TradeException, PortfolioException {
        tradeService.executeTrade(trade);
        List<Trade> trades = tradeService.listTradesByInstrument(trade.getInstrumentId());
        assertEquals(2, trades.size());
    }
}
