package com.fidelity.capstone.stock_stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InstrumentTest {

    @Test
    void testSetInstrumentId_Null() {
        Instrument instrument = new Instrument();
        assertThrows(NullPointerException.class, () -> instrument.setInstrumentId(null));
    }

    @Test
    void testSetInstrumentId_Empty() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setInstrumentId(""));
    }

    @Test
    void testSetInstrumentId_Valid() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentId("123");
        assertEquals("123", instrument.getInstrumentId());
    }

    @Test
    void testSetExternalIdType_Null() {
        Instrument instrument = new Instrument();
        assertThrows(NullPointerException.class, () -> instrument.setExternalIdType(null));
    }

    @Test
    void testSetExternalIdType_Empty() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setExternalIdType(""));
    }

    @Test
    void testSetExternalIdType_Valid() {
        Instrument instrument = new Instrument();
        instrument.setExternalIdType("ISIN");
        assertEquals("ISIN", instrument.getExternalIdType());
    }

    @Test
    void testSetExternalId_Null() {
        Instrument instrument = new Instrument();
        assertThrows(NullPointerException.class, () -> instrument.setExternalId(null));
    }

    @Test
    void testSetExternalId_Empty() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setExternalId(""));
    }

    @Test
    void testSetExternalId_Valid() {
        Instrument instrument = new Instrument();
        instrument.setExternalId("ABC123");
        assertEquals("ABC123", instrument.getExternalId());
    }

    @Test
    void testSetCategoryId_Null() {
        Instrument instrument = new Instrument();
        assertThrows(NullPointerException.class, () -> instrument.setCategoryId(null));
    }

    @Test
    void testSetCategoryId_Empty() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setCategoryId(""));
    }

    @Test
    void testSetCategoryId_Valid() {
        Instrument instrument = new Instrument();
        instrument.setCategoryId("CAT001");
        assertEquals("CAT001", instrument.getCategoryId());
    }

    @Test
    void testSetInstrumentDescription_Null() {
        Instrument instrument = new Instrument();
        assertThrows(NullPointerException.class, () -> instrument.setInstrumentDescription(null));
    }

    @Test
    void testSetInstrumentDescription_Empty() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setInstrumentDescription(""));
    }

    @Test
    void testSetInstrumentDescription_Valid() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentDescription("Description");
        assertEquals("Description", instrument.getInstrumentDescription());
    }

    @Test
    void testSetMaxQuantity_Zero() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setMaxQuantity(0));
    }

    @Test
    void testSetMaxQuantity_Negative() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setMaxQuantity(-1));
    }

    @Test
    void testSetMaxQuantity_Valid() {
        Instrument instrument = new Instrument();
        instrument.setMaxQuantity(100);
        assertEquals(100, instrument.getMaxQuantity());
    }

    @Test
    void testSetMinQuantity_Zero() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setMinQuantity(0));
    }

    @Test
    void testSetMinQuantity_Negative() {
        Instrument instrument = new Instrument();
        assertThrows(IllegalArgumentException.class, () -> instrument.setMinQuantity(-1));
    }

    @Test
    void testSetMinQuantity_Valid() {
        Instrument instrument = new Instrument();
        instrument.setMinQuantity(10);
        assertEquals(10, instrument.getMinQuantity());
    }
}
