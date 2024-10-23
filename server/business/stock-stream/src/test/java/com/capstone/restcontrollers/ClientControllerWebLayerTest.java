package com.capstone.restcontrollers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.dto.LivePricingResponse;
import com.capstone.integration.FmtsDao;
import com.capstone.models.Holding;
import com.capstone.models.Instrument;
import com.capstone.services.HoldingService;

class ClientControllerWebLayerTest {

	private MockMvc mockMvc;

    @Mock
    private HoldingService holdingService;

    @Mock
    private FmtsDao fmtsDao;

    @Mock
    private Logger logger;
    
    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testGetWidgets() throws Exception {
        List<Holding> holdings = Arrays.asList(
            new Holding("Stock A", "INST001", "C001", 100, BigDecimal.valueOf(150.5), 15050.0, BigDecimal.valueOf(155), 3.0, 450.0, 2.5)
        );

        when(holdingService.getClientPortfolio("C001")).thenReturn(holdings);

        mockMvc.perform(get("/api/clients/holdings/C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String response = result.getResponse().getContentAsString();
                    System.out.println("Response: " + response);
                })
                .andExpect(jsonPath("$[0].instrument").value("Stock A"))
                .andExpect(jsonPath("$[0].instrumentId").value("INST001"))
                .andExpect(jsonPath("$[0].quantity").value(100))
                .andExpect(jsonPath("$[0].averagePrice").value(150.5))
                .andExpect(jsonPath("$[0].investedCapital").value(15050.0))
                .andExpect(jsonPath("$[0].ltp").value(155))
                .andExpect(jsonPath("$[0].percentChange").value(3.0))
                .andExpect(jsonPath("$[0].profitLoss").value(450.0))
                .andExpect(jsonPath("$[0].dayChangePercent").value(2.5))
                .andExpect(jsonPath("$[0].clientId").value("C001"));
                

        verify(holdingService, times(1)).getClientPortfolio("C001");
    }
    
    @Test
    public void testGetWidgets_NoContent() throws Exception {
        when(holdingService.getClientPortfolio("C001")).thenReturn(null);

        mockMvc.perform(get("/api/clients/holdings/C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(holdingService, times(1)).getClientPortfolio("C001");
    }

    @Test
    public void testGetWidgets_InternalServerError() throws Exception {
        when(holdingService.getClientPortfolio("C001")).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/api/clients/holdings/C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(holdingService, times(1)).getClientPortfolio("C001");
    }

    @Test
    public void testGetLivePrices() throws Exception {
        List<LivePricingResponse> livePricing = Arrays.asList(
            new LivePricingResponse(104.75, 104.25, "21-AUG-19 10.00.01.042000000 AM GMT", new Instrument("N123456", "CUSIP", "46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock", 1000, 1))
        );

        when(fmtsDao.getLivePricing()).thenReturn(livePricing);

        mockMvc.perform(get("/api/clients/live-prices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].askPrice").value(104.75))
                .andExpect(jsonPath("$[0].bidPrice").value(104.25))
                .andExpect(jsonPath("$[0].priceTimestamp").value("21-AUG-19 10.00.01.042000000 AM GMT"))
                .andExpect(jsonPath("$[0].instrument.instrumentId").value("N123456"))
                .andExpect(jsonPath("$[0].instrument.externalIdType").value("CUSIP"))
                .andExpect(jsonPath("$[0].instrument.externalId").value("46625H100"))
                .andExpect(jsonPath("$[0].instrument.categoryId").value("STOCK"))
                .andExpect(jsonPath("$[0].instrument.instrumentDescription").value("JPMorgan Chase & Co. Capital Stock"))
                .andExpect(jsonPath("$[0].instrument.maxQuantity").value(1000))
                .andExpect(jsonPath("$[0].instrument.minQuantity").value(1));

        verify(fmtsDao, times(1)).getLivePricing();
    }
    
    

    @Test
    public void testGetLivePrices_InternalServerError() throws Exception {
        when(fmtsDao.getLivePricing()).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/api/clients/live-prices")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(fmtsDao, times(1)).getLivePricing();
    }
}

