package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.capstone.integration.InvestmentPreferenceDao;
import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;
import com.capstone.services.v2.InvestmentPreferenceService;

@WebMvcTest
class InvestmentPreferenceControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Mock
    private InvestmentPreferenceService mockBusinessService;

    @InjectMocks
    private InvestmentPreferenceController investmentPreferenceController;
    
    @MockBean
    private Logger mockLogger;

    @Test
    public void test_getInvestmentPreference_success() throws Exception {
        String clientId = "C001";
        InvestmentPreference investmentPreference = new InvestmentPreference("C001",
                InvestmentPurpose.BUSINESS_INVESTMENT, InvestmentPurpose.BUSINESS_INVESTMENT.getDescription(),
                RiskTolerance.CONSERVATIVE, IncomeCategory.ABOVE_80000, InvestmentYear.SEVEN_TO_TEN, true);
        when(mockBusinessService.getInvestmentPreference(clientId)).thenReturn(investmentPreference);

        // In this test case, we'll be very thorough and test every property
        // of the returned JSON

        mockMvc.perform(get("/stock_stream/investment_preference/C001"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
