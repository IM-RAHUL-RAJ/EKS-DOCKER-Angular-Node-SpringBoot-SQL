package com.capstone.integration;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.capstone.enums.OrderStatus;
import com.capstone.exceptions.DatabaseException;
import com.capstone.models.Instrument;
import com.capstone.models.Order;
import com.capstone.models.Price;
import com.capstone.models.Trade;

public class TradeDaoImpl implements TradeDao {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private DataSource dataSource;

	public TradeDaoImpl(DataSource ds) {
		dataSource = ds;
	}

	private Instrument extractInstrument(ResultSet rs) throws SQLException {
		String instrumentId = rs.getString("INSTRUMENT_ID");
		String externalIdType = rs.getString("EXTERNAL_ID_TYPE");
		String externalId = rs.getString("EXTERNAL_ID");
		String categoryId = rs.getString("CATEGORY_ID");
		String instrumentDescription = rs.getString("DESCRIPTION");
		int maxQuantity = rs.getInt("MAX_QUANTITY");
		int minQuantity = rs.getInt("MIN_QUANTITY");
		return new Instrument(instrumentId, externalIdType, externalId, categoryId, instrumentDescription, maxQuantity,
				minQuantity);
	}

	private Order extractOrder(ResultSet rs) throws SQLException {
		String orderId = rs.getString("ORDER_ID");
		String instrumentId = rs.getString("INSTRUMENT_ID");
		String clientId = rs.getString("clientId");
		int quantity = rs.getInt("QUANTITY");
		BigDecimal targetPrice = rs.getBigDecimal("TARGET_PRICE");
		String direction = rs.getString("DIRECTION");
		
		int orderStatus = rs.getInt("STATUS");
		OrderStatus orderStatusEnum = null;
		if (!rs.wasNull()) {
			orderStatusEnum = OrderStatus.of(orderStatus);
		}
		Timestamp creationTime = null;
		return new Order(orderId, clientId, instrumentId, quantity, targetPrice, direction, orderStatusEnum, creationTime);
	}

	private Trade extractTrade(ResultSet rs) throws SQLException {
		String tradeId = rs.getString("TRADE_ID");
		String clientId = rs.getString("clientId");
		String orderId = rs.getString("ORDER_ID");
		String instrumentId = rs.getString("INSTRUMENT_ID");
		int quantity = rs.getInt("QUANTITY");
		BigDecimal executionPrice = rs.getBigDecimal("EXECUTION_PRICE");
		;
		String direction = rs.getString("DIRECTION");
		Timestamp creationTime = null;
		return new Trade(tradeId, clientId, orderId, instrumentId, quantity, executionPrice, direction, creationTime);
	}

	@Override
	public List<Instrument> getAllInstruments() {
		String GET_INSTRUMENTS_QUERY = "SELECT * FROM ss_instruments";
		List<Instrument> allInstruments = new ArrayList<>();
		Statement stmt = null;
		try {
			Connection conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(GET_INSTRUMENTS_QUERY);
			while (rs.next()) {
				allInstruments.add(extractInstrument(rs));
			}
		} catch (SQLException e) {
			String message = "Unable to extract instruments";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return allInstruments;
	}

	@Override
	public Instrument getInstrumentById(String instrumentId) {
		String GET_INSTRUMENT_BY_ID_QUERY = "SELECT * FROM ss_instruments WHERE INSTRUMENT_ID = ?";
		Instrument instrument = null;
		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(GET_INSTRUMENT_BY_ID_QUERY)) {
				stmt.setString(1, instrumentId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					instrument = this.extractInstrument(rs);
				}
			}
		} catch (SQLException e) {
			String message = "Unable to extract instrument information";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return instrument;
	}

	@Override
	public Price getPrice(String instrumentId) {
		String GET_PRICE_BY_ID_QUERY = "SELECT * FROM ss_price WHERE INSTRUMENT_ID = ?";
		Price price = null;
		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(GET_PRICE_BY_ID_QUERY)) {
				stmt.setString(1, instrumentId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					BigDecimal askPrice = rs.getBigDecimal("ASKPRICE");
					BigDecimal bidPrice = rs.getBigDecimal("BIDPRICE");
					Timestamp updateTime = null;
					price = new Price(instrumentId, askPrice, bidPrice, updateTime);
				}
			}
		} catch (SQLException e) {
			String message = "Unable to extract price information";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return price;
	}

	@Override
	public List<Trade> getTradeHistory() {
		String GET_TRADE_HISTORY_QUERY = "SELECT * FROM ss_trades ORDER BY EXECUTED_AT DESC FETCH FIRST 100 ROWS ONLY";
		List<Trade> allTrades = new ArrayList<>();
		Statement stmt = null;
		try {
			Connection conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(GET_TRADE_HISTORY_QUERY);
			while (rs.next()) {
				allTrades.add(extractTrade(rs));
			}
		} catch (SQLException e) {
			String message = "Unable to trade history";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return allTrades;
	}

	@Override
	public Trade getTradeById(String tradeId) {
		String GET_TRADE_BY_ID_QUERY = "SELECT * FROM ss_trades WHERE TRADE_ID = ?";
		Trade trade = null;
		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(GET_TRADE_BY_ID_QUERY)) {
				stmt.setString(1, tradeId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					trade = this.extractTrade(rs);
				}
			}
		} catch (SQLException e) {
			String message = "Unable to extract trade information";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return trade;
	}

	@Override
	public void insertTrade(Trade trade) {
		String INSERT_TRADE_QUERY = "INSERT INTO SS_TRADES (TRADE_ID, clientId, ORDER_ID, INSTRUMENT_ID, QUANTITY, EXECUTION_PRICE, DIRECTION, EXECUTED_AT) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = dataSource.getConnection();
			try (PreparedStatement stmt = conn.prepareStatement(INSERT_TRADE_QUERY)) {
				stmt.setString(1, trade.getTradeId());
				stmt.setString(2, trade.getClientId());
				stmt.setString(3, trade.getOrderId());
				stmt.setString(4, trade.getInstrumentId());
				stmt.setInt(5, trade.getQuantity());
				stmt.setBigDecimal(6, trade.getExecutionPrice());
				stmt.setString(7, trade.getDirection());
				stmt.setTimestamp(8, trade.getCreationTime());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("Cannot insert into orders {}", INSERT_TRADE_QUERY, e);
			throw new DatabaseException("Cannot insert into orders " + INSERT_TRADE_QUERY, e);
		}
	}

	@Override
	public List<Order> getAllPendingOrders() {
		String GET_PENDING_ORDERS_QUERY = "SELECT * FROM ss_orders WHERE STATUS = 0";
		List<Order> pendingOrders = new ArrayList<>();
		Statement stmt = null;
		try {
			Connection conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(GET_PENDING_ORDERS_QUERY);
			while (rs.next()) {
				pendingOrders.add(extractOrder(rs));
			}
		} catch (SQLException e) {
			String message = "Unable to extract pending orders";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return pendingOrders;
	}

	@Override
	public List<Order> getAllOrders() {
		String GET_ORDERS_QUERY = "SELECT * FROM ss_orders";
		List<Order> allOrders = new ArrayList<>();
		Statement stmt = null;
		try {
			Connection conn = dataSource.getConnection();
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(GET_ORDERS_QUERY);
			while (rs.next()) {
				allOrders.add(extractOrder(rs));
			}
		} catch (SQLException e) {
			String message = "Unable to extract orders";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return allOrders;
	}

	@Override
	public Order getOrderById(String orderId) {
		String GET_ORDER_BY_ID_QUERY = "SELECT * FROM ss_orders WHERE ORDER_ID = ?";
		Order order = null;
		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(GET_ORDER_BY_ID_QUERY)) {
				stmt.setString(1, orderId);
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					order = this.extractOrder(rs);
				}
			}
		} catch (SQLException e) {
			String message = "Unable to extract order information";
			logger.error(message, e);
			throw new DatabaseException(message, e);
		}
		return order;
	}

	@Override
	public void insertOrder(Order order) {
		String INSERT_ORDER_QUERY = "INSERT INTO SS_ORDERS (ORDER_ID, clientId, INSTRUMENT_ID, QUANTITY, TARGET_PRICE, DIRECTION, STATUS, CREATED_AT) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection conn = dataSource.getConnection();
			try (PreparedStatement stmt = conn.prepareStatement(INSERT_ORDER_QUERY)) {
				stmt.setString(1, order.getOrderId());
				stmt.setString(2, order.getClientId());
				stmt.setString(3, order.getInstrumentId());
				stmt.setInt(4, order.getQuantity());
				stmt.setBigDecimal(5, order.getTargetPrice());
				stmt.setString(6, order.getDirection());
				stmt.setInt(7, order.getOrderStatus().getCode());
				stmt.setTimestamp(8, order.getCreationTime());

				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("Cannot insert into orders {}", INSERT_ORDER_QUERY, e);
			throw new DatabaseException("Cannot insert into orders " + INSERT_ORDER_QUERY, e);
		}
	}

	@Override
	public void cancelOrder(String orderId) {
		String CANCEL_ORDER_BY_ID_QUERY = "UPDATE ss_orders SET status = 2 WHERE ORDER_ID = ?";
		try {
			Connection conn = dataSource.getConnection();
			try (PreparedStatement stmt = conn.prepareStatement(CANCEL_ORDER_BY_ID_QUERY)) {
				stmt.setString(1, orderId);
				int canceledOrder = stmt.executeUpdate();
				if (canceledOrder == 0) {
					throw new DatabaseException("Order does not exist " + CANCEL_ORDER_BY_ID_QUERY);
				}
			}
		} catch (SQLException e) {
			logger.error("Cannot delete from orders {}", CANCEL_ORDER_BY_ID_QUERY, e);
			throw new DatabaseException("Cannot delete from orders " + CANCEL_ORDER_BY_ID_QUERY, e);
		}
	}

	@Override
	public void modifyOrder(Order order) {
		String MODIFY_ORDER_QUERY = "UPDATE ss_orders SET ORDER_ID = ?, clientId = ?, INSTRUMENT_ID = ?, QUANTITY = ?, TARGET_PRICE = ?, DIRECTION = ?, STATUS = ?, CREATED_AT = ? WHERE ORDER_ID = ?";
		try {
			Connection connection = dataSource.getConnection();
			try (PreparedStatement stmt = connection.prepareStatement(MODIFY_ORDER_QUERY)) {
				stmt.setString(1, order.getOrderId());
				stmt.setString(2, order.getClientId());
				stmt.setString(3, order.getInstrumentId());
				stmt.setInt(4, order.getQuantity());
				stmt.setBigDecimal(5, order.getTargetPrice());
				stmt.setString(6, order.getDirection());
				stmt.setInt(7, order.getOrderStatus().getCode());
				stmt.setTimestamp(8, order.getCreationTime());
				stmt.setString(9, order.getOrderId());
				stmt.executeUpdate();
			}
		} catch (SQLException ex) {
			throw new DatabaseException("Unable to modify order with id=" + order.getOrderId(), ex);
		}
	}

	@Override
	public Trade executeOrder(Order order) {
		Trade trade = null;
		try {
			String tradeId = 'T' + order.getOrderId();
			String clientId = order.getClientId();
			String orderId = order.getOrderId();
			String instrumentId = order.getInstrumentId();
			int quantity = order.getQuantity();
			BigDecimal executionPrice = order.getTargetPrice();
			String direction = order.getDirection();
			Timestamp creationTime = order.getCreationTime();
			trade = new Trade(tradeId, clientId, orderId, instrumentId, quantity, executionPrice, direction,
					creationTime);
			this.insertTrade(trade);
		} catch (Exception ex) {
			throw new DatabaseException("Unable to execute order with id=" + order.getOrderId(), ex);
		}
		return trade;
	}
}
