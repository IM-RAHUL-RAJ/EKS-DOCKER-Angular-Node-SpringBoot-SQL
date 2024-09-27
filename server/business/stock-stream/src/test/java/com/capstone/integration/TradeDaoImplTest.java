package com.capstone.integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import com.capstone.enums.OrderStatus;
import com.capstone.exceptions.DatabaseException;
import com.capstone.models.*;

public class TradeDaoImplTest {
	static final String TRADES_TABLE = "SS_TRADES";
	static final String ORDERS_TABLE = "SS_ORDERS";
	static final String INSTRUMENTS_TABLE = "SS_INSTRUMENTS";
	static final String PRICE_TABLE = "SS_PRICE";

	static PoolableDataSource dataSource;
	TradeDao dao;
	private TransactionManager transactionManager;
	private DataSource mockDataSource;
	private TradeDao tradeDao;

	@BeforeAll
	static void init() throws IOException {
		dataSource = new PoolableDataSource();
	}

	@BeforeEach
	void setUp() throws SQLException, IOException {
		dao = new TradeDaoImpl(dataSource);
		transactionManager = new TransactionManager(dataSource);
		transactionManager.startTransaction();
		mockDataSource = mock();
		tradeDao = new TradeDaoImpl(mockDataSource);
	}

	@AfterEach
	void tearDown() throws SQLException {
		transactionManager.rollbackTransaction();
	}

	@AfterAll
	static void cleanup() {
		dataSource.shutdown();
	}

	@Test
	void testQueryInstrument_Success() throws SQLException {
		List<Instrument> allInstruments = dao.getAllInstruments();
		assertEquals(DbTestUtils.countRowsInTable(dataSource.getConnection(), INSTRUMENTS_TABLE),
				allInstruments.size());
	}

	@Test
	void testQueryInstrument_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getAllInstruments();
		});
	}

	@Test
	void testQueryTrades_Success() throws SQLException {
		List<Trade> allTrades = dao.getTradeHistory();
		assertEquals(DbTestUtils.countRowsInTable(dataSource.getConnection(), TRADES_TABLE), allTrades.size());
	}

	@Test
	void testQueryTrades_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getAllInstruments();
		});
	}

	@Test
	void testQueryOrders_Success() throws SQLException {
		List<Order> allorders = dao.getAllOrders();
		assertEquals(DbTestUtils.countRowsInTable(dataSource.getConnection(), ORDERS_TABLE), allorders.size());
	}

	@Test
	void testQueryOrders_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getAllInstruments();
		});
	}

	@Test
	void testQueryPendingOrders_Success() throws SQLException {
		List<Order> pendingOrders = dao.getAllPendingOrders();
		assertEquals(1, pendingOrders.size());
	}

	@Test
	void testQueryPendingOrders_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getAllInstruments();
		});
	}

	@Test
	void testQueryGetInstrumentById_Success() throws SQLException {
		Instrument instrument = dao.getInstrumentById("I001");
		assertEquals(instrument.getInstrumentDescription(), "Instrument 1");
	}

	@Test
	void testQueryGetInstrumentById_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getInstrumentById("I001");
		});
	}

	@Test
	void testQueryGetPrice_Success() {
		Price price = dao.getPrice("I001");
		assertEquals(price.getBidPrice(), new BigDecimal(140));
	}

	@Test
	void testQueryGetPrice_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getPrice("I001");
		});
	}

	@Test
	void testQueryGetTradeById_Success() {
		Trade trade = dao.getTradeById("T002");
		assertEquals(trade.getDirection(), "S");
	}

	@Test
	void testQueryGetTradeById_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getTradeById("T002");
		});
	}

	@Test
	void testQueryGetOrderById_Success() {
		Order order = dao.getOrderById("O002");
		assertEquals(order.getTargetPrice(), new BigDecimal(245));
	}

	@Test
	void testQueryGetOrderById_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.getOrderById("O002");
		});
	}

	@Test
	void testInsertTrade_Success() throws SQLException {
		Connection conn = dataSource.getConnection();
		int oldSize = DbTestUtils.countRowsInTable(conn, TRADES_TABLE);
		assertEquals(0, DbTestUtils.countRowsInTableWhere(conn, TRADES_TABLE, "TRADE_ID = 'DUMMYID'"));

		dao.insertTrade(new Trade("DUMMYID", "C001", "O003", "I001", 1, new BigDecimal(1), "S",
				new Timestamp(System.currentTimeMillis())));

		assertEquals(oldSize + 1, DbTestUtils.countRowsInTable(conn, TRADES_TABLE));
		assertEquals(1,
				DbTestUtils.countRowsInTableWhere(conn, TRADES_TABLE, "TRADE_ID = 'DUMMYID' and DIRECTION = 'S'"));
	}

	@Test
	void testInsertTrade_ThrowsDatabaseException() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.insertTrade(new Trade("DUMMYID", "DUMMYID1", "DUMMYID3", "DUMMYID4", 1, new BigDecimal(1), "S",
					new Timestamp(System.currentTimeMillis())));
		});
	}

	@Test
	void testInsertOrder_Success() throws SQLException {
		Connection conn = dataSource.getConnection();
		int oldSize = DbTestUtils.countRowsInTable(conn, ORDERS_TABLE);
		assertEquals(0, DbTestUtils.countRowsInTableWhere(conn, ORDERS_TABLE, "ORDER_ID = 'DUMMYID'"));

		dao.insertOrder(new Order("DUMMYID", "C001", "I001", 1, new BigDecimal(1), "S", OrderStatus.PENDING,
				new Timestamp(System.currentTimeMillis())));

		assertEquals(oldSize + 1, DbTestUtils.countRowsInTable(conn, ORDERS_TABLE));
		assertEquals(1,
				DbTestUtils.countRowsInTableWhere(conn, ORDERS_TABLE, "ORDER_ID = 'DUMMYID' and DIRECTION = 'S'"));
	}

	@Test
	void testInsertOrder_ThrowsDatabaseException() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.insertOrder(new Order("DUMMYID", "DUMMYID1", "DUMMYID3", 1, new BigDecimal(1), "S", OrderStatus.PENDING,
					new Timestamp(System.currentTimeMillis())));
		});
	}

	@Test
	void testInsertTrade_DuplicatePrimaryKeyThrowsException() {
		assertThrows(Exception.class, () -> {
			tradeDao.insertTrade(new Trade("T001", "C001", "O001", "I001", 10, new BigDecimal(145.00).setScale(2), "B",
					Timestamp.valueOf("2023-01-01 21:00:00")));
		});
	}

	@Test
	void testInsertOrder_DuplicatePrimaryKeyThrowsException() {
		assertThrows(Exception.class, () -> {
			tradeDao.insertOrder(new Order("O002", "C002", "I002", 20, new BigDecimal(245.00).setScale(2), "S", OrderStatus.EXECUTED,
					Timestamp.valueOf("2023-01-02 21:00:00")));
		});
	}

	@Test
	void testCancelOrder_Success() throws SQLException {
		Connection conn = dataSource.getConnection();
		String ORDER_TO_DELETE = "O002";

		int oldSize = DbTestUtils.countRowsInTable(conn, ORDERS_TABLE);

		dao.cancelOrder(ORDER_TO_DELETE);

		int newSize = DbTestUtils.countRowsInTable(conn, ORDERS_TABLE);
		assertEquals(oldSize, newSize, "Could not delelte from " + ORDERS_TABLE);

		int rowsWithDeletedId = DbTestUtils.countRowsInTableWhere(conn, ORDERS_TABLE, "ORDER_ID = '" + ORDER_TO_DELETE + "'");
		assertEquals(1, rowsWithDeletedId);
	}

	@Test
	void testCancelOrder_NotPresent_ThrowsException() {
		String ORDER_TO_DELETE_NOT_PRESENT = "O002s2";

		assertThrows(DatabaseException.class, () -> {
			dao.cancelOrder(ORDER_TO_DELETE_NOT_PRESENT);
		});
	}

	@Test
	void testCancelOrder_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		String ORDER_TO_DELETE = "O002";

		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.cancelOrder(ORDER_TO_DELETE);
		});
	}

	@Test
	void testModifyOrder_Success() throws SQLException {
		Connection conn = dataSource.getConnection();
		int expectedRows = DbTestUtils.countRowsInTable(conn, ORDERS_TABLE);
		String whereCondition = "ORDER_ID = 'O002' AND DIRECTION = 'B'";
		int rowcount = DbTestUtils.countRowsInTableWhere(conn, ORDERS_TABLE, whereCondition);
		assertEquals(0, rowcount);

		dao.modifyOrder(new Order("O001", "C001", "I001", 20, new BigDecimal(245.00).setScale(2), "B", OrderStatus.CANCELED,
				Timestamp.valueOf("2023-01-02 21:00:00")));

		int actualRows = DbTestUtils.countRowsInTable(conn, ORDERS_TABLE);
		assertEquals(expectedRows, actualRows);
	}

	@Test
	void testModifyOrder_ThrowsDatabaseException_OnBadDataSource() throws Exception {
		when(mockDataSource.getConnection()).thenThrow(new SQLException("mockSQLException"));
		assertThrows(DatabaseException.class, () -> {
			tradeDao.modifyOrder(new Order("O001", "C001", "I001", 20, new BigDecimal(245.00).setScale(2), "B", OrderStatus.EXECUTED,
					Timestamp.valueOf("2023-01-02 21:00:00")));
		});
	}

	@Test
	void testExecuteOrder() {
		Trade trade = dao.executeOrder(new Order("O002", "C001", "I001", 1, new BigDecimal(1), "S", OrderStatus.EXECUTED,
				new Timestamp(System.currentTimeMillis())));
		assertNotNull(trade);
	}
}
