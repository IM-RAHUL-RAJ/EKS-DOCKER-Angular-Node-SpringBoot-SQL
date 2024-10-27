package com.capstone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.capstone.models.Order;
import com.capstone.models.Trade;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    @Test
    void testSetInstrumentId_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setInstrumentId(null));
    }

    @Test
    void testSetInstrumentId_Empty() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setInstrumentId(""));
    }

    @Test
    void testSetInstrumentId_Valid() {
        Trade trade = new Trade();
        trade.setInstrumentId("123");
        assertEquals("123", trade.getInstrumentId());
    }

    @Test
    void testSetQuantity_Zero() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setQuantity(0));
    }

    @Test
    void testSetQuantity_Negative() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setQuantity(-1));
    }

    @Test
    void testSetQuantity_Valid() {
        Trade trade = new Trade();
        trade.setQuantity(10);
        assertEquals(10, trade.getQuantity());
    }

    @Test
    void testSetExecutionPrice_Zero() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setExecutionPrice(BigDecimal.ZERO));
    }

    @Test
    void testSetExecutionPrice_Negative() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setExecutionPrice(new BigDecimal(-1.0)));
    }

    @Test
    void testSetExecutionPrice_Valid() {
        Trade trade = new Trade();
        trade.setExecutionPrice(new BigDecimal(100.0));
        assertEquals(100.0, trade.getExecutionPrice());
    }

    @Test
    void testSetDirection_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setDirection(null));
    }

    @Test
    void testSetDirection_Invalid() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setDirection("hold"));
    }

    @Test
    void testSetDirection_ValidBuy() {
        Trade trade = new Trade();
        trade.setDirection("buy");
        assertEquals("buy", trade.getDirection());
    }

    @Test
    void testSetDirection_ValidSell() {
        Trade trade = new Trade();
        trade.setDirection("sell");
        assertEquals("sell", trade.getDirection());
    }

    @Test
    void testSetClientId_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setClientId(null));
    }

    @Test
    void testSetClientId_Empty() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setClientId(""));
    }

    @Test
    void testSetClientId_Valid() {
        Trade trade = new Trade();
        trade.setClientId("client123");
        assertEquals("client123", trade.getClientId());
    }

    @Test
    void testSetOrder_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setOrder(null));
    }

    @Test
    void testSetOrder_Valid() {
        Trade trade = new Trade();
        Order order = new Order();
        order.setOrderId("order123");
        trade.setOrder(order);
        assertEquals(order, trade.getOrder());
    }

    @Test
    void testSetTradeId_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setTradeId(null));
    }

    @Test
    void testSetTradeId_Empty() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setTradeId(""));
    }

    @Test
    void testSetTradeId_Valid() {
        Trade trade = new Trade();
        trade.setTradeId("trade123");
        assertEquals("trade123", trade.getTradeId());
    }

    @Test
    void testSetCashValue_Negative() {
        Trade trade = new Trade();
        assertThrows(IllegalArgumentException.class, () -> trade.setCashValue(-1.0));
    }

    @Test
    void testSetCashValue_Valid() {
        Trade trade = new Trade();
        trade.setCashValue(1000.0);
        assertEquals(1000.0, trade.getCashValue());
    }

    @Test
    void testSetTradeDate_Null() {
        Trade trade = new Trade();
        assertThrows(NullPointerException.class, () -> trade.setCreationTime(null));
    }

    @Test
    void testSetTradeDate_Valid() {
        Trade trade = new Trade();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        trade.setCreationTime(timestamp);
        assertEquals(timestamp, trade.getCreationTime());
    }
}
