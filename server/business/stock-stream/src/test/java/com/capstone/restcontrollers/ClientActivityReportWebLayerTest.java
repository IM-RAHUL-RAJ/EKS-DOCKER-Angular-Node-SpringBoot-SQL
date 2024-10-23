package com.capstone.restcontrollers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.capstone.models.ClientActivityReport;
import com.capstone.services.ClientActivityReportService;

class ClientActivityReportWebLayerTest {

	private MockMvc mockMvc;

    @Mock
    private ClientActivityReportService service;

    @Mock
    private Logger logger;

    @InjectMocks
    private ClientActivityReportController controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    
    
    @Test
    public void testPrintClientActivityReports() throws Exception {
        List<ClientActivityReport> reports = Arrays.asList(
            new ClientActivityReport("Monthly Performance Overview for C001", "A detailed summary of the client's portfolio performance over the past month, including key metrics such as total returns, top-performing assets, and areas for improvement.", "1"),
            new ClientActivityReport("Quarterly Investment Insights for C001", "An in-depth analysis of the client's investment activities for the quarter, highlighting significant market trends, portfolio adjustments, and strategic recommendations for the next quarter.", "2"),
            new ClientActivityReport("Annual Financial Health Check for C001", "A comprehensive review of the client's financial health over the past year, covering portfolio performance, risk assessment, and personalized advice for achieving long-term financial goals.", "3"),
            new ClientActivityReport("Customized Market Analysis Report for C001", "A tailored report providing insights into market trends and forecasts relevant to the client's investment strategy, including sector performance, economic indicators, and potential opportunities.", "4")
        );

        when(service.getClientActivityReports("C001")).thenReturn(reports);

        mockMvc.perform(get("/api/clients/reports/C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    String response = result.getResponse().getContentAsString();
                    System.out.println("Response: " + response);
                });
    }

    @Test
    public void testGetClientActivityReports_Simplified() throws Exception {
        List<ClientActivityReport> reports = Arrays.asList(
            new ClientActivityReport("Monthly Performance Overview for C001", "A detailed summary of the client's portfolio performance over the past month, including key metrics such as total returns, top-performing assets, and areas for improvement.", "1"),
            new ClientActivityReport("Quarterly Investment Insights for C001", "An in-depth analysis of the client's investment activities for the quarter, highlighting significant market trends, portfolio adjustments, and strategic recommendations for the next quarter.", "2"),
            new ClientActivityReport("Annual Financial Health Check for C001", "A comprehensive review of the client's financial health over the past year, covering portfolio performance, risk assessment, and personalized advice for achieving long-term financial goals.", "3"),
            new ClientActivityReport("Customized Market Analysis Report for C001", "A tailored report providing insights into market trends and forecasts relevant to the client's investment strategy, including sector performance, economic indicators, and potential opportunities.", "4")
        );

        when(service.getClientActivityReports("C001")).thenReturn(reports);

        mockMvc.perform(get("/api/clients/reports/C001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> {
                    String response = result.getResponse().getContentAsString();
                    System.out.println("Response: " + response);
                })
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].summary").exists())
                .andExpect(jsonPath("$[0].clientId").exists());
    }




    @Test
    public void testGetClientActivityReports_NoContent() throws Exception {
        when(service.getClientActivityReports("client1")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/clients/reports/client1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(service, times(1)).getClientActivityReports("client1");
    }

    @Test
    public void testGetClientActivityReports_BadRequest() throws Exception {
        when(service.getClientActivityReports("invalidClient")).thenThrow(new IllegalArgumentException("Invalid client ID"));

        mockMvc.perform(get("/api/clients/reports/invalidClient")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(service, times(1)).getClientActivityReports("invalidClient");
    }

    @Test
    public void testGetClientActivityReports_InternalServerError() throws Exception {
        when(service.getClientActivityReports("client1")).thenThrow(new RuntimeException("Internal Server Error"));

        mockMvc.perform(get("/api/clients/reports/client1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(service, times(1)).getClientActivityReports("client1");
    }

}
