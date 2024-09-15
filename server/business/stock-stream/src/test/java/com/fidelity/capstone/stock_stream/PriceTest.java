package com.fidelity.capstone.stock_stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void testSetAskPrice_Negative() {
        Price price = new Price();
        assertThrows(IllegalArgumentException.class, () -> price.setAskPrice(-1.0));
    }

    @Test
    void testSetAskPrice_Valid() {
        Price price = new Price();
        price.setAskPrice(100.0);
        assertEquals(100.0, price.getAskPrice());
    }

    @Test
    void testSetBidPrice_Negative() {
        Price price = new Price();
        assertThrows(IllegalArgumentException.class, () -> price.setBidPrice(-1.0));
    }

    @Test
    void testSetBidPrice_Valid() {
        Price price = new Price();
        price.setBidPrice(90.0);
        assertEquals(90.0, price.getBidPrice());
    }

    @Test
    void testSetPriceTimestamp_Null() {
        Price price = new Price();
        assertThrows(NullPointerException.class, () -> price.setPriceTimestamp(null));
    }

    @Test
    void testSetPriceTimestamp_Empty() {
        Price price = new Price();
        assertThrows(IllegalArgumentException.class, () -> price.setPriceTimestamp(""));
    }

    @Test
    void testSetPriceTimestamp_Valid() {
        Price price = new Price();
        price.setPriceTimestamp("2024-09-15T10:15:30");
        assertEquals("2024-09-15T10:15:30", price.getPriceTimestamp());
    }

    @Test
    void testSetInstrument_Null() {
        Price price = new Price();
        assertThrows(NullPointerException.class, () -> price.setInstrument(null));
    }

    @Test
    void testSetInstrument_Valid() {
        Price price = new Price();
        Instrument instrument = new Instrument();
        instrument.setInstrumentId("123");
        price.setInstrument(instrument);
        assertEquals(instrument, price.getInstrument());
    }
}
