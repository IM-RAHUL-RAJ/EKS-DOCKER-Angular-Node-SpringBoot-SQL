package com.fidelity.capstone.stock_stream;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class InstrumentReportTest {

    @Test
    public void testGetAndSetInstrumentId() {
        InstrumentReport report = new InstrumentReport();
        String testId = "ABC123";
        report.setInstrumentId(testId);
        assertEquals(testId, report.getInstrumentId(), "Instrument ID should match the set value.");
    }

    @Test
    public void testGetAndSetPeRatio() {
        InstrumentReport report = new InstrumentReport();
        BigDecimal testPeRatio = new BigDecimal("15.75");
        report.setPeRatio(testPeRatio);
        assertEquals(testPeRatio, report.getPeRatio(), "PE Ratio should match the set value.");
    }

    @Test
    public void testSetPeRatioNull() {
        InstrumentReport report = new InstrumentReport();
        assertThrows(NullPointerException.class, () -> report.setPeRatio(null), "PE Ratio cannot be null");
    }

    @Test
    public void testSetPeRatioNegative() {
        InstrumentReport report = new InstrumentReport();
        BigDecimal negativePeRatio = new BigDecimal("-1.0");
        assertThrows(IllegalArgumentException.class, () -> report.setPeRatio(negativePeRatio), "PE Ratio cannot be negative.");
    }
}
