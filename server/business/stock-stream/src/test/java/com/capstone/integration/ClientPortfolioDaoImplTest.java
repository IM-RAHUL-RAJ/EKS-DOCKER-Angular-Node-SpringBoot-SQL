package com.capstone.integration;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.capstone.models.Portfolio;

class ClientPortfolioDaoImplTest {

	static PoolableDataSource dataSource;
	ClientPortfolioDaoImpl dao;
	Connection connection;
	TransactionManager transactionManager;
	
	@BeforeAll
	static void init() throws IOException {
		dataSource=new PoolableDataSource();

	}
	
	@BeforeEach
	void setUp() throws SQLException, IOException {
		dao=new ClientPortfolioDaoImpl(dataSource);
		transactionManager = new TransactionManager(dataSource);
		transactionManager.startTransaction();
	}

	@AfterEach
	void tearDown() throws SQLException {
		transactionManager.rollbackTransaction();

	}
	
	@AfterAll
	static void cleanup() {
		 if (dataSource != null) {
	            dataSource.shutdown();
	        }
	}
	
	
	
	@Test
	@DisplayName("Successful get method")
    void testGetClients() throws SQLException {
	    Connection conn = dataSource.getConnection();
		List<Portfolio> querClients=dao.getClientPortfolio("CLIENT002");
		assertEquals(2,querClients.size());
		
	    }
	
	@Test
    @DisplayName("Client portfolio retrieve throws exception")
    void testGetClientPortfolio_ThrowsException() {
        assertThrows(NullPointerException.class, () -> {
            dao.getClientPortfolio(null);
        });
    }

    @Test
    @DisplayName("Client not found in database")
    void testGetClientPortfolio_ClientNotFound() throws SQLException {
        List<Portfolio> portfolios = dao.getClientPortfolio("NON_EXISTENT_CLIENT");
        assertTrue(portfolios.isEmpty());
    }

    @Test
    @DisplayName("Client portfolio not found in database")
    void testGetClientPortfolio_PortfolioNotFound() throws SQLException {
        List<Portfolio> portfolios = dao.getClientPortfolio("CLIENT_WITH_NO_PORTFOLIO");
        assertTrue(portfolios.isEmpty());
    }
}

