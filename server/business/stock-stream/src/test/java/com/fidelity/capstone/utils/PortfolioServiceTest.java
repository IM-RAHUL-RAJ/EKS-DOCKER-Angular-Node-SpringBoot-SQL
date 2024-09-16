package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.stock_stream.Portfolio;

import java.util.List;
import java.util.NoSuchElementException;

public class PortfolioServiceTest {

    private PortfolioService portfolioService;

    @BeforeEach
    public void setUp() {
        portfolioService = new PortfolioService();
    }

    @Test
	@DisplayName("If client id is valid then the portfolio must be displayed")
    public void testGetClientPortfolio_Client1() {
        List<Portfolio> portfolios = portfolioService.getClientPortfolio("6734GHJ");
        assertNotNull(portfolios);
        assertEquals(1, portfolios.size());
        assertEquals("6734GHJ", portfolios.get(0).getClientId());
    }

    @Test
    public void testGetClientPortfolio_Client2() {
        List<Portfolio> portfolios = portfolioService.getClientPortfolio("8734GHJ");
        assertNotNull(portfolios);
        assertEquals(1, portfolios.size());
        assertEquals("8734GHJ", portfolios.get(0).getClientId());
    }

    
    @Test
	@DisplayName("If client id is invalid then exception must be thrown.")
    public void testGetClientPortfolio_NonExistentClient() {
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            portfolioService.getClientPortfolio("nonexistent");
        });
        assertEquals("Client ID nonexistent not found.", exception.getMessage());
    }
}
