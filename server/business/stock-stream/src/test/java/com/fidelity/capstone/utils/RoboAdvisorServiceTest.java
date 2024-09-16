package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.stock_stream.IncomeCategory;
import com.fidelity.capstone.stock_stream.Instrument;
import com.fidelity.capstone.stock_stream.InstrumentReport;
import com.fidelity.capstone.stock_stream.InvestmentPreference;
import com.fidelity.capstone.stock_stream.InvestmentPurpose;
import com.fidelity.capstone.stock_stream.InvestmentYear;
import com.fidelity.capstone.stock_stream.Price;
import com.fidelity.capstone.stock_stream.RiskTolerance;
import com.fidelity.capstone.exceptions.RoboAdvisorException;
import com.fidelity.capstone.exceptions.RoboAdvisorMandatoryException;

public class RoboAdvisorServiceTest {
    
    private RoboAdvisorService roboAdvisorService;
    
    @BeforeEach
    void setUp() {
        roboAdvisorService = new RoboAdvisorService();
    }
    
    @Test
    void testAddSuggestion_ValidData() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentId("N123456");
        instrument.setExternalIdType("CUSIP");
        instrument.setExternalId("46625H100");
        instrument.setCategoryId("STOCK");
        instrument.setInstrumentDescription("JPMorgan Chase & Co. Capital Stock");
        instrument.setMaxQuantity(1000);
        instrument.setMinQuantity(1);

        LocalDateTime now = LocalDateTime.now();

        assertDoesNotThrow(() -> {
            roboAdvisorService.addSuggestion(BigDecimal.valueOf(104.75), BigDecimal.valueOf(104.25), now, instrument);
        });

        List<Price> suggestions = roboAdvisorService.getAllSuggestions();
        assertEquals(1, suggestions.size());
        Price price = suggestions.get(0);
        assertEquals(BigDecimal.valueOf(104.75), price.getAskPrice());
        assertEquals(BigDecimal.valueOf(104.25), price.getBidPrice());
        assertEquals(now, price.getPriceTimeStamp());
        assertEquals(instrument, price.getInstrument());
    }

    @Test
    void testAddSuggestion_InvalidData() {
        Instrument instrument = new Instrument();
        instrument.setInstrumentId("N123456");
        instrument.setExternalIdType("CUSIP");
        instrument.setExternalId("46625H100");
        instrument.setCategoryId("STOCK");
        instrument.setInstrumentDescription("JPMorgan Chase & Co. Capital Stock");
        instrument.setMaxQuantity(1000);
        instrument.setMinQuantity(1);

        LocalDateTime now = LocalDateTime.now();

        assertThrows(RoboAdvisorException.RoboAdvisorValidationException.class, () -> {
            roboAdvisorService.addSuggestion(BigDecimal.valueOf(-104.75), BigDecimal.valueOf(104.25), now, instrument);
        });
    }
    
    @Test
    void testGenerateSuggestionBasedOnInvestmentPreferences() throws RoboAdvisorMandatoryException {
        Instrument instrument1 = new Instrument();
        instrument1.setInstrumentId("N123456");
        instrument1.setExternalIdType("CUSIP");
        instrument1.setExternalId("46625H100");
        instrument1.setCategoryId("STOCK");
        instrument1.setInstrumentDescription("JPMorgan Chase & Co. Capital Stock");
        instrument1.setMaxQuantity(1000);
        instrument1.setMinQuantity(1);
        
        Instrument instrument2 = new Instrument();
        instrument2.setInstrumentId("N123789");
        instrument2.setExternalIdType("ISIN");
        instrument2.setExternalId("US0846707026");
        instrument2.setCategoryId("STOCK");
        instrument2.setInstrumentDescription("Berkshire Hathaway Inc. Class A");
        instrument2.setMaxQuantity(10);
        instrument2.setMinQuantity(1);

        LocalDateTime now = LocalDateTime.now();
        
        Price price1 = new Price();
        price1.setAskPrice(BigDecimal.valueOf(104.75));
        price1.setBidPrice(BigDecimal.valueOf(104.25));
        price1.setPriceTimeStamp(now);
        price1.setInstrument(instrument1);
        
        Price price2 = new Price();
        price2.setAskPrice(BigDecimal.valueOf(312500));
        price2.setBidPrice(BigDecimal.valueOf(312000));
        price2.setPriceTimeStamp(now);
        price2.setInstrument(instrument2);

        List<Price> allPrices = List.of(price1, price2);
        
        InstrumentReport report1 = new InstrumentReport();
        report1.setInstrumentId("N123456");
        report1.setPeRatio(BigDecimal.valueOf(15.0));
        
        InstrumentReport report2 = new InstrumentReport();
        report2.setInstrumentId("N123789");
        report2.setPeRatio(BigDecimal.valueOf(25.0));
        
        List<InstrumentReport> instrumentReports = List.of(report1, report2);

        InvestmentPreference preference = new InvestmentPreference(
                "client1",
                InvestmentPurpose.RETIREMENT,
                "Saving for retirement",
                RiskTolerance.AGGRESSIVE,
                IncomeCategory.ABOVE_80000,
                InvestmentYear.TEN_TO_FIFTEEN,
                true
        );

        roboAdvisorService.generateSuggestionBasedOnInvestmentPreferences(preference, allPrices, instrumentReports);

        List<Price> suggestions = roboAdvisorService.getAllSuggestions();
        assertEquals(2, suggestions.size()); 

        assertTrue(suggestions.get(0).getRankScore().compareTo(suggestions.get(1).getRankScore()) >= 0);
       
    }
}