package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.exceptions.RoboAdvisorException;
import com.fidelity.capstone.stock_stream.Instrument;
import com.fidelity.capstone.stock_stream.RoboAdvisorSuggestion;

class RoboAdvisorServiceTest {

    private RoboAdvisorService service;
    private Instrument validInstrument;

    @BeforeEach
    void setUp() {
        service = new RoboAdvisorService();
        validInstrument = new Instrument();
        validInstrument.setInstrumentId("ID123");
        validInstrument.setExternalIdType("TypeA");
        validInstrument.setExternalId("ExtID456");
        validInstrument.setCategoryId("Cat789");
        validInstrument.setInstrumentDescription("Test Instrument");
        validInstrument.setMaxQuantity(100);
        validInstrument.setMinQuantity(1);
    }

    @Test
    void testAddSuggestionValid() {
        BigDecimal askPrice = new BigDecimal("100.00");
        BigDecimal bidPrice = new BigDecimal("99.00");
        LocalDateTime now = LocalDateTime.now();

        assertDoesNotThrow(() -> service.addSuggestion(askPrice, bidPrice, now, validInstrument),
                "Adding a valid suggestion should not throw an exception");

        assertEquals(1, service.getAllSuggestions().size(), "There should be one suggestion in the list");
        RoboAdvisorSuggestion suggestion = service.getAllSuggestions().get(0);
        assertEquals(askPrice, suggestion.getAskPrice(), "Ask price should match");
        assertEquals(bidPrice, suggestion.getBidPrice(), "Bid price should match");
        assertEquals(now, suggestion.getPriceTimeStamp(), "Price timestamp should match");
        assertEquals(validInstrument, suggestion.getInstrument(), "Instrument should match");
    }

    @Test
    void testAddSuggestionInvalidAskPrice() {
        BigDecimal invalidAskPrice = new BigDecimal("-1.00");
        BigDecimal bidPrice = new BigDecimal("99.00");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(RoboAdvisorException.RoboAdvisorValidationException.class,
                () -> service.addSuggestion(invalidAskPrice, bidPrice, now, validInstrument),
                "Adding a suggestion with invalid ask price should throw RoboAdvisorValidationException");
    }

    @Test
    void testAddSuggestionInvalidBidPrice() {
        BigDecimal askPrice = new BigDecimal("100.00");
        BigDecimal invalidBidPrice = new BigDecimal("-1.00");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(RoboAdvisorException.RoboAdvisorValidationException.class,
                () -> service.addSuggestion(askPrice, invalidBidPrice, now, validInstrument),
                "Adding a suggestion with invalid bid price should throw RoboAdvisorValidationException");
    }

    @Test
    void testAddSuggestionNullInstrument() {
        BigDecimal askPrice = new BigDecimal("100.00");
        BigDecimal bidPrice = new BigDecimal("99.00");
        LocalDateTime now = LocalDateTime.now();

        assertThrows(RoboAdvisorException.RoboAdvisorValidationException.class,
                () -> service.addSuggestion(askPrice, bidPrice, now, null),
                "Adding a suggestion with null instrument should throw RoboAdvisorValidationException");
    }

    @Test
    void testGetAllSuggestions() {
        BigDecimal askPrice = new BigDecimal("100.00");
        BigDecimal bidPrice = new BigDecimal("99.00");
        LocalDateTime now = LocalDateTime.now();
        service.addSuggestion(askPrice, bidPrice, now, validInstrument);

        assertEquals(1, service.getAllSuggestions().size(), "There should be one suggestion in the list");
        RoboAdvisorSuggestion suggestion = service.getAllSuggestions().get(0);
        assertEquals(askPrice, suggestion.getAskPrice(), "Ask price should match");
        assertEquals(bidPrice, suggestion.getBidPrice(), "Bid price should match");
        assertEquals(now, suggestion.getPriceTimeStamp(), "Price timestamp should match");
        assertEquals(validInstrument, suggestion.getInstrument(), "Instrument should match");
    }
}
