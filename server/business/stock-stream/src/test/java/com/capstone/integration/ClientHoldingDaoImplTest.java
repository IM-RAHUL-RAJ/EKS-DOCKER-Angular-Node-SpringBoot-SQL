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

import com.capstone.exceptions.DatabaseException;
import com.capstone.models.Holding;

class ClientHoldingDaoImplTest {

	static PoolableDataSource dataSource;
	ClientHoldingDaoImpl dao;
	Connection connection;
	TransactionManager transactionManager;

	@BeforeAll
	static void init() throws IOException {
		dataSource = new PoolableDataSource();

	}

	@BeforeEach
	void setUp() throws SQLException, IOException {
		dao = new ClientHoldingDaoImpl(dataSource);
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
		List<Holding> querClients = dao.getClientHoldings("CLIENT002");
		assertEquals(2, querClients.size());

	}

	@Test
	@DisplayName("Client portfolio retrieve throws exception")
	void testGetClientPortfolio_ThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			dao.getClientHoldings(null);
		});
	}

	@Test
	@DisplayName("Client not found in database")
	void testGetClientPortfolio_ClientNotFound() throws SQLException {
		List<Holding> portfolios = dao.getClientHoldings("NON_EXISTENT_CLIENT");
		assertTrue(portfolios.isEmpty());
	}

	@Test
	@DisplayName("Client portfolio not found in database")
	void testGetClientPortfolio_PortfolioNotFound() throws SQLException {
		List<Holding> portfolios = dao.getClientHoldings("CLIENT_WITH_NO_PORTFOLIO");
		assertTrue(portfolios.isEmpty());
	}

	@Test
	void addClientHoldingToSucceed() throws SQLException {

		Holding newHolding = new Holding("Stock A", "INST003", "CLIENT001", 100, 150.50, 15050.00, 155.00, 3.00, 450.00,
				2.50);

		Holding holding = dao.addClientHolding(newHolding);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), "holdings",
				"clientid='CLIENT001' AND instrumentid='INST003'"));
		assertEquals(holding, newHolding);

	}

	@Test
	void addClientHoldingToThrowDatabaseException() throws SQLException {

		Holding newHolding = new Holding("Stock A", "INST002", "CLIENT001", 100, 150.50, 15050.00, 155.00, 3.00, 450.00,
				2.50);

		assertThrows(DatabaseException.class, () -> {
			dao.addClientHolding(newHolding);
		});

	}

	@Test
	void addClientHoldingToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {
			dao.addClientHolding(null);
		});

	}

	@Test
	void updateClientHoldingToSucceed() throws SQLException {

		Holding newHolding = dao.getClientHolding("CLIENT001", "INST002");

		newHolding.setAveragePrice(22.2);

		Holding holding = dao.updateClientHolding(newHolding);

		assertEquals(1, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), "holdings",
				"clientid='CLIENT001' AND instrumentid='INST002'"));
		assertEquals(holding, newHolding);

	}

	@Test
	void updateClientHoldingToThrowDatabaseException() throws SQLException {

		Holding newHolding = new Holding("Stock A", "INST003", "CLIENT001", 100, 150.50, 15050.00, 155.00, 3.00, 450.00,
				2.50);
		;

		newHolding.setAveragePrice(22.2);

		assertThrows(DatabaseException.class, () -> {

			dao.updateClientHolding(newHolding);
		});

	}

	@Test
	void updateClientHoldingToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {

			dao.updateClientHolding(null);
		});

	}
	
	@Test
	void removeClientHoldingToSucceed() throws SQLException {

		Holding holdingToBeDeleted = dao.getClientHolding("CLIENT001", "INST002");


		Holding deletedHolding = dao.removeClientHolding("CLIENT001", "INST002");

		assertEquals(0, DbTestUtils.countRowsInTableWhere(dataSource.getConnection(), "holdings",
				"clientid='CLIENT001' AND instrumentid='INST002'"));
		assertEquals(deletedHolding, holdingToBeDeleted);

	}
	
	@Test
	void removeClientHoldingToThrowDatabaseException() throws SQLException {

		assertThrows(DatabaseException.class, () -> {

			dao.removeClientHolding("CLIENT001", "INST003");
		});


	}
	
	@Test
	void removeClientHoldingToThrowIllegalArgumentException() throws SQLException {

		assertThrows(IllegalArgumentException.class, () -> {

			dao.removeClientHolding(null, null);
		});


	}
}
