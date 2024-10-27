package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.models.IncomeCategory;
import com.capstone.models.InvestmentPreference;
import com.capstone.models.InvestmentPurpose;
import com.capstone.models.InvestmentYear;
import com.capstone.models.RiskTolerance;
import com.capstone.services.InvestmentPreferenceService;
import com.fasterxml.jackson.databind.ObjectMapper;

class InvestmentPreferenceControllerWebLayerTest {

    private MockMvc mockMvc;

    @Mock
    private InvestmentPreferenceService service;

    @Mock
    private Logger logger;

    @InjectMocks
    private InvestmentPreferenceController investmentPreferenceController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(investmentPreferenceController).build();
    }

    @Test
    public void test_getInvestmentPreference_success() throws Exception {
        String clientId = "C001";
        InvestmentPreference investmentPreference = new InvestmentPreference(
            "C001",
            InvestmentPurpose.BUSINESS_INVESTMENT,
            "Funds meant for starting or expanding a personal business",
            RiskTolerance.CONSERVATIVE,
            IncomeCategory.ABOVE_80000,
            InvestmentYear.SEVEN_TO_TEN,
            true
        );

        when(service.getInvestmentPreference(clientId)).thenReturn(investmentPreference);

        mockMvc.perform(get("/stock_stream/investment_preference/C001"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("C001"))
                .andExpect(jsonPath("$.investmentPurpose").value("BUSINESS_INVESTMENT"))
                .andExpect(jsonPath("$.investmentPurposeDescription").value("Funds meant for starting or expanding a personal business"))
                .andExpect(jsonPath("$.riskTolerance").value("CONSERVATIVE"))
                .andExpect(jsonPath("$.incomeCategory").value("ABOVE_80000"))
                .andExpect(jsonPath("$.investmentYear").value("SEVEN_TO_TEN"))
                .andExpect(jsonPath("$.isRoboAdvisorTermsAccepted").value(true));
    }

    @Test
    public void test_addInvestmentPreference_success() throws Exception {
        InvestmentPreference investmentPreference = new InvestmentPreference(
            "C008",
            InvestmentPurpose.COLLEGE_FUND,
            "Saving for higher education expenses",
            RiskTolerance.ABOVE_AVERAGE,
            IncomeCategory.BELOW_20000,
            InvestmentYear.ZERO_TO_FIVE,
            true
        );

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(investmentPreference);

        when(service.addInvestmentPreference(investmentPreference)).thenReturn(investmentPreference);

        mockMvc.perform(post("/stock_stream/investment_preference")
        		.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonString))  // Adding content and setting ContentType
                .andDo(print())  // This will print the full request and response to the console for debugging
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("C008"))
                .andExpect(jsonPath("$.investmentPurpose").value("COLLEGE_FUND"))
                .andExpect(jsonPath("$.investmentPurposeDescription").value("Saving for higher education expenses"))
                .andExpect(jsonPath("$.riskTolerance").value("ABOVE_AVERAGE"))
                .andExpect(jsonPath("$.incomeCategory").value("BELOW_20000"))
                .andExpect(jsonPath("$.investmentYear").value("ZERO_TO_FIVE"))
                .andExpect(jsonPath("$.isRoboAdvisorTermsAccepted").value(true));
    }


    @Test
    public void test_updateInvestmentPreference_success() throws Exception {
        InvestmentPreference investmentPreference = new InvestmentPreference(
            "C001",
            InvestmentPurpose.RETIREMENT,
            "Saving for retirement",
            RiskTolerance.AGGRESSIVE,
            IncomeCategory.BELOW_20000,
            InvestmentYear.SEVEN_TO_TEN,
            true
        );
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(investmentPreference);

        when(service.updateInvestmentPreference(investmentPreference)).thenReturn(investmentPreference);

        mockMvc.perform(put("/stock_stream/investment_preference")
                .contentType("application/json")
                .content( jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("C001"))
                .andExpect(jsonPath("$.investmentPurpose").value("RETIREMENT"))
                .andExpect(jsonPath("$.investmentPurposeDescription").value("Saving for retirement"))
                .andExpect(jsonPath("$.riskTolerance").value("AGGRESSIVE"))
                .andExpect(jsonPath("$.incomeCategory").value("BELOW_20000"))
                .andExpect(jsonPath("$.investmentYear").value("SEVEN_TO_TEN"))
                .andExpect(jsonPath("$.isRoboAdvisorTermsAccepted").value(true));
    }

    @Test
    public void test_deleteInvestmentPreference_success() throws Exception {
        String clientId = "C001";
        InvestmentPreference investmentPreference = new InvestmentPreference(
            "C001",
            InvestmentPurpose.RETIREMENT,
            "Saving for retirement",
            RiskTolerance.CONSERVATIVE,
            IncomeCategory.BELOW_20000,
            InvestmentYear.SEVEN_TO_TEN,
            true
        );

        when(service.removeInvestmentPreference(clientId)).thenReturn(investmentPreference);
        
        ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(investmentPreference);

        mockMvc.perform(delete("/stock_stream/investment_preference/C001").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(jsonString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("C001"))
                .andExpect(jsonPath("$.investmentPurpose").value("RETIREMENT"))
                .andExpect(jsonPath("$.investmentPurposeDescription").value("Saving for retirement"))
                .andExpect(jsonPath("$.riskTolerance").value("CONSERVATIVE"))
                .andExpect(jsonPath("$.incomeCategory").value("BELOW_20000"))
                .andExpect(jsonPath("$.investmentYear").value("SEVEN_TO_TEN"))
                .andExpect(jsonPath("$.isRoboAdvisorTermsAccepted").value(true));
    }
}
