package com.capstone.restcontrollers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.dto.LivePricingResponse;
import com.capstone.integration.ClientHoldingDao;
import com.capstone.integration.FmtsDao;
import com.capstone.models.Holding;
import com.capstone.models.Instrument;
import com.capstone.restcontroller.PortfolioController;
import com.capstone.services.v2.HoldingService;

class PortfolioControllerWebLayerTest {

	private MockMvc mockMvc;

    @Mock
    private Logger logger;

    @Mock
    private ClientHoldingDao dao;

    @Mock
    private HoldingService service;

    @Mock
    private FmtsDao fmtsDao;

    @InjectMocks
    private PortfolioController portfolioController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(portfolioController).build();
    }

    @Test
    public void testPing() throws Exception {
        mockMvc.perform(get("/client/ping"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetWidgets() throws Exception {
        String clientId = "123";
        List<Holding> holdings = Arrays.asList(
            new Holding("Instrument1", "ID1", clientId, 10, new BigDecimal("100.00"), 1000.0, new BigDecimal("110.00"), 10.0, 100.0, 1.0),
            new Holding("Instrument2", "ID2", clientId, 20, new BigDecimal("200.00"), 4000.0, new BigDecimal("210.00"), 5.0, 200.0, 0.5)
        );

        when(service.getClientPortfolio(clientId)).thenReturn(holdings);

        mockMvc.perform(get("/client/holdings/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().json("[{'instrument':'Instrument1','instrumentId':'ID1','clientId':'123','quantity':10,'averagePrice':100.00,'investedCapital':1000.0,'ltp':110.00,'percentChange':10.0,'profitLoss':100.0,'dayChangePercent':1.0},{'instrument':'Instrument2','instrumentId':'ID2','clientId':'123','quantity':20,'averagePrice':200.00,'investedCapital':4000.0,'ltp':210.00,'percentChange':5.0,'profitLoss':200.0,'dayChangePercent':0.5}]"));
    }

    @Test
    public void testGetWidgetsNoContent() throws Exception {
        String clientId = "123";

        when(service.getClientPortfolio(clientId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/client/holdings/{id}", clientId))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetLivePrices() throws Exception {
        List<LivePricingResponse> livePricing = Arrays.asList(
            new LivePricingResponse(
                104.75,
                104.25,
                "21-AUG-19 10.00.01.042000000 AM GMT",
                new Instrument("N123456", "CUSIP", "46625H100", "STOCK", "JPMorgan Chase & Co. Capital Stock", 1000, 1)
            ),
            new LivePricingResponse(
                105.75,
                105.25,
                "21-AUG-19 10.05.01.042000000 AM GMT",
                new Instrument("N123457", "CUSIP", "46625H101", "STOCK", "Another Stock", 1000, 1)
            )
        );

        when(fmtsDao.getLivePricing()).thenReturn(livePricing);

        mockMvc.perform(get("/client/live-prices"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) content().json("[{'askPrice':104.75,'bidPrice':104.25,'priceTimestamp':'21-AUG-19 10.00.01.042000000 AM GMT','instrument':{'instrumentId':'N123456','externalIdType':'CUSIP','externalId':'46625H100','categoryId':'STOCK','instrumentDescription':'JPMorgan Chase & Co. Capital Stock','maxQuantity':1000,'minQuantity':1}},{'askPrice':105.75,'bidPrice':105.25,'priceTimestamp':'21-AUG-19 10.05.01.042000000 AM GMT','instrument':{'instrumentId':'N123457','externalIdType':'CUSIP','externalId':'46625H101','categoryId':'STOCK','instrumentDescription':'Another Stock','maxQuantity':1000,'minQuantity':1}}]"));
    }
}


