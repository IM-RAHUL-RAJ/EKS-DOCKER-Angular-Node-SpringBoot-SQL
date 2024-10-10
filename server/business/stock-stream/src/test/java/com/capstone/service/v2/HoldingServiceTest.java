package com.capstone.service.v2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.capstone.models.Holding;
import com.capstone.services.v2.HoldingService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import com.capstone.exceptions.PortfolioException;

@SpringBootTest
@Transactional
public class HoldingServiceTest {

	@Autowired
    private HoldingService holdingService;
    private Holding holding1;
    private Holding holding2;

    @BeforeEach
    public void setUp() {
        holding1 = new Holding("StockA", "ID1", "C001", 100, BigDecimal.valueOf(50.0), 5000.0, BigDecimal.valueOf( 55.0), 10.0, 500.0, 2.0);
        holding2 = new Holding("StockB", "ID2", "C001", 200, BigDecimal.valueOf(30.0) , 6000.0,BigDecimal.valueOf(35.0), 16.67, 1000.0, 3.0);
        try {
            holdingService.addPortfolioItem(holding1);
            holdingService.addPortfolioItem(holding2);
        } catch (PortfolioException e) {
            fail("Setup failed: " + e.getMessage());
        }
    }
    


    @Test
    @DisplayName("adding valid portfolio item")
    public void testAddPortfolioItem() throws SQLException {
        Holding portfolio3 = new Holding("StockC", "I0021", "C002", 150, new BigDecimal(40.0).setScale(2), 6000.0, new BigDecimal(45.0).setScale(2), 12.5, 750.0, 1.5);
        try {
            holdingService.addPortfolioItem(portfolio3);
            List<Holding> client2Portfolios = holdingService.getClientPortfolio("C002");
            assertEquals(3, client2Portfolios.size());
        } catch (PortfolioException e) {
            fail("Add portfolio item failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Removing valid portfolio item")
    public void testRemovePortfolioItem() throws SQLException {
        try {
            holdingService.removePortfolioItem(holding1, 50);
            List<Holding> client1Portfolios = holdingService.getClientPortfolio("C001");
            assertEquals(4, client1Portfolios.size());
            assertEquals(100, client1Portfolios.get(0).getQuantity());

            holdingService.removePortfolioItem(holding1, 50);
            client1Portfolios = holdingService.getClientPortfolio("C001");
            assertEquals(3, client1Portfolios.size());
        } catch (PortfolioException e) {
            fail("Remove portfolio item failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Updating valid portfolio item")
    public void testUpdatePortfolioItem() throws SQLException {
        Holding updatedPortfolio = new Holding("StockA", "ID1", "C001", 150, new BigDecimal(52.0).setScale(2), 7800.0, new BigDecimal(57.0).setScale(2), 9.62, 750.0, 2.5);
        try {
            holdingService.updatePortfolioItem(updatedPortfolio);
            List<Holding> client1Portfolios = holdingService.getClientPortfolio("C001");
            assertEquals(4, client1Portfolios.size());
            Holding portfolio = client1Portfolios.stream()
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Updated portfolio not found"));
            assertEquals(150, portfolio.getQuantity());
        } catch (PortfolioException e) {
            fail("Update portfolio item failed: " + e.getMessage());
        }
    }
    
    @Test
    @DisplayName("adding invalid portfolio item")
    public void testAddInvalidPortfolioItem() {
        Holding invalidPortfolio = new Holding(null, "ID4", "Client2", 150, new BigDecimal(40.0).setScale(2), 6000.0, new BigDecimal(45.0).setScale(2), 12.5, 750.0, 1.5);
        Exception exception = assertThrows(PortfolioException.class, () -> {
            holdingService.addPortfolioItem(invalidPortfolio);
        });
        assertEquals("Instrument ID is invalid.", exception.getMessage());
    }
    
    @Test
    @DisplayName("Removing more than existing quantity")
    public void testRemoveMoreQuantityThanExists() throws SQLException {
        try {
            holdingService.removePortfolioItem(holding1, 150);
            List<Holding> client1Portfolios = holdingService.getClientPortfolio("C001");
            assertEquals(3, client1Portfolios.size());
        } catch (PortfolioException e) {
            fail("Remove portfolio item failed: " + e.getMessage());
        }
    }
    
    
    @Test
    @DisplayName("Retrieving invalid portfolio item")
    public void testGetClientPortfolio() throws SQLException {
        List<Holding> client1Portfolios = holdingService.getClientPortfolio("C001");
        assertEquals(4, client1Portfolios.size());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            holdingService.getClientPortfolio("Client3");
        });
        assertEquals("Client ID Client3 not found.", exception.getMessage());
    }
}