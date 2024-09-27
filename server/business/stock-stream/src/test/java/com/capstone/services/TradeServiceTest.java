package com.capstone.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.capstone.exceptions.TradeException;
import com.capstone.models.Order;
import com.capstone.models.Trade;
import com.capstone.services.PortfolioException;
import com.capstone.services.TradeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TradeServiceTest {
//
//    private TradeService tradeService;
//    private Trade trade;
//
//    @BeforeEach
//    void setUp() {
//        tradeService = new TradeService();
//        trade = new Trade();
//        trade.setInstrumentId("123");
//        trade.setQuantity(10);
//        trade.setExecutionPrice(100.0);
//        trade.setDirection("buy");
//        trade.setClientId("client123");
//        trade.setTradeId("trade123");
//        trade.setCashValue(1000.0);
//        trade.setTradeDate("2024-09-15");
//    }
//
//    @Test
//    void testExecuteTrade_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        assertEquals(1, tradeService.listAllTrades().size());
//    }
//
//    @Test
//    void testExecuteTrade_DuplicateTradeId() {
//        assertThrows(TradeException.TradeAlreadyExistsException.class, () -> {
//            tradeService.executeTrade(trade);
//            tradeService.executeTrade(trade);
//        });
//    }
//
//    @Test
//    void testCreateOrder_Valid() throws TradeException {
//        Order order = tradeService.createOrder(trade);
//        assertEquals(trade.getTradeId(), order.getOrderId());
//    }
//
//    @Test
//    void testValidateTrade_NullTrade() {
//        assertThrows(TradeException.class, () -> tradeService.validateTrade(null));
//    }
//
//    @Test
//    void testCalculateTradeValue_Valid() throws TradeException {
//        double value = tradeService.calculateTradeValue(trade);
//        assertEquals(1000.0, value);
//    }
//
//    @Test
//    void testCancelTrade_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        tradeService.cancelTrade(trade.getTradeId());
//        assertEquals(0, tradeService.listAllTrades().size());
//    }
//
//    @Test
//    void testCancelTrade_TradeNotFound() {
//        assertThrows(TradeException.TradeNotFoundException.class, () -> tradeService.cancelTrade("nonexistent"));
//    }
//
//    @Test
//    void testUpdateTrade_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        Trade updatedTrade = new Trade();
//        updatedTrade.setInstrumentId("123");
//        updatedTrade.setQuantity(20);
//        updatedTrade.setExecutionPrice(200.0);
//        updatedTrade.setDirection("sell");
//        updatedTrade.setClientId("client123");
//        updatedTrade.setTradeId("trade123");
//        updatedTrade.setCashValue(4000.0);
//        updatedTrade.setTradeDate("2024-09-16");
//        tradeService.updateTrade(updatedTrade);
//        assertEquals(20, tradeService.getTradeById("trade123").getQuantity());
//    }
//
//    @Test
//    void testUpdateTrade_TradeNotFound() {
//        Trade updatedTrade = new Trade();
//        updatedTrade.setTradeId("nonexistent");
//        assertThrows(TradeException.TradeNotFoundException.class, () -> tradeService.updateTrade(updatedTrade));
//    }
//
//    @Test
//    void testGetTradeById_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        Trade foundTrade = tradeService.getTradeById(trade.getTradeId());
//        assertEquals(trade.getTradeId(), foundTrade.getTradeId());
//    }
//
//    @Test
//    void testGetTradeById_TradeNotFound() {
//        assertThrows(TradeException.TradeNotFoundException.class, () -> tradeService.getTradeById("nonexistent"));
//    }
//
//    @Test
//    void testListAllTrades() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        List<Trade> trades = tradeService.listAllTrades();
//        assertEquals(1, trades.size());
//    }
//    
//    @Test
//    void testGetClientTradeHistory() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        
//        List<Trade> tradeHistory = tradeService.getClientTradeHistory(trade.getClientId());
//        assertEquals(1, tradeHistory.size());
//    }
//
//    @Test
//    void testListTradesByClient_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        List<Trade> trades = tradeService.listTradesByClient(trade.getClientId());
//        assertEquals(1, trades.size());
//    }
//
//    @Test
//    void testListTradesByInstrument_Valid() throws TradeException, PortfolioException {
//        tradeService.executeTrade(trade);
//        List<Trade> trades = tradeService.listTradesByInstrument(trade.getInstrumentId());
//        assertEquals(1, trades.size());
//    }
}
