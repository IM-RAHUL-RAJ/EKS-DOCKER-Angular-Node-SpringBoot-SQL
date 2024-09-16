package com.fidelity.capstone.utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fidelity.capstone.stock_stream.Portfolio;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;

public class PortfolioServiceTest {

    private PortfolioService portfolioService;
    private Portfolio portfolio1;
    private Portfolio portfolio2;

    @BeforeEach
    public void setUp() {
        portfolioService = new PortfolioService();
        portfolio1 = new Portfolio("StockA", "ID1", "Client1", 100, 50.0, 5000.0, 55.0, 10.0, 500.0, 2.0);
        portfolio2 = new Portfolio("StockB", "ID2", "Client1", 200, 30.0, 6000.0, 35.0, 16.67, 1000.0, 3.0);
        try {
            portfolioService.addPortfolioItem(portfolio1);
            portfolioService.addPortfolioItem(portfolio2);
        } catch (PortfolioException e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    public void testAddPortfolioItem() {
        Portfolio portfolio3 = new Portfolio("StockC", "ID3", "Client2", 150, 40.0, 6000.0, 45.0, 12.5, 750.0, 1.5);
        try {
            portfolioService.addPortfolioItem(portfolio3);
            List<Portfolio> client2Portfolios = portfolioService.getClientPortfolio("Client2");
            assertEquals(1, client2Portfolios.size());
            assertEquals("ID3", client2Portfolios.get(0).getInstrumentId());
        } catch (PortfolioException e) {
            fail("Add portfolio item failed: " + e.getMessage());
        }
    }

    @Test
    public void testRemovePortfolioItem() {
        try {
            portfolioService.removePortfolioItem(portfolio1, 50);
            List<Portfolio> client1Portfolios = portfolioService.getClientPortfolio("Client1");
            assertEquals(2, client1Portfolios.size());
            assertEquals(50, client1Portfolios.get(0).getQuantity());

            portfolioService.removePortfolioItem(portfolio1, 50);
            client1Portfolios = portfolioService.getClientPortfolio("Client1");
            assertEquals(1, client1Portfolios.size());
            assertEquals("ID2", client1Portfolios.get(0).getInstrumentId());
        } catch (PortfolioException e) {
            fail("Remove portfolio item failed: " + e.getMessage());
        }
    }

    @Test
    public void testUpdatePortfolioItem() {
        Portfolio updatedPortfolio = new Portfolio("StockA", "ID1", "Client1", 150, 52.0, 7800.0, 57.0, 9.62, 750.0, 2.5);
        try {
            portfolioService.updatePortfolioItem(updatedPortfolio);
            List<Portfolio> client1Portfolios = portfolioService.getClientPortfolio("Client1");
            assertEquals(2, client1Portfolios.size());
            Portfolio portfolio = client1Portfolios.stream()
                    .filter(p -> p.getInstrumentId().equals("ID1"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Updated portfolio not found"));
            assertEquals(150, portfolio.getQuantity());
            assertEquals(52.0, portfolio.getAveragePrice());
        } catch (PortfolioException e) {
            fail("Update portfolio item failed: " + e.getMessage());
        }
    }
    @Test
    public void testGetClientPortfolio() {
        List<Portfolio> client1Portfolios = portfolioService.getClientPortfolio("Client1");
        assertEquals(2, client1Portfolios.size());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            portfolioService.getClientPortfolio("Client3");
        });
        assertEquals("Client ID Client3 not found.", exception.getMessage());
    }
}