package com.capstone.models;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.capstone.models.Instrument;
import com.capstone.models.Price;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

	private Price price;
    private Instrument instrument;

    @BeforeEach
    void setUp() {
        price = new Price();
        instrument = new Instrument();
    }

    @Test
    void testSetGetAskPrice() {
        BigDecimal askPrice = new BigDecimal("100.00");
        price.setAskPrice(askPrice);
        assertEquals(askPrice, price.getAskPrice(), "Ask price should be set and retrieved correctly");
    }

    @Test
    void testSetGetBidPrice() {
        BigDecimal bidPrice = new BigDecimal("99.99");
        price.setBidPrice(bidPrice);
        assertEquals(bidPrice, price.getBidPrice(), "Bid price should be set and retrieved correctly");
    }

    @Test
    void testSetGetPriceTimeStamp() {
        LocalDateTime now = LocalDateTime.now();
        price.setPriceTimeStamp(Timestamp.from(Instant.now()));
        assertEquals(now, price.getPriceTimeStamp(), "Price timestamp should be set and retrieved correctly");
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
        
        price.setInstrument(instrument);
        assertEquals(instrument, price.getInstrument(), "Instrument should be set and retrieved correctly");
    }

    @Test
    void testSetAskPriceNull() {
        assertThrows(NullPointerException.class, () -> price.setAskPrice(null), "Ask Price cannot be null");
    }

    @Test
    void testSetBidPriceNull() {
        assertThrows(NullPointerException.class, () -> price.setBidPrice(null), "Bid Price cannot be null");
    }

    @Test
    void testSetPriceTimeStampNull() {
        assertThrows(NullPointerException.class, () -> price.setPriceTimeStamp(null), "Price TimeStamp cannot be null");
    }

    @Test
    void testSetInstrumentNull() {
        assertThrows(NullPointerException.class, () -> price.setInstrument(null), "Instrument cannot be null");
    }

    @Test
    void testSetAskPriceNegative() {
        assertThrows(IllegalArgumentException.class, () -> price.setAskPrice(new BigDecimal("-1.00")), "Ask Price cannot be negative");
    }

    @Test
    void testSetBidPriceNegative() {
        assertThrows(IllegalArgumentException.class, () -> price.setBidPrice(new BigDecimal("-1.00")), "Bid Price cannot be negative");
    }

    @Test
    void testAskPriceScale() {
        BigDecimal askPrice = new BigDecimal("100.12345");
        price.setAskPrice(askPrice);
        assertEquals(new BigDecimal("100.12"), price.getAskPrice(), "Ask Price should be scaled to 2 decimal places");
    }

    @Test
    void testBidPriceScale() {
        BigDecimal bidPrice = new BigDecimal("99.98765");
        price.setBidPrice(bidPrice);
        assertEquals(new BigDecimal("99.99"), price.getBidPrice(), "Bid Price should be scaled to 2 decimal places");
    }

}
