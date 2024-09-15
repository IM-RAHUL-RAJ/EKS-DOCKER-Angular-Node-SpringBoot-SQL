package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fidelity.capstone.exceptions.TradeException;
import com.fidelity.capstone.stock_stream.Order;
import com.fidelity.capstone.stock_stream.Trade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TestTradeService {
    @Test
    public void testExecuteTrade() {
        TradeService tradeService = new TradeService();
        Trade trade = new Trade();
        try {
            trade.setInstrumentId("123");
            trade.setQuantity(100);
            trade.setExecutionPrice(50.0);
            trade.setDirection("buy");
            trade.setClientId("client1");
            trade.setTradeId("trade1");
            trade.setCashValue(5000.0);
            trade.setTradeDate("2024-09-15");

            assertDoesNotThrow(() -> tradeService.executeTrade(trade));
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateOrder() {
        TradeService tradeService = new TradeService();
        Trade trade = new Trade();
        try {
            trade.setInstrumentId("123");
            trade.setQuantity(100);
            trade.setExecutionPrice(50.0);
            trade.setDirection("buy");
            trade.setClientId("client1");
            trade.setTradeId("trade1");
            trade.setCashValue(5000.0);
            trade.setTradeDate("2024-09-15");

            Order order = tradeService.createOrder(trade);
            assertNotNull(order);
            assertEquals("123", order.getInstrumentId());
            assertEquals(100, order.getQuantity());
            assertEquals(50.0, order.getTargetPrice());
            assertEquals("buy", order.getDirection());
            assertEquals("client1", order.getClientId());
            assertEquals("trade1", order.getOrderId());
            assertEquals("2024-09-15", order.getOrderDate());
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateOrderWithInvalidTrade() {
        TradeService tradeService = new TradeService();
        Trade trade = new Trade();
        try {
            trade.setInstrumentId("");
            trade.setQuantity(-100);
            trade.setExecutionPrice(-50.0);
            trade.setDirection("invalid");
            trade.setClientId("");
            trade.setTradeId("");
            trade.setCashValue(-5000.0);
            trade.setTradeDate("");

            Exception exception = assertThrows(TradeException.TradeValidationException.class, () -> {
                tradeService.createOrder(trade);
            });

            String expectedMessage = "Invalid trade data";
            String actualMessage = exception.getMessage();

            assertTrue(actualMessage.contains(expectedMessage));
        } catch (Exception e) {
        	assertNotNull(e, e.getMessage());
        }
    }
}
