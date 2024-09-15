package com.fidelity.capstone.stock_stream;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoboAdvisorSuggestionTest {

    private RoboAdvisorSuggestion suggestion;
    private Instrument instrument;

    @BeforeEach
    void setUp() {
        suggestion = new RoboAdvisorSuggestion();
        instrument = new Instrument();
    }

    @Test
    void testSetGetAskPrice() {
        BigDecimal askPrice = new BigDecimal("100.00");
        suggestion.setAskPrice(askPrice);
        assertEquals(askPrice, suggestion.getAskPrice(), "Ask price should be set and retrieved correctly");
    }

    @Test
    void testSetGetBidPrice() {
        BigDecimal bidPrice = new BigDecimal("99.99");
        suggestion.setBidPrice(bidPrice);
        assertEquals(bidPrice, suggestion.getBidPrice(), "Bid price should be set and retrieved correctly");
    }

    @Test
    void testSetGetPriceTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        suggestion.setPriceTimeStamp(now);
        assertEquals(now, suggestion.getPriceTimeStamp(), "Price timestamp should be set and retrieved correctly");
    }

    @Test
    void testSetGetInstrument() {
        instrument.setInstrumentId("ID123");
        instrument.setExternalIdType("TypeA");
        instrument.setExternalId("ExtID456");
        instrument.setCategoryId("Cat789");
        instrument.setInstrumentDescription("Test Instrument");
        instrument.setMaxQuantity(100);
        instrument.setMinQuantity(1);
        
        suggestion.setInstrument(instrument);
        assertEquals(instrument, suggestion.getInstrument(), "Instrument should be set and retrieved correctly");
    }

    @Test
    void testSetAskPriceNull() {
        assertThrows(NullPointerException.class, () -> suggestion.setAskPrice(null), "Ask Price cannot be null");
    }

    @Test
    void testSetBidPriceNull() {
        assertThrows(NullPointerException.class, () -> suggestion.setBidPrice(null), "Bid Price cannot be null");
    }

    @Test
    void testSetPriceTimeStampNull() {
        assertThrows(NullPointerException.class, () -> suggestion.setPriceTimeStamp(null), "Price TimeStamp cannot be null");
    }

    @Test
    void testSetInstrumentNull() {
        assertThrows(NullPointerException.class, () -> suggestion.setInstrument(null), "Instrument cannot be null");
    }

    @Test
    void testSetAskPriceNegative() {
        assertThrows(IllegalArgumentException.class, () -> suggestion.setAskPrice(new BigDecimal("-1.00")), "Ask Price cannot be negative");
    }

    @Test
    void testSetBidPriceNegative() {
        assertThrows(IllegalArgumentException.class, () -> suggestion.setBidPrice(new BigDecimal("-1.00")), "Bid Price cannot be negative");
    }

    @Test
    void testAskPriceScale() {
        BigDecimal askPrice = new BigDecimal("100.12345");
        suggestion.setAskPrice(askPrice);
        assertEquals(new BigDecimal("100.12"), suggestion.getAskPrice(), "Ask Price should be scaled to 2 decimal places");
    }

    @Test
    void testBidPriceScale() {
        BigDecimal bidPrice = new BigDecimal("99.98765");
        suggestion.setBidPrice(bidPrice);
        assertEquals(new BigDecimal("99.99"), suggestion.getBidPrice(), "Bid Price should be scaled to 2 decimal places");
    }

}
