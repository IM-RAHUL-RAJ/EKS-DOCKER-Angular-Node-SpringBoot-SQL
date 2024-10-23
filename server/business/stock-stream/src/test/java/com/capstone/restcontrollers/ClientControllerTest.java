package com.capstone.restcontrollers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.capstone.integration.FmtsDao;
import com.capstone.models.Price;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FmtsDao fmtsDao;

    @InjectMocks
    private ClientController clientController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    public void testGetInstrumentsByCategory_Success() throws Exception {
        // Arrange
        Price price = new Price();
//        price.setInstrumentId("T67890");
        price.setAskPrice(new BigDecimal("1.03375"));
        price.setBidPrice(new BigDecimal("1.03390625"));
        
        List<Price> prices = Collections.singletonList(price);
        
        when(fmtsDao.getInstrumentsByCategory("GOVT")).thenReturn(prices);

        // Act & Assert
        mockMvc.perform(get("/api/clients/instruments")
                .param("category", "GOVT")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].instrumentId").value("T67890"))
                .andExpect(jsonPath("$[0].askPrice").value(1.03375));
    }

    @Test
    public void testGetInstrumentsByCategory_NoInstrumentsFound() throws Exception {
        // Arrange
        when(fmtsDao.getInstrumentsByCategory("CD")).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/api/clients/instruments")
                .param("category", "CD")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Instrument category does not exist."));
    }

    @Test
    public void testGetInstrumentsByCategory_InvalidCategory() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/clients/instruments")
                .param("category", "INVALID_CATEGORY")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid category: INVALID_CATEGORY"));
    }

    @Test
    public void testGetInstrumentsByCategory_ServerError() throws Exception {
        // Arrange
        when(fmtsDao.getInstrumentsByCategory("GOVT")).thenThrow(new RuntimeException("Internal server error"));

        // Act & Assert
        mockMvc.perform(get("/api/clients/instruments")
                .param("category", "GOVT")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal server error"));
    }
}

