package com.capstone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.capstone.models.Order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testSetInstrumentId_Null() {
        Order order = new Order();
        assertThrows(NullPointerException.class, () -> order.setInstrumentId(null));
    }

    @Test
    void testSetInstrumentId_Empty() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setInstrumentId(""));
    }

    @Test
    void testSetInstrumentId_Valid() {
        Order order = new Order();
        order.setInstrumentId("123");
        assertEquals("123", order.getInstrumentId());
    }

    @Test
    void testSetQuantity_Zero() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setQuantity(0));
    }

    @Test
    void testSetQuantity_Negative() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setQuantity(-1));
    }

    @Test
    void testSetQuantity_Valid() {
        Order order = new Order();
        order.setQuantity(10);
        assertEquals(10, order.getQuantity());
    }

    @Test
    void testSetTargetPrice_Zero() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setTargetPrice(BigDecimal.ZERO));
    }

    @Test
    void testSetTargetPrice_Negative() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setTargetPrice(new BigDecimal(-1.0)));
    }

    @Test
    void testSetTargetPrice_Valid() {
        Order order = new Order();
        order.setTargetPrice(new BigDecimal(100.0));
        assertEquals(100.0, order.getTargetPrice());
    }

    @Test
    void testSetDirection_Null() {
        Order order = new Order();
        assertThrows(NullPointerException.class, () -> order.setDirection(null));
    }

    @Test
    void testSetDirection_Invalid() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setDirection("hold"));
    }

    @Test
    void testSetDirection_ValidBuy() {
        Order order = new Order();
        order.setDirection("buy");
        assertEquals("buy", order.getDirection());
    }

    @Test
    void testSetDirection_ValidSell() {
        Order order = new Order();
        order.setDirection("sell");
        assertEquals("sell", order.getDirection());
    }

    @Test
    void testSetClientId_Null() {
        Order order = new Order();
        assertThrows(NullPointerException.class, () -> order.setClientId(null));
    }

    @Test
    void testSetClientId_Empty() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setClientId(""));
    }

    @Test
    void testSetClientId_Valid() {
        Order order = new Order();
        order.setClientId("client123");
        assertEquals("client123", order.getClientId());
    }

    @Test
    void testSetOrderId_Null() {
        Order order = new Order();
        assertThrows(NullPointerException.class, () -> order.setOrderId(null));
    }

    @Test
    void testSetOrderId_Empty() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setOrderId(""));
    }

    @Test
    void testSetOrderId_Valid() {
        Order order = new Order();
        order.setOrderId("order123");
        assertEquals("order123", order.getOrderId());
    }

    @Test
    void testSetOrderDate_Null() {
        Order order = new Order();
        assertThrows(NullPointerException.class, () -> order.setCreationTime(null));
    }

    @Test
    void testSetOrderDate_Valid() {
        Order order = new Order();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        order.setCreationTime(timestamp);
        assertEquals(timestamp, order.getCreationTime());
    }
}
